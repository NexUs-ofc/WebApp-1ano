package org.example.interdisciplinar.controller.Auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.interdisciplinar.conexao.GerenteConexao;

import java.io.IOException;

public class ServletLogout extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/index.html");
    }

    public void destroy(){
        GerenteConexao.desconectar();
    }
}
