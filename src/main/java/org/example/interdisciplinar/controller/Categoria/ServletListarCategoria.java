package org.example.interdisciplinar.controller.Categoria;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.CategoriaDAO;
import org.example.interdisciplinar.model.Categoria;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListarCategorias", value = "/categorias")
public class ServletListarCategoria extends HttpServlet {
    private CategoriaDAO categoriaDAO;

    @Override
    public void init(){
        categoriaDAO = new CategoriaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Categoria> categorias = categoriaDAO.listar();
        request.setAttribute("categorias", categorias);
        request.getRequestDispatcher("/WEB-INF/Views/categorias.jsp").forward(request, response);
    }


    public void destroy(){
        GerenteConexao.desconectar();
    }
}
