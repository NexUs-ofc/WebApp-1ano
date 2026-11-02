package org.example.interdisciplinar.controller.Endereco;

import org.example.interdisciplinar.dao.EnderecoDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Endereco;
import org.example.interdisciplinar.utils.Regex;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="AtualizarEndereco", value="admin/enderecos/editar")
public class ServletAtualizarEndereco extends HttpServlet {

    private EnderecoDAO enderecoDAO;

    @Override
    public void init() {
        enderecoDAO = new EnderecoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

            Endereco endereco = enderecoDAO.buscarporId(id);

            if (endereco == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            request.setAttribute("endereco", endereco);

            request.getRequestDispatcher("/enderecos").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
                getServletContext().log("Erro de conexão com o banco", e);

                request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro de persistência", e);
            request.setAttribute("erro", "Não foi possível realizar a operação.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String idParametro = request.getParameter("id");
            String rua = request.getParameter("rua");
            String numero = request.getParameter("numero");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            String cep = request.getParameter("cep");

            if (idParametro == null || idParametro.isBlank() ||
                    rua == null || rua.isBlank() ||
                    numero == null || numero.isBlank() ||
                    bairro == null || bairro.isBlank() ||
                    cidade == null || cidade.isBlank() ||
                    estado == null || estado.isBlank() ||
                    cep == null || cep.isBlank()) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Preencha todos os campos");
                return;
            }

            int id = Integer.parseInt(idParametro);

            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            rua = rua.trim();
            numero = numero.trim();
            bairro = bairro.trim();
            cidade = cidade.trim();
            estado = estado.trim().toUpperCase();
            cep = cep.trim();

            if (!Regex.validarCep(cep)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "O formato do cep está inválido");
                return;
            }

            Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, cep);

            endereco.setId(id);

            enderecoDAO.atualizar(endereco);

            response.sendRedirect(request.getContextPath() + "/enderecos");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            getServletContext().log("Erro de conexão com o banco", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/erro.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro de persistência", e);

            request.setAttribute("erro", "Não foi possível realizar a operação.");
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        }
    }
}
