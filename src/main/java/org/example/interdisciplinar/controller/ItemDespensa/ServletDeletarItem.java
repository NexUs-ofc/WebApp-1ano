package org.example.interdisciplinar.controller.ItemDespensa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.dao.ItemDespensaDAO;

import java.io.IOException;

@WebServlet(name = "DeletarItem", value = "/itensDespensa/deletar")
public class ServletDeletarItem extends HttpServlet {
    private ItemDespensaDAO itemDespensaDAO;

    @Override
    public void init(){
        itemDespensaDAO = new ItemDespensaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idItemTexto = request.getParameter("id");
        int idItem = Integer.parseInt(idItemTexto);
        itemDespensaDAO.deletar(idItem);
        response.sendRedirect(request.getContextPath() + "/itensDespensa");
    }
}
