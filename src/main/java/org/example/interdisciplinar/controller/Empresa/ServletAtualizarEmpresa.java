package org.example.interdisciplinar.controller.Empresa;

import org.example.interdisciplinar.dao.EmpresaDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Empresa;
import org.example.interdisciplinar.utils.Regex;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AtualizarEmpresa", value = "/admin/empresas/editar")
public class ServletAtualizarEmpresa extends HttpServlet {

    private EmpresaDAO empresaDAO;

    @Override
    public void init() {
        empresaDAO = new EmpresaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idParametro = request.getParameter("id");

            if (idParametro == null || idParametro.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            int id = Integer.parseInt(idParametro);

            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Empresa empresa = empresaDAO.buscarporId(id);

            if (empresa == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            request.setAttribute("empresa", empresa);
            request.getRequestDispatcher("/admin/empresas").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            getServletContext().log("Erro de conexão com o banco", e);
            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro de persistência", e);
            request.setAttribute("erro", "Não foi possível buscar a empresa.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            String idParametro = request.getParameter("id");
            String nome = request.getParameter("nome");
            String cnpj = request.getParameter("cnpj");
            String idEnderecoParametro = request.getParameter("idEndereco");
            String email = request.getParameter("email");
            String telefone = request.getParameter("telefone");

            if (idParametro == null || idParametro.isBlank()
                    || nome == null || nome.isBlank()
                    || cnpj == null || cnpj.isBlank()
                    || idEnderecoParametro == null || idEnderecoParametro.isBlank()
                    || email == null || email.isBlank()
                    || telefone == null || telefone.isBlank()) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            int id = Integer.parseInt(idParametro);
            int idEndereco = Integer.parseInt(idEnderecoParametro);

            if (id <= 0 || idEndereco <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Empresa empresaExistente = empresaDAO.buscarporId(id);

            if (empresaExistente == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }


            nome = nome.trim();
            cnpj = cnpj.trim();
            email = email.trim();
            telefone = telefone.trim();

            if (!Regex.validarCnpj(cnpj) || !Regex.validarEmail(email) || !Regex.validarTelefone(telefone)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            empresaExistente.setNome(nome);
            empresaExistente.setCnpj(cnpj);
            empresaExistente.setIdEndereco(idEndereco);
            empresaExistente.setEmail(email);
            empresaExistente.setTelefone(telefone);

            empresaDAO.atualizar(empresaExistente);

            response.sendRedirect(request.getContextPath() + "/admin/empresas");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            getServletContext().log("Erro de conexão com o banco", e);
            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro de persistência", e);
            request.setAttribute("erro", "Não foi possível atualizar a empresa.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}
