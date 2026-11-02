
package org.example.interdisciplinar.controller.Categoria;

import org.example.interdisciplinar.dao.CategoriaDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Categoria;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AtualizarCategoria", value = "/admin/categorias/editar")
public class ServletAtualizarCategoria extends HttpServlet {

    private CategoriaDAO categoriaDAO;

    @Override
    public void init() {
        categoriaDAO = new CategoriaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String idParametro = request.getParameter("id");

            if (idParametro == null || idParametro.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            int id = Integer.parseInt(idParametro);

            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Categoria categoria = categoriaDAO.buscarporId(id);

            if (categoria == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            request.setAttribute("categoria", categoria);
            request.getRequestDispatcher("/admin/categorias").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            getServletContext().log("Erro de conexão com o banco", e);
            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro de persistência", e);
            request.setAttribute("erro", "Não foi possível buscar a categoria.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            String idParametro = request.getParameter("id");
            String nome = request.getParameter("nome");
            String descricao = request.getParameter("descricao");

            if (idParametro == null || idParametro.isBlank()
                    || nome == null || nome.isBlank()
                    || descricao == null || descricao.isBlank()) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            int id = Integer.parseInt(idParametro);

            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Categoria categoriaExistente = categoriaDAO.buscarporId(id);

            if (categoriaExistente == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            categoriaExistente.setNome(nome.trim());
            categoriaExistente.setDescricao(descricao.trim());

            categoriaDAO.atualizar(categoriaExistente);

            response.sendRedirect(request.getContextPath() + "/admin/categorias");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            getServletContext().log("Erro de conexão com o banco", e);
            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro de persistência", e);
            request.setAttribute("erro", "Não foi possível atualizar a categoria.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}
