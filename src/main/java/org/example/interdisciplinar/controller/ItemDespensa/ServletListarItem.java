package org.example.interdisciplinar.controller.ItemDespensa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.ItemDespensaDAO;
import org.example.interdisciplinar.model.ItemDespensa;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListarItens", value = "/itensDespensa")
public class ServletListarItem extends HttpServlet {
    private ItemDespensaDAO itemDespensaDAO;

    @Override
    public void init(){
        itemDespensaDAO = new ItemDespensaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<ItemDespensa> itensDespensa = itemDespensaDAO.listar();
        request.setAttribute("itensDespensa", itensDespensa);
        request.getRequestDispatcher("/WEB-INF/Views/itensDespensa.jsp").forward(request, response);
    }

    public void destroy(){
        GerenteConexao.desconectar();
    }
}
