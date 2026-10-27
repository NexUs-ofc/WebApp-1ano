package org.example.interdisciplinar.controller.ItemDespensa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.ItemDespensaDAO;
import org.example.interdisciplinar.model.ItemDespensa;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// Recebe os dados enviados pelo pop-up de criacao
@WebServlet(name = "CriarItemDespensa", value = "/itensDespensa/inserir")
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

        try {
            ItemDespensa itemDespensa = new ItemDespensa();
            itemDespensa.setIdUsuario(Integer.parseInt(request.getParameter("idUsuario")));
            itemDespensa.setIdAlimento(Integer.parseInt(request.getParameter("idAlimento")));
            itemDespensa.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
            itemDespensa.setValidade(LocalDate.parse(request.getParameter("validade")));

            if (!dadosValidos(itemDespensa)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados do item invalidos.");
                return;
            }

            if (!itemDespensaDAO.inserir(itemDespensa)) {
                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Nao foi possivel inserir o item da despensa."
                );
                return;
            }

            response.sendRedirect(request.getContextPath() + "/itensDespensa");
        } catch (NumberFormatException | DateTimeParseException excecao) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados do item invalidos.");
        }
    }

    // Confere os campos numericos antes de enviar ao banco
    private boolean dadosValidos(ItemDespensa itemDespensa) {
        return itemDespensa.getIdUsuario() > 0
                && itemDespensa.getIdAlimento() > 0
                && itemDespensa.getQuantidade() > 0;
    }

    @Override
    public void destroy() {
        GerenteConexao.desconectar();
    }
}
