package org.example.interdisciplinar.controller.Categoria;

// Todos os imports necessários
import org.example.interdisciplinar.dao.CategoriaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import java.io.IOException;

@WebServlet(name = "DeletarCategoria", value = "/categorias/deletar")
public class ServletDeletarCategoria extends HttpServlet {

    private CategoriaDAO categoriaDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        categoriaDAO = new CategoriaDAO();
    }

    // Método que recebe a solicitação de exclusão
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupera o ID da categoria
        String idParametro = request.getParameter("id");

        // Verifica se o ID foi informado
        if (idParametro == null || idParametro.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/categorias");
            return;
        }

        try {
            // Converte o ID para inteiro
            int id = Integer.parseInt(idParametro);

            // Verifica se o ID é válido
            if (id <= 0) {
                response.sendRedirect(request.getContextPath() + "/categorias");
                return;
            }

            // Realiza a exclusão da categoria
            categoriaDAO.deletar(id);

            // Retorna para a listagem
            response.sendRedirect(request.getContextPath() + "/categorias");

        } catch (NumberFormatException e) {
            // Trata IDs que não possuem formato numérico
            response.sendRedirect(request.getContextPath() + "/categorias");

        } catch (FalhaConexaoException e) {
            getServletContext().log("Falha na conexão com o banco", e);
            request.setAttribute("erro", "Não foi possível estabelecer conexão com o banco.");

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro ao deletar categoria", e);
            request.setAttribute("erro", "Não foi possível deletar a categoria.");
        }
    }
}
