package org.example.interdisciplinar.controller.ItemDespensa;

import org.example.interdisciplinar.dao.ItemDespensaDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.ItemDespensa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet(name = "AtualizarItem", value = "/itensDespensa/editar")
public class ServletAtualizarItem extends HttpServlet {

    private ItemDespensaDAO itemDespensaDAO;

    @Override
    public void init() {
        itemDespensaDAO = new ItemDespensaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idItemTexto = request.getParameter("id");

            if (idItemTexto == null || idItemTexto.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            int idItem = Integer.parseInt(idItemTexto);

            if (idItem <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            ItemDespensa itemDespensa = itemDespensaDAO.buscarporId(idItem);

            if (itemDespensa == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            request.setAttribute("itemDespensa", itemDespensa);
            request.getRequestDispatcher("/itensDespensa")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            getServletContext().log("Erro de conexão com o banco.", e);
            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro de persistência.", e);
            request.setAttribute("erro", "Não foi possível buscar o item pelo ID.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String idParametro = request.getParameter("id");
            String idUsuarioTexto = request.getParameter("idUsuario");
            String idAlimentoTexto = request.getParameter("idAlimento");
            String quantidadeTexto = request.getParameter("quantidade");
            String validadeTexto = request.getParameter("validade");

            if (idParametro == null || idParametro.isBlank()
                    || idUsuarioTexto == null || idUsuarioTexto.isBlank()
                    || idAlimentoTexto == null || idAlimentoTexto.isBlank()
                    || quantidadeTexto == null || quantidadeTexto.isBlank()
                    || validadeTexto == null || validadeTexto.isBlank()) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Preencha todos os campos."
                );
                return;
            }

            int id = Integer.parseInt(idParametro);
            int idUsuario = Integer.parseInt(idUsuarioTexto);
            int idAlimento = Integer.parseInt(idAlimentoTexto);
            int quantidade = Integer.parseInt(quantidadeTexto);
            LocalDate validade = LocalDate.parse(validadeTexto);

            if (id <= 0 || idUsuario <= 0 || idAlimento <= 0 || quantidade <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            ItemDespensa itemExistente = itemDespensaDAO.buscarporId(id);

            if (itemExistente == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            ItemDespensa itemDespensa = new ItemDespensa(
                    idUsuario,
                    idAlimento,
                    quantidade,
                    validade
            );

            itemDespensa.setId(id);

            boolean atualizado = itemDespensaDAO.atualizar(itemDespensa);

            if (atualizado) {
                response.sendRedirect(request.getContextPath() + "/itensDespensa");
            } else {
                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Não foi possível atualizar o item."
                );
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (DateTimeParseException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Formato de validade inválido."
            );

        } catch (FalhaConexaoException e) {
            getServletContext().log("Erro de conexão com o banco.", e);
            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro de persistência.", e);
            request.setAttribute("erro", "Não foi possível realizar a operação.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}