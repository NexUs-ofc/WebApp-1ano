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
        Integer idInicio = null;
        Integer idFim = null;

        String idInicioTexto = request.getParameter("idInicio");
        String idFimTexto = request.getParameter("idFim");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String ordenar = request.getParameter("ordenar");

        if(idInicioTexto != null && !idInicioTexto.isBlank()){
            idInicio = Integer.parseInt(idInicioTexto);
        }

        if(idFimTexto != null && !idFimTexto.isBlank()){
            idFim = Integer.parseInt(idFimTexto);
        }

        if (idInicio != null && idFim != null && idInicio > idFim) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "O ID inicial não pode ser maior que o ID final."
            );
            return;
        }

        List<Usuario> usuarios = usuarioDAO.listarFiltrado(idInicio, idFim, nome, email, ordenar);
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/WEB-INF/Views/usuarios.jsp").forward(request, response);

    }

    public void destroy(){
        GerenteConexao.desconectar();
    }
}
