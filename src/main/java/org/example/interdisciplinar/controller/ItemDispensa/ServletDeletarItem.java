package org.example.interdisciplinar.controller.ItemDispensa;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.ItemDespensaDAO;
import org.example.interdisciplinar.model.ItemDespensa;

import java.io.IOException;

public class ServletDeletarItem extends HttpServlet {
    private ItemDespensaDAO itemDespensaDAO;
    @Override
    public void init(){
        itemDespensaDAO = new ItemDespensaDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idItemTexto = request.getParameter("id");
        int idItem = Integer.parseInt(idItemTexto);
        itemDespensaDAO.deletar(idItem);
        response.sendRedirect(request.getContextPath() + "/itens");
    }

}
