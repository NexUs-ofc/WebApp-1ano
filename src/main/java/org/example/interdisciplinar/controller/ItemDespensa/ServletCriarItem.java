package org.example.interdisciplinar.controller.ItemDespensa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.interdisciplinar.dao.ItemDespensaDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.ItemDespensa;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet(name = "CriarItem", value = "/itensDespensa/criar")
public class ServletCriarItem extends HttpServlet {

    private ItemDespensaDAO itemDespensaDAO;

    @Override
    public void init() {
        itemDespensaDAO = new ItemDespensaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idUsuarioTexto = request.getParameter("idUsuario");
        String idAlimentoTexto = request.getParameter("idAlimento");
        String quantidadeTexto = request.getParameter("quantidade");
        String validadeTexto = request.getParameter("validade");

        if (idUsuarioTexto == null || idUsuarioTexto.isBlank()
                || idAlimentoTexto == null || idAlimentoTexto.isBlank()
                || quantidadeTexto == null || quantidadeTexto.isBlank()
                || validadeTexto == null || validadeTexto.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Preencha todos os campos."
            );
            return;
        }

        try {
            int idUsuario = Integer.parseInt(idUsuarioTexto);
            int idAlimento = Integer.parseInt(idAlimentoTexto);
            int quantidade = Integer.parseInt(quantidadeTexto);
            LocalDate validade = LocalDate.parse(validadeTexto);

            if (idUsuario <= 0 || idAlimento <= 0 || quantidade <= 0) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Os IDs e a quantidade devem ser maiores que zero."
                );
                return;
            }

            ItemDespensa itemDespensa = new ItemDespensa();

            itemDespensa.setIdUsuario(idUsuario);
            itemDespensa.setIdAlimento(idAlimento);
            itemDespensa.setQuantidade(quantidade);
            itemDespensa.setValidade(validade);

            boolean inserido = itemDespensaDAO.inserir(itemDespensa);

            if (inserido) {
                response.sendRedirect(request.getContextPath() + "/itensDespensa");
            } else {
                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Não foi possível cadastrar o item."
                );
            }

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Os IDs e a quantidade devem ser numéricos."
            );

        } catch (DateTimeParseException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "A data informada é inválida."
            );

        } catch (FalhaConexaoException e) {
            getServletContext().log("Falha ao conectar ao banco de dados.", e);
            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro ao cadastrar item da despensa.", e);
            request.setAttribute("erro", "Não foi possível cadastrar o item.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}