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


// Definindo um nome interno para o servlet e a url que ativa ele
@WebServlet(name = "CriarCategorias", value = "/categorias/insert")


public class ServletCriarCategoria extends HttpServlet {
    private CategoriaDAO categoriaDAO;

    @Override
    public void init(){
        categoriaDAO = new CategoriaDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                "/WEB-INF/Views/categorias.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        String nomeCategoria = request.getParameter("nomeCategoria");
        String descricaoCategoria = request.getParameter("descricaoCategoria");

        // Verifica se nenhum campo está vazio
        if (nomeCategoria == null || nomeCategoria.isBlank()
                || descricaoCategoria == null || descricaoCategoria.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Preencha todos os campos."
            );
            request.getRequestDispatcher("/categorias").forward(request, response);
            return;
        }


        // Cria o objeto Categoria
        Categoria categoria = new Categoria(nomeCategoria, descricaoCategoria);

        // Chama o DAO para inserir no banco
        boolean inserido = categoriaDAO.inserir(categoria);

        if (inserido) {

            // Redireciona para a listagem
            response.sendRedirect(
                    request.getContextPath() + "/categorias"
            );

        } else {

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível inserir o alimento."
            );
        }

    }

    @Override
    public void destroy(){
        GerenteConexao.desconectar();
    }

}

