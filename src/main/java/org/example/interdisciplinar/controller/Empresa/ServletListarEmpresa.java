package org.example.interdisciplinar.controller.Empresa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.dao.EmpresaDAO;
import org.example.interdisciplinar.dto.EmpresaView;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListarEmpresas", value = "/empresas")
public class ServletListarEmpresa extends HttpServlet {

    private EmpresaDAO empresaDAO;

    @Override
    public void init() {
        empresaDAO = new EmpresaDAO();
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

            List<EmpresaView> empresas = empresaDAO.listarFiltrado(
                    idInicio, idFim, nome, ordenar
            );

            request.setAttribute("empresas", empresas);
            request.getRequestDispatcher("/WEB-INF/Views/empresas.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "O ID da empresa precisa ser um número inteiro."
            );

        } catch (FalhaConexaoException e) {
            getServletContext().log("Falha ao conectar ao banco de dados.", e);
            request.setAttribute("erro", "Não foi possível estabelecer conexão com o banco de dados.");
            request.getRequestDispatcher("/WEB-INF/erro.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro ao listar empresas.", e);
            request.setAttribute("erro", "Ocorreu um erro ao listar as empresas.");
            request.getRequestDispatcher("/WEB-INF/erro.jsp").forward(request, response);
        }
    }
}