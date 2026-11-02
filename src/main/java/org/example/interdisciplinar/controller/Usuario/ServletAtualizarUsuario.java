package org.example.interdisciplinar.controller.Usuario;

import org.example.interdisciplinar.dao.UsuarioDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Usuario;
import org.example.interdisciplinar.model.ConfiguracaoAlerta;
import org.example.interdisciplinar.utils.Regex;
import org.example.interdisciplinar.utils.Criptografia;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AtualizarUsuario", value = "/admin/usuarios/editar")
public class ServletAtualizarUsuario extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
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

            Usuario usuario = usuarioDAO.buscarporId(id);

            if (usuario == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            request.setAttribute("usuario", usuario);

            request.getRequestDispatcher("/admin/usuarios").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            getServletContext().log("Erro de conexão com o banco", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro de persistência", e);

            request.setAttribute("erro", "Não foi possível buscar o usuário pelo ID.");
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
            String preferenciasConsumo = request.getParameter("preferenciasConsumo");
            String configuracaoAlertaTexto = request.getParameter("configuracaoAlerta");
            String telefone = request.getParameter("telefone");
            String senha = request.getParameter("senha");

            if (idTexto == null || idTexto.isBlank() ||
                    nome == null || nome.isBlank() ||
                    email == null || email.isBlank() ||
                    preferenciasConsumo == null || preferenciasConsumo.isBlank() ||
                    configuracaoAlertaTexto == null || configuracaoAlertaTexto.isBlank() ||
                    telefone == null || telefone.isBlank()) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Preencha todos os campos obrigatórios");
                return;
            }

            int id = Integer.parseInt(idTexto.trim());

            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Usuario usuario = usuarioDAO.buscarporId(id);

            if (usuario == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            nome = nome.trim();
            email = email.trim();
            preferenciasConsumo = preferenciasConsumo.trim();
            configuracaoAlertaTexto = configuracaoAlertaTexto.trim();
            telefone = telefone.trim();

            if (!Regex.validarEmail(email) || !Regex.validarTelefone(telefone)) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Um ou mais campos possuem valores inválidos");
                return;
            }

            ConfiguracaoAlerta configuracaoAlerta;

            try {
                configuracaoAlerta = ConfiguracaoAlerta.valueOf(configuracaoAlertaTexto);
            } catch (IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Configuração de alerta inválida");
                return;
            }

            String senhaCriptografada = usuario.getSenha();

            if (senha != null && !senha.isBlank()) {

                if (!Regex.validarSenha(senha)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "A senha informada não atende aos requisitos");
                    return;
                }

                senhaCriptografada = Criptografia.criptografar(senha);
            }

            Usuario usuarioAtualizado = new Usuario();

            usuarioAtualizado.setId(id);
            usuarioAtualizado.setNome(nome);
            usuarioAtualizado.setEmail(email);
            usuarioAtualizado.setPreferenciasConsumo(preferenciasConsumo);
            usuarioAtualizado.setConfiguracaoAlerta(configuracaoAlerta);
            usuarioAtualizado.setTelefone(telefone);
            usuarioAtualizado.setSenha(senhaCriptografada);

            usuarioDAO.atualizar(usuarioAtualizado);

            response.sendRedirect(request.getContextPath() + "/admin/usuarios");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            getServletContext().log("Erro de conexão com o banco", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            getServletContext().log("Erro de persistência", e);

            request.setAttribute("erro", "Não foi possível atualizar o usuário.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}
