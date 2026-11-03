package org.example.interdisciplinar.controller.Empresa;

import org.example.interdisciplinar.dao.EmpresaDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "DeletarEmpresa", value = "/empresas/deletar")
public class ServletDeletarEmpresa extends HttpServlet {

    private EmpresaDAO empresaDAO;

    @Override
    public void init() {
        empresaDAO = new EmpresaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");

        if (idParametro == null || idParametro.isBlank()) {
            request.setAttribute("erro", "ID da empresa não informado.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }

        try {
            int id = Integer.parseInt(idParametro);

            if (id <= 0) {
                request.setAttribute("erro", "ID da empresa inválido.");
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                return;
            }

            boolean deletado = empresaDAO.deletar(id);

            if (deletado) {
                response.sendRedirect(request.getContextPath() + "/empresas");
            } else {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Empresa não encontrada."
                );
            }

        } catch (NumberFormatException e) {
            request.setAttribute("erro", "ID da empresa inválido.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (FalhaConexaoException e) {
            getServletContext().log("Falha na conexão com o banco de dados.", e);
            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro ao deletar empresa.", e);
            request.setAttribute("erro", "Não foi possível deletar a empresa.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}