package org.example.interdisciplinar.controller.Administrador;

import org.example.interdisciplinar.dao.AdministradorDAO;
import org.example.interdisciplinar.model.Administrador;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(
        name = "DeletarAdministrador",
        value = {"/administradores/excluir", "/admin/administradores/excluir"}
)
public class ServletDeletarAdministrador extends HttpServlet {

    private AdministradorDAO administradorDAO;

    @Override
    public void init() {
        administradorDAO = new AdministradorDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession sessao = request.getSession(false);

        // Verifica se existe um administrador autenticado.
        if (sessao == null ||
                !(sessao.getAttribute("administrador") instanceof Administrador)) {

            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Administrador administradorLogado =
                (Administrador) sessao.getAttribute("administrador");

        String idParametro = request.getParameter("id");

        if (idParametro == null || idParametro.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Administrador inválido.");
            return;
        }

        try {
            int id = Integer.parseInt(idParametro);

            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "ID do administrador inválido.");
                return;
            }

            // Impede que o administrador exclua sua própria conta.
            if (id == administradorLogado.getId()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Você não pode excluir sua própria conta.");
                return;
            }

            // Verifica se o administrador existe.
            Administrador administrador = administradorDAO.buscarporId(id);

            if (administrador == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Administrador não encontrado.");
                return;
            }

            // Exclui o administrador do banco de dados.
            if (!administradorDAO.deletar(id)) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Não foi possível excluir o administrador.");
                return;
            }

            // Retorna à listagem de administradores.
            response.sendRedirect(request.getContextPath() + "/administradores");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "ID do administrador inválido.");

        } catch (FalhaConexaoException e) {
            getServletContext().log("Falha na conexão com o banco de dados", e);

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível conectar ao banco de dados.");

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro ao deletar administrador", e);

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível deletar o administrador.");
        }
    }
}
