package org.example.interdisciplinar.controller.Alimento;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.AlimentoDAO;
import org.example.interdisciplinar.dto.AlimentoView;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Alimento;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListarAlimentos", value = "/alimentos")
public class ServletListarAlimento extends HttpServlet {
    private AlimentoDAO alimentoDAO;

    @Override
    public void init(){
        alimentoDAO = new AlimentoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer idInicio = null;
        Integer idFim = null;

        try {
            String idInicioTexto = request.getParameter("idInicio");
            String idFimTexto = request.getParameter("idFim");
            String nome = request.getParameter("nome");
            String marca = request.getParameter("marca");
            String categoria = request.getParameter("categoria");
            String ordenar = request.getParameter("ordenar");

            if (idInicioTexto != null && !idInicioTexto.isBlank()) {
                idInicio = Integer.parseInt(idInicioTexto);
            }

            if (idFimTexto != null && !idFimTexto.isBlank()) {
                idFim = Integer.parseInt(idFimTexto);
            }

            if (idInicio != null && idFim != null && idInicio > idFim) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "O ID inicial não pode ser maior que o ID final."
                );
                return;
            }

            List<AlimentoView> alimentos = alimentoDAO.listarFiltrado(idInicio, idFim, nome, categoria, marca, ordenar);
            request.setAttribute("alimentos", alimentos);
            request.getRequestDispatcher("/WEB-INF/Views/alimentos.jsp").forward(request, response);

        } catch (NumberFormatException nfe) {
            getServletContext().log("Erro ao formatar o número.", nfe);
            request.setAttribute("erro", "O ID dos alimentos precisa ser um número inteiro");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (FalhaConexaoException e) {

            // Registra os detalhes técnicos da falha de conexão.
            getServletContext().log("Falha ao conectar ao banco de dados.", e);

            // Define uma mensagem compreensível para o administrador.
            request.setAttribute(
                    "erro",
                    "Não foi possível estabelecer conexão com o banco de dados."
            );

            // Encaminha para a página de erro
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);


        } catch (ErroPersistenciaException e) {

            // Registra os detalhes técnicos do erro de persistência.
            getServletContext().log("Erro ao listar os alimentos", e);

            // Define uma mensagem compreensível para o administrador.
            request.setAttribute(
                    "erro",
                    "Ocorreu um erro ao listar os administradores."
            );

            // Encaminha para a página de erro.
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        }
    }
}
