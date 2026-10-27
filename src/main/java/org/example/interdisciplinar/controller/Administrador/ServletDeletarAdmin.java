package org.example.interdisciplinar.controller.Administrador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.AdministradorDAO;

import java.io.IOException;

// Recebe o id confirmado no pop-up de exclusão
@WebServlet(name = "DeletarAdministrador", value = "/administradores/deletar")
public class ServletDeletarAdmin extends HttpServlet {
    private AdministradorDAO administradorDAO;

    @Override
    public void init() {
        administradorDAO = new AdministradorDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idAdministrador = Integer.parseInt(request.getParameter("id"));

            if (!administradorDAO.deletar(idAdministrador)) {
                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Não foi possível excluir o administrador."
                );
                return;
            }

            response.sendRedirect(request.getContextPath() + "/administradores");
        } catch (NumberFormatException excecao) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Administrador inválido.");
        }
    }

    @Override
    public void destroy() {
        GerenteConexao.desconectar();
    }
}
