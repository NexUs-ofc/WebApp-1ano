package org.example.interdisciplinar.controller.Administrador;

import org.example.interdisciplinar.dao.AdministradorDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Administrador;
import org.example.interdisciplinar.utils.Regex;
import org.example.interdisciplinar.utils.Criptografia;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@WebServlet(name = "AtualizarAdministrador", value = "/admin/administradores/editar")
public class ServletAtualizarAdministrador extends HttpServlet {

    private AdministradorDAO administradorDAO;

    @Override
    public void init() {
        administradorDAO = new AdministradorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String idTexto = request.getParameter("id");

            if (idTexto == null || idTexto.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            int id = Integer.parseInt(idTexto);

            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Administrador administrador = administradorDAO.buscarporId(id);

            if (administrador == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            request.setAttribute("administrador", administrador);

            request.getRequestDispatcher("/admin/administradores").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            getServletContext().log("Erro de conexão com o banco", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro de persistência", e);

            request.setAttribute("erro", "Não foi possível buscar o administrador pelo ID.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            String idTexto = request.getParameter("id");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String telefone = request.getParameter("telefone");
            String cpf = request.getParameter("cpf");
            String urlFoto = request.getParameter("urlFoto");

            if (idTexto == null || idTexto.isBlank() ||
                    nome == null || nome.isBlank() ||
                    email == null || email.isBlank() ||
                    telefone == null || telefone.isBlank() ||
                    cpf == null || cpf.isBlank()) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Preencha todos os campos obrigatórios");
                return;
            }

            int id = Integer.parseInt(idTexto.trim());
            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Administrador administradorExistente = administradorDAO.buscarporId(id);
            if (administradorExistente == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            nome = nome.trim();
            email = email.trim();
            telefone = telefone.trim();
            cpf = cpf.trim();
            urlFoto = urlFoto == null ? "" : urlFoto.trim();

            if (!Regex.validarTelefone(telefone) ||
                    !Regex.validarEmail(email) ||
                    !Regex.validarCpf(cpf)) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Um ou mais campos possuem valores inválidos");
                return;
            }


            if (!urlFoto.isBlank()) {
                try {
                    URI uri = URI.create(urlFoto);

                    if (!uri.isAbsolute() ||
                            uri.getHost() == null ||
                            (!"https".equalsIgnoreCase(uri.getScheme()) &&
                                    !"http".equalsIgnoreCase(uri.getScheme()))) {

                        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                                "URL da foto inválida");
                        return;
                    }

                } catch (IllegalArgumentException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "URL da foto inválida");
                    return;
                }
            } else {
                urlFoto = administradorExistente.getUrlFoto();
            }

            String senhaCriptografada = administradorExistente.getSenha();

            if (senha != null && !senha.isBlank()) {

                if (!Regex.validarSenha(senha)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "A senha não atende aos requisitos");
                    return;
                }

                senhaCriptografada = Criptografia.criptografar(senha);
            }

            Administrador administrador = new Administrador();

            administrador.setId(id);
            administrador.setNome(nome);
            administrador.setEmail(email);
            administrador.setSenha(senhaCriptografada);
            administrador.setTelefone(telefone);
            administrador.setCpf(cpf);
            administrador.setUrlFoto(urlFoto);

            administradorDAO.atualizar(administrador);

            response.sendRedirect(request.getContextPath() + "/admin/administradores");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            getServletContext().log("Erro de conexão com o banco", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro de persistência", e);

            request.setAttribute("erro", "Não foi possível atualizar o administrador.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}
