
package org.example.interdisciplinar.controller.Administrador;

import org.example.interdisciplinar.dao.AdministradorDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Administrador;
import org.example.interdisciplinar.utils.Regex;
import org.example.interdisciplinar.utils.Criptografia;
import org.example.interdisciplinar.utils.Cloudinary;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;

@WebServlet(
        name = "AtualizarAdministrador",
        value = {"/administradores/atualizar", "/admin/administradores/editar"}
)
@MultipartConfig(maxFileSize = 5242880, maxRequestSize = 6291456)
public class ServletAtualizarAdministrador extends HttpServlet {

    private AdministradorDAO administradorDAO;

    @Override
    public void init() {
        administradorDAO = new AdministradorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

            request.getRequestDispatcher("/administradores")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException | ErroPersistenciaException e) {
            getServletContext().log("Erro ao buscar administrador", e);

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível buscar o administrador.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String idTexto = request.getParameter("id");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String ddd = request.getParameter("ddd");
            String telefone = request.getParameter("telefone");

            // Nome original do campo de arquivo na JSP.
            Part arquivo = request.getPart("fotoPerfilArquivo");

            if (idTexto == null || idTexto.isBlank()
                    || nome == null || nome.isBlank()
                    || email == null || email.isBlank()) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Preencha todos os campos obrigatórios.");
                return;
            }

            int id = Integer.parseInt(idTexto.trim());

            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "ID do administrador inválido.");
                return;
            }

            Administrador administradorExistente =
                    administradorDAO.buscarporId(id);

            if (administradorExistente == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            nome = nome.trim();
            email = email.trim();

            // Junta o DDD e o telefone, quando ambos são informados.
            if (ddd != null && !ddd.isBlank()
                    && telefone != null && !telefone.isBlank()) {

                telefone = ddd.trim() + telefone.trim();
            }

            // Mantém o telefone anterior se o formulário não o informar.
            if (telefone == null || telefone.isBlank()) {
                telefone = administradorExistente.getTelefone();
            }

            if (!Regex.validarEmail(email)
                    || telefone == null
                    || !Regex.validarTelefone(telefone)) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "E-mail ou telefone inválido.");
                return;
            }

            // A senha só será alterada quando uma nova for informada.
            String senhaCriptografada = administradorExistente.getSenha();

            if (senha != null && !senha.isBlank()) {

                if (!Regex.validarSenha(senha)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "A senha não atende aos requisitos.");
                    return;
                }

                senhaCriptografada = Criptografia.criptografar(senha);
            }

            // Preserva a foto anterior quando não existe um novo arquivo.
            String urlFoto = administradorExistente.getUrlFoto();

            if (arquivo != null
                    && arquivo.getSize() > 0
                    && arquivo.getSubmittedFileName() != null
                    && !arquivo.getSubmittedFileName().isBlank()) {

                urlFoto = Cloudinary.enviarFotoAdministrador(arquivo);
            }

            Administrador administrador = new Administrador();

            administrador.setId(id);
            administrador.setNome(nome);
            administrador.setEmail(email);
            administrador.setSenha(senhaCriptografada);
            administrador.setTelefone(telefone);
            administrador.setUrlFoto(urlFoto);

            if (!administradorDAO.atualizar(administrador)) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Não foi possível atualizar o administrador.");
                return;
            }

            // Atualiza a sessão se a conta editada for a conta logada.
            HttpSession sessao = request.getSession(false);

            if (sessao != null) {
                Object administradorLogado =
                        sessao.getAttribute("administrador");

                if (administradorLogado instanceof Administrador
                        && ((Administrador) administradorLogado).getId() == id) {

                    sessao.setAttribute("administrador", administrador);
                    sessao.setAttribute("administradorNome", nome);
                    sessao.setAttribute("administradorFoto", urlFoto);
                }
            }

            response.sendRedirect(request.getContextPath()
                    + "/administradores");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "ID do administrador inválido.");

        } catch (FalhaConexaoException | ErroPersistenciaException e) {
            getServletContext().log("Erro ao atualizar administrador", e);

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível atualizar o administrador.");

        } catch (IllegalStateException e) {
            getServletContext().log("Erro no tamanho do arquivo", e);

            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "A imagem excede o tamanho permitido.");

        } catch (IOException | ServletException e) {
            getServletContext().log("Erro ao enviar a foto", e);

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível processar a fotografia.");
        }
    }
}
