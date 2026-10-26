package org.example.interdisciplinar.controller.Alimento;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.AlimentoDAO;
import org.example.interdisciplinar.dao.UsuarioDAO;
import org.example.interdisciplinar.model.Usuario;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListarAlimentos", value = "/alimentos")
public class ServletListarAlimento extends HttpServlet {
    private AlimentoDAO alimentoDAO;

    @Override
    public void init(){
        alimentoDAO = new AlimentoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer idInicio = null;
        Integer idFim = null;
        String idInicioTexto = request.getParameter("idInicio");
        String idFimTexto = request.getParameter("idFim");
        String nome = request.getParameter("nome");
        String marca = request.getParameter("marca");
        String categoria = request.getParameter("categoria");


    }
    public void destroy(){
        GerenteConexao.desconectar();
    }
}
