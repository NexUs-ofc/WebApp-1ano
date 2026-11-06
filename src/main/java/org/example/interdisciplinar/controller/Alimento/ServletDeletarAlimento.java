package org.example.interdisciplinar.controller.Alimento;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import org.example.interdisciplinar.dao.AlimentoDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// Define o endereço responsável pela exclusão dos alimentos
@WebServlet(name = "DeletarAlimento", value = "/admin/alimentos/deletar")
public class ServletDeletarAlimento extends HttpServlet {

    // Instância responsável pelas operações no banco de dados
    private AlimentoDAO alimentoDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        alimentoDAO = new AlimentoDAO();
    }

    // Método que recebe o ID e realiza a exclusão de um alimento
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupera o ID enviado pelo formulário
        String idParametro = request.getParameter("id");

        // Verifica se o ID foi informado
        if (idParametro == null || idParametro.isBlank()) {
            request.setAttribute("erro", "ID do alimento não informado.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
            return;
        }

        try {
            // Converte o ID para inteiro
            int id = Integer.parseInt(idParametro);

            // Verifica se o ID é válido
            if (id <= 0) {
                request.setAttribute("erro", "ID do alimento inválido.");
                request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
                return;
            }

            // Envia o ID para a DAO realizar a exclusão
            boolean deletado = alimentoDAO.deletar(id);

            // Verifica se o alimento foi excluído
            if (!deletado) {
                response.sendRedirect(request.getContextPath() + "/admin/alimentos");
                return;
            }

            // Redireciona para a listagem após a exclusão
            response.sendRedirect(request.getContextPath() + "/admin/alimentos");

        } catch (NumberFormatException e) {
            // Registra o erro de conversão no log
            getServletContext().log("Erro ao converter o ID do alimento", e);

            request.setAttribute("erro", "ID do alimento inválido.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (FalhaConexaoException e) {
            // Registra os detalhes da falha de conexão no log
            getServletContext().log("Falha na conexão com o banco de dados", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao deletar alimento", e);

            request.setAttribute("erro", "Não foi possível deletar o alimento.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
        }
    }
}
