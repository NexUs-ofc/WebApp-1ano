package org.example.interdisciplinar.controller.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.UsuarioDAO;
import org.example.interdisciplinar.model.Usuario;

import java.io.IOException;

public class ServletDeletarUsuario extends HttpServlet {
    private UsuarioDAO usuarioDAO;
    @Override
    public void init(){
        usuarioDAO = new UsuarioDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idUsuarioTexto = request.getParameter("id");
        int idUsuario = Integer.parseInt(idUsuarioTexto);
        usuarioDAO.deletar(idUsuario);
        response.sendRedirect(request.getContextPath() + "/usuarios");
    }

}
