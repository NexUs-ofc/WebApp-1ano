package org.example.interdisciplinar.controller.Administrador;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.AdministradorDAO;
import org.example.interdisciplinar.model.Administrador;

import java.io.IOException;

public class ServletDeletarAdministrador extends HttpServlet {
    private AdministradorDAO administradorDAO;
    @Override
    public void init(){
        administradorDAO = new AdministradorDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String senhaTexto = request.getParameter("senha");
        administradorDAO.deletar(senhaTexto);
        response.sendRedirect(request.getContextPath() + "/administradores");
    }

    public void destroy(){
        GerenteConexao.desconectar();
    }
}
