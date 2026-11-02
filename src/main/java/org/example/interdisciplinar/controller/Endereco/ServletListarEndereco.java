package org.example.interdisciplinar.controller.Endereco;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.EnderecoDAO;
import org.example.interdisciplinar.model.Endereco;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListarEnderecos", value = "/enderecos")
public class ServletListarEndereco extends HttpServlet {
    private EnderecoDAO enderecoDAO;

    @Override
    public void init(){
        enderecoDAO = new EnderecoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Endereco> enderecos = enderecoDAO.listar();
        request.setAttribute("enderecos", enderecos);
        request.getRequestDispatcher("/WEB-INF/Views/enderecos.jsp").forward(request, response);
    }

    public void destroy(){
        GerenteConexao.desconectar();
    }
}
