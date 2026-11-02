package org.example.interdisciplinar.controller.Endereco;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.EnderecoDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Endereco;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListarEnderecos", value = "/enderecos")
public class ServletListarEndereco extends HttpServlet {
    private EnderecoDAO enderecoDAO;

    @Override
    public void init(){
        enderecoDAO = new EnderecoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            Integer idInicio = null;
            Integer idFim = null;

            String idInicioTexto = request.getParameter("idInicio");
            String idFimTexto = request.getParameter("idFim");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            String ordenar = request.getParameter("ordenar");

            if(idInicioTexto != null && !idInicioTexto.isBlank()){
                idInicio = Integer.parseInt(idInicioTexto);
            }

            if(idFimTexto != null && !idFimTexto.isBlank()){
                idFim = Integer.parseInt(idFimTexto);
            }

            if (idInicio != null && idFim != null && idInicio > idFim) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "O ID inicial não pode ser maior que o ID final."
                );
                return;
            }

            List<Endereco> enderecos = enderecoDAO.listarFiltrado(idInicio, idFim, cidade, estado, ordenar);
            request.setAttribute("enderecos", enderecos);
            request.getRequestDispatcher("/WEB-INF/Views/enderecos.jsp").forward(request,response);

        } catch (NumberFormatException nfe){
            getServletContext().log("Erro ao formatar o número.", nfe);
            request.setAttribute("erro", "O ID do endereço precisa ser um número inteiro");
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
            getServletContext().log("Erro ao listar os endereços", e);

            // Define uma mensagem compreensível para o administrador.
            request.setAttribute(
                    "erro",
                    "Ocorreu um erro ao listar os endereços."
            );

            // Encaminha para a página de erro.
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        }
    }
}
