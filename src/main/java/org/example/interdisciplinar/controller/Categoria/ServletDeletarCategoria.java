package org.example.interdisciplinar.controller.Categoria;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.CategoriaDAO;
import org.example.interdisciplinar.model.Categoria;

import java.io.IOException;

public class ServletDeletarCategoria extends HttpServlet {
    private CategoriaDAO categoriaDAO;
    @Override
    public void init(){
        categoriaDAO = new CategoriaDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idCategoriaTexto = request.getParameter("id");
        int idCategoria = Integer.parseInt(idCategoriaTexto);
        categoriaDAO.deletar(idCategoria);
        response.sendRedirect(request.getContextPath() + "/categorias");
    }

    public void destroy(){
        GerenteConexao.desconectar();
    }
}
