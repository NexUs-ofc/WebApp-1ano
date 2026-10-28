package org.example.interdisciplinar.controller.Endereco;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.EnderecoDAO;
import org.example.interdisciplinar.model.Endereco;

import java.io.IOException;

public class ServletDeletarEndereco extends HttpServlet {
    private EnderecoDAO enderecoDAO;
    @Override
    public void init(){
        enderecoDAO = new EnderecoDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idEnderecoTexto = request.getParameter("id");
        int idEndereco = Integer.parseInt(idEnderecoTexto);
        enderecoDAO.deletar(idEndereco);
        response.sendRedirect(request.getContextPath() + "/enderecos");
    }

    public void destroy(){
        GerenteConexao.desconectar();
    }
}
