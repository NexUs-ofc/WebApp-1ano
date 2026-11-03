package org.example.interdisciplinar.controller.Categoria;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import org.example.interdisciplinar.dao.CategoriaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import java.io.IOException;

// Define o endereço responsável pela exclusão das categorias
@WebServlet(name = "DeletarCategoria", value = "/admin/categorias/deletar")
public class ServletDeletarCategoria extends HttpServlet {

    private CategoriaDAO categoriaDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        categoriaDAO = new CategoriaDAO();
    }

    // Método que recebe o ID e realiza a exclusão de uma categoria
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupera o ID da categoria enviado pelo formulário
        String idParametro = request.getParameter("id");

        // Verifica se o ID foi informado
        if (idParametro == null || idParametro.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/admin/categorias");
            return;
        }

        try {
            // Converte o ID para inteiro
            int id = Integer.parseInt(idParametro);

            // Verifica se o ID é válido
            if (id <= 0) {
                response.sendRedirect(request.getContextPath() + "/admin/categorias");
                return;
            }

            // Envia o ID para a DAO realizar a exclusão
            categoriaDAO.deletar(id);

            // Redireciona para a listagem após a exclusão
            response.sendRedirect(request.getContextPath() + "/admin/categorias");

        } catch (NumberFormatException e) {
            // Trata IDs que não possuem formato numérico
            response.sendRedirect(request.getContextPath() + "/admin/categorias");

        } catch (FalhaConexaoException e) {
            // Registra os detalhes do erro de conexão no log
            getServletContext().log("Falha na conexão com o banco", e);
            request.setAttribute("erro", "Não foi possível estabelecer conexão com o banco.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao deletar categoria", e);
            request.setAttribute("erro", "Não foi possível deletar a categoria.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
        }
    }
}