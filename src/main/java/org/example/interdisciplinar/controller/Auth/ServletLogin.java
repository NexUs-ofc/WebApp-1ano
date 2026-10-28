package org.example.interdisciplinar.controller.Auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.AdministradorDAO;

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
    }
}
