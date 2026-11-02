package org.example.interdisciplinar.controller.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.dao.UsuarioDAO;
import org.example.interdisciplinar.model.ConfiguracaoAlerta;
import org.example.interdisciplinar.model.Usuario;
import org.example.interdisciplinar.utils.Regex;

import java.io.IOException;


@WebServlet(name = "CriarUsuarios", value = "/usuarios/insert")
public class ServletCriarUsuario extends HttpServlet {
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String preferencias = request.getParameter("preferenciasConsumo");
        String configAlerta = request.getParameter("configuracaoAlerta");
        String senha = request.getParameter("senha");

        if (
                nome == null || nome.isBlank() ||
                email == null || email.isBlank() ||
                preferencias == null || preferencias.isBlank() ||
                configAlerta == null || configAlerta.isBlank() ||
                senha == null || senha.isBlank()
        ) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Todos os campos precisam ser preenchidos!");
            return;
        }

        if (!Regex.validarSenha(senha)) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Senha com formato inválido."
            );
            return;
        }

        if (!Regex.validarEmail(email)) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Email com formato inválido."
            );
            return;
        }

        ConfiguracaoAlerta configuracao;

        try {
            configuracao = ConfiguracaoAlerta.valueOf(configAlerta.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Configuração de alerta inválida."
            );
            return;
        }

        Usuario usuario = new Usuario(nome, email, preferencias, configuracao, senha, null);
        boolean inserido = usuarioDAO.inserir(usuario);

        if (inserido) {
            response.sendRedirect(request.getContextPath() + "/usuarios");
        } else {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível inserir o usuário."
            );
        }
    }

}
