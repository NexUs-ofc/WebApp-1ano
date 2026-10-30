package org.example.interdisciplinar.controller.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.UsuarioDAO;
import org.example.interdisciplinar.model.Usuario;

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
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                "/WEB-INF/Views/usuarios.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void destroy(){
        GerenteConexao.desconectar();
    }

}

