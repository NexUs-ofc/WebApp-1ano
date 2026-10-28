package org.example.interdisciplinar.controller.Auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.AdministradorDAO;
import org.example.interdisciplinar.model.Administrador;
import org.example.interdisciplinar.utils.Criptografia;
import org.example.interdisciplinar.utils.Regex;

import java.io.IOException;

public class ServletLogin extends HttpServlet {
    private AdministradorDAO administradorDAO;

    @Override
    public void init(){
        administradorDAO = new AdministradorDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if(email != null && !email.isBlank() && senha != null && !senha.isBlank()){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Preencha todos os campos");
            return;
        }

        if(!Regex.validarEmail(email) || !Regex.validarSenha(senha)){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de email ou senha inválido");
            return;
        }

        Administrador administrador = administradorDAO.buscarPorEmail(email);
        if (administrador == null){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Email ou senha incorretos");
            return;
        }

        String senhaAdmin = administrador.getSenha();
        if(!Criptografia.verificar(senha, senhaAdmin)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Email ou senha incorretos.");
        }

        HttpSession sessao = request.getSession();
        sessao.setAttribute("administrador", administrador);

        response.sendRedirect(request.getContextPath() + "/analises");

    }
}
