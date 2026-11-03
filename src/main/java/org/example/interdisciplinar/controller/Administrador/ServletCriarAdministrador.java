
package org.example.interdisciplinar.controller.Administrador;

import org.example.interdisciplinar.dao.AdministradorDAO;
import org.example.interdisciplinar.model.Administrador;
import org.example.interdisciplinar.utils.Regex;
import org.example.interdisciplinar.utils.Criptografia;
import org.example.interdisciplinar.utils.Cloudinary;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

@WebServlet(name = "CriarAdministrador", value = "/administradores/criar")
@MultipartConfig(maxFileSize = 5242880, maxRequestSize = 6291456)
public class ServletCriarAdministrador extends HttpServlet {

    private AdministradorDAO administradorDAO;

    @Override
    public void init() {
        administradorDAO = new AdministradorDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String telefone = request.getParameter("telefone");

            Part arquivo = request.getPart("foto");

            if (nome == null || nome.isBlank()
                    || email == null || email.isBlank()
                    || senha == null || senha.isBlank()
                    || telefone == null || telefone.isBlank()) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Preencha todos os campos obrigatórios.");
                return;
            }

            nome = nome.trim();
            email = email.trim();
            telefone = telefone.trim();

            if (!Regex.validarEmail(email)
                    || !Regex.validarSenha(senha)
                    || !Regex.validarTelefone(telefone)) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Verifique os dados informados.");
                return;
            }

            String urlFoto = Cloudinary.enviarFotoAdministrador(arquivo);
            String senhaCriptografada = Criptografia.criptografar(senha);

            Administrador administrador = new Administrador();

            administrador.setNome(nome);
            administrador.setEmail(email);
            administrador.setSenha(senhaCriptografada);
            administrador.setTelefone(telefone);
            administrador.setUrlFoto(urlFoto);

            if (administradorDAO.inserir(administrador)) {
                response.sendRedirect(request.getContextPath()
                        + "/admin/administradores");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Não foi possível cadastrar o administrador.");
            }

        } catch (FalhaConexaoException | ErroPersistenciaException e) {
            getServletContext().log("Erro ao criar administrador", e);

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível cadastrar o administrador.");

        } catch (IllegalStateException | IOException | ServletException e) {
            getServletContext().log("Erro no upload da foto", e);

            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Não foi possível enviar a foto.");
        }
    }
}
