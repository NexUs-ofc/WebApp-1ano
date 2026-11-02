
package org.example.interdisciplinar.controller.ItemDespensa;

// Todos os imports necessários
import org.example.interdisciplinar.dao.ItemDespensaDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "DeletarItemDespensa", value = "/itens-despensa/deletar")
public class ServletDeletarItem extends HttpServlet {

    private ItemDespensaDAO itemDespensaDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        itemDespensaDAO = new ItemDespensaDAO();
    }

    // Método que recebe a solicitação de exclusão
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupera o ID enviado pelo formulário
        String idParametro = request.getParameter("id");

        // Verifica se o ID foi informado
        if (idParametro == null || idParametro.isBlank()) {
            request.setAttribute("erro", "ID do item da despensa não informado.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }

        try {
            // Converte o ID para inteiro
            int id = Integer.parseInt(idParametro);

            // Verifica se o ID é válido
            if (id <= 0) {
                request.setAttribute("erro", "ID do item da despensa inválido.");
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                return;
            }

            // Realiza a exclusão do item da despensa
            itemDespensaDAO.deletar(id);

            // Redireciona após a exclusão
            response.sendRedirect(request.getContextPath() + "/itens-despensa");

        } catch (NumberFormatException e) {
            // Trata IDs que não possuem formato numérico
            request.setAttribute("erro", "ID do item da despensa inválido.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (FalhaConexaoException e) {
            // Registra problemas de conexão com o banco
            getServletContext().log("Falha na conexão com o banco de dados", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra falhas durante a exclusão
            getServletContext().log("Erro ao deletar item da despensa", e);

            request.setAttribute("erro", "Não foi possível deletar o item da despensa.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}
