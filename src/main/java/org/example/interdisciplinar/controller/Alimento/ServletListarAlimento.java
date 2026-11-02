package org.example.interdisciplinar.controller.Alimento;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
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

// Define o endereço responsável pela listagem dos alimentos
@WebServlet(name = "ListarAlimentos", value = "/alimentos")
public class ServletListarAlimento extends HttpServlet {

    private AlimentoDAO alimentoDAO;

    // Método que inicializa a DAO
    @Override
    public void init(){
        alimentoDAO = new AlimentoDAO();
    }

    // Método que recebe os filtros e lista os alimentos
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Inicializa os IDs como nulos caso nenhum filtro seja informado
        Integer idInicio = null;
        Integer idFim = null;

        try {
            // Recupera os filtros enviados pela página
            String idInicioTexto = request.getParameter("idInicio");
            String idFimTexto = request.getParameter("idFim");
            String nome = request.getParameter("nome");
            String marca = request.getParameter("marca");
            String categoria = request.getParameter("categoria");
            String ordenar = request.getParameter("ordenar");

            // Converte o ID inicial para inteiro, caso tenha sido informado
            if (idInicioTexto != null && !idInicioTexto.isBlank()) {
                idInicio = Integer.parseInt(idInicioTexto);
            }

            // Converte o ID final para inteiro, caso tenha sido informado
            if (idFimTexto != null && !idFimTexto.isBlank()) {
                idFim = Integer.parseInt(idFimTexto);
            }

            // Verifica se o ID inicial é maior que o ID final
            if (idInicio != null && idFim != null && idInicio > idFim) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "O ID inicial não pode ser maior que o ID final."
                );
                return;
            }

            // Busca os alimentos de acordo com os filtros informados
            List<AlimentoView> alimentos = alimentoDAO.listarFiltrado(idInicio, idFim, nome, categoria, marca, ordenar);

            // Envia a lista de alimentos para a página
            request.setAttribute("alimentos", alimentos);

            // Encaminha para a página de listagem
            request.getRequestDispatcher("/WEB-INF/Views/alimentos.jsp").forward(request, response);

        } catch (NumberFormatException nfe) {
            // Registra o erro de conversão no log do servidor
            getServletContext().log("Erro ao formatar o número.", nfe);

            request.setAttribute("erro", "O ID dos alimentos precisa ser um número inteiro");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (FalhaConexaoException e) {

            // Registra os detalhes da falha de conexão no log
            getServletContext().log("Falha ao conectar ao banco de dados.", e);

            // Define uma mensagem compreensível para o administrador
            request.setAttribute(
                    "erro",
                    "Não foi possível estabelecer conexão com o banco de dados."
            );

            // Encaminha para a página de erro
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {

            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao listar os alimentos", e);

            // Informa que ocorreu um problema durante a listagem
            request.setAttribute(
                    "erro",
                    "Ocorreu um erro ao listar os alimentos."
            );

            // Encaminha para a página de erro
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        }
    }
}