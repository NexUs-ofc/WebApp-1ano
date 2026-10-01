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
import java.util.List;

@WebServlet(name = "ListarUsuarios", value = "/usuarios")
public class ServletListarUsuario extends HttpServlet {
    private UsuarioDAO usuarioDAO;

    @Override
    public void init(){
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> usuarios = usuarioDAO.listar();
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/WEB-INF/Views/usuarios.jsp").forward(request, response);
    }

    public void destroy(){
        GerenteConexao.desconectar();
    }
}
