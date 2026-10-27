package org.example.interdisciplinar.controller.ItemDispensa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.ItemDispensaDAO;
import org.example.interdisciplinar.model.ItemDispensa;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListarItens", value = "/itensDispensa")
public class ServletListarItem extends HttpServlet {
    private ItemDispensaDAO itemDispensaDAO;

    @Override
    public void init(){
        itemDispensaDAO = new ItemDispensaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<ItemDispensa> itensDispensa = itemDispensaDAO.listar();
        request.setAttribute("itensDispensa", itensDispensa);
        request.getRequestDispatcher("/WEB-INF/Views/itensDispensa.jsp").forward(request, response);
    }

    public void destroy(){
        GerenteConexao.desconectar();
    }
}
