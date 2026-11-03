package org.example.interdisciplinar.controller.ItemDespensa;

import org.example.interdisciplinar.dao.ItemDespensaDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "DeletarItemDespensa", value = "/admin/itens/deletar")
public class ServletDeletarItem extends HttpServlet {

    private ItemDespensaDAO itemDespensaDAO;

    @Override
    public void init() {
        itemDespensaDAO = new ItemDespensaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");

        if (idParametro == null || idParametro.isBlank()) {
            request.setAttribute("erro", "ID do item da despensa não informado.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
            return;
        }

        try {
            int id = Integer.parseInt(idParametro);

            if (id <= 0) {
                request.setAttribute("erro", "ID do item da despensa inválido.");
                request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
                return;
            }

            boolean deletado = itemDespensaDAO.deletar(id);

            if (deletado) {
                response.sendRedirect(request.getContextPath() + "/admin/itens");
            } else {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Item da despensa não encontrado."
                );
            }

        } catch (NumberFormatException e) {
            request.setAttribute("erro", "ID do item da despensa inválido.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (FalhaConexaoException e) {
            getServletContext().log("Falha na conexão com o banco de dados.", e);
            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro ao deletar item da despensa.", e);
            request.setAttribute("erro", "Não foi possível deletar o item da despensa.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
        }
    }
}
