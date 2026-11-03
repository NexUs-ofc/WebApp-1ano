package org.example.interdisciplinar.controller.Empresa;

import org.example.interdisciplinar.dao.EmpresaDAO;
import org.example.interdisciplinar.model.Empresa;
import org.example.interdisciplinar.utils.Regex;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CriarEmpresa", value = "/empresas/criar")
public class ServletCriarEmpresa extends HttpServlet {

    private EmpresaDAO empresaDAO;

    @Override
    public void init() {
        empresaDAO = new EmpresaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String cnpj = request.getParameter("cnpj");
        String idEndereco = request.getParameter("idEndereco");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");

        if (nome == null || nome.isBlank()
                || cnpj == null || cnpj.isBlank()
                || idEndereco == null || idEndereco.isBlank()
                || email == null || email.isBlank()
                || telefone == null || telefone.isBlank()) {

            request.setAttribute("erro", "Preencha todos os campos.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }

        nome = nome.trim();
        cnpj = cnpj.trim();
        email = email.trim();
        telefone = telefone.trim();

        if (!Regex.validarCnpj(cnpj)
                || !Regex.validarEmail(email)
                || !Regex.validarTelefone(telefone)) {

            request.setAttribute("erro", "Verifique os dados informados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }

        try {
            int idEnderecoConvertido = Integer.parseInt(idEndereco);

            if (idEnderecoConvertido <= 0) {
                request.setAttribute("erro", "Endereço inválido.");
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                return;
            }

            Empresa empresa = new Empresa();
            empresa.setNome(nome);
            empresa.setCnpj(cnpj);
            empresa.setIdEndereco(idEnderecoConvertido);
            empresa.setEmail(email);
            empresa.setTelefone(telefone);

            boolean inserido = empresaDAO.inserir(empresa);

            if (inserido) {
                response.sendRedirect(request.getContextPath() + "/empresas");
            } else {
                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Não foi possível cadastrar a empresa."
                );
            }

        } catch (NumberFormatException e) {
            request.setAttribute("erro", "ID do endereço inválido.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (FalhaConexaoException e) {
            getServletContext().log("Falha na conexão com o banco de dados.", e);
            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro ao criar empresa.", e);
            request.setAttribute("erro", "Não foi possível cadastrar a empresa.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}