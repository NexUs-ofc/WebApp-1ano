package org.example.interdisciplinar.controller.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.UsuarioDAO;
import org.example.interdisciplinar.model.Alimento;
import org.example.interdisciplinar.model.ConfiguracaoAlerta;
import org.example.interdisciplinar.model.Usuario;
import org.example.interdisciplinar.utils.Regex;

import java.io.IOException;


// Definindo um nome interno para o servlet e a url que ativa ele
@WebServlet(name = "CriarUsuarios", value = "/usuarios/insert")


public class ServletCriarUsuario extends HttpServlet {
    private UsuarioDAO usuarioDAO;

    @Override
    public void init(){
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String preferencias = request.getParameter("preferencias");
        String configAlerta = request.getParameter("configuracao");
        String telefone = request.getParameter("telefone");
        String senha = request.getParameter("senha");

        if(
                nome == null || nome.isBlank() ||
                email == null || email.isBlank() ||
                preferencias == null || preferencias.isBlank() ||
                configAlerta == null || configAlerta.isBlank() ||
                telefone == null || telefone.isBlank() ||
                senha == null || senha.isBlank()
        ){
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

        if (!Regex.validarTelefone(telefone)) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Telefone com formato inválido."
            );
            return;
        }

        ConfiguracaoAlerta configuracao = ConfiguracaoAlerta.valueOf(configAlerta);
        Usuario usuario = new Usuario(nome, email, preferencias, configuracao, telefone, senha);
        boolean inserido = usuarioDAO.inserir(usuario);

        if(inserido){
            // Redireciona para a listagem
            response.sendRedirect(
                    request.getContextPath() + "/alimentos"
            );

        } else {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível inserir o alimento."
            );
        }


    }

}
