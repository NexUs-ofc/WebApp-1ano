package org.example.interdisciplinar.controller.Administrador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.interdisciplinar.dao.AdministradorDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Administrador;

import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "ListarAdministradores",
        value = {"/administradores", "/admin/administradores"}
)
public class ServletListarAdministrador extends HttpServlet {

    private AdministradorDAO administradorDAO;

    @Override
    public void init() {
        administradorDAO = new AdministradorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idInicio = null;
        Integer idFim = null;

        try {
            String idInicioTexto = request.getParameter("idInicio");
            String idFimTexto = request.getParameter("idFim");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String ordenar = request.getParameter("ordenar");

            if (idInicioTexto != null && !idInicioTexto.isBlank()) {
                idInicio = Integer.parseInt(idInicioTexto);
            }

            if (idFimTexto != null && !idFimTexto.isBlank()) {
                idFim = Integer.parseInt(idFimTexto);
            }

            if (idInicio != null && idFim != null && idInicio > idFim) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "O ID inicial não pode ser maior que o ID final."
                );
                return;
            }

            List<Administrador> administradores =
                    administradorDAO.listarFiltrado(
                            idInicio, idFim, nome, email, ordenar
                    );

            request.setAttribute("administradores", administradores);

            request.getRequestDispatcher("/WEB-INF/Views/administradores.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            getServletContext().log("Erro ao formatar o número.", e);

            request.setAttribute("erro",
                    "O ID dos administradores precisa ser um número inteiro.");

            request.getRequestDispatcher("/WEB-INF/errorpage.jsp")
                    .forward(request, response);

        } catch (FalhaConexaoException e) {
            getServletContext().log("Falha ao conectar ao banco de dados.", e);

            request.setAttribute("erro",
                    "Não foi possível estabelecer conexão com o banco de dados.");

            request.getRequestDispatcher("/WEB-INF/errorpage.jsp")
                    .forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro ao listar os administradores", e);

            request.setAttribute("erro",
                    "Ocorreu um erro ao listar os administradores.");

            request.getRequestDispatcher("/WEB-INF/errorpage.jsp")
                    .forward(request, response);
        }
    }
}
