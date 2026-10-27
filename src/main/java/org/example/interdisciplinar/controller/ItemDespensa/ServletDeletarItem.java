package org.example.interdisciplinar.controller.ItemDespensa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.ItemDespensaDAO;

import java.io.IOException;

// Recebe o id confirmado no pop-up de exclusao
@WebServlet(name = "DeletarItemDespensa", value = "/itensDespensa/deletar")
public class ServletDeletarItem extends HttpServlet {
    private ItemDespensaDAO itemDespensaDAO;

    @Override
    public void init() {
        itemDespensaDAO = new ItemDespensaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idItem = Integer.parseInt(request.getParameter("id"));

            if (idItem <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Item invalido.");
                return;
            }

            if (!itemDespensaDAO.deletar(idItem)) {
                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Nao foi possivel excluir o item da despensa."
                );
                return;
            }

            response.sendRedirect(request.getContextPath() + "/itensDespensa");
        } catch (NumberFormatException excecao) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Item invalido.");
        }
    }

    @Override
    public void destroy() {
        GerenteConexao.desconectar();
    }
}
