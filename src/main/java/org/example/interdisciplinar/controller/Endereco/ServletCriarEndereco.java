package org.example.interdisciplinar.controller.Endereco;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.EnderecoDAO;
import org.example.interdisciplinar.model.Categoria;
import org.example.interdisciplinar.model.Endereco;
import org.example.interdisciplinar.utils.Regex;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CriarEnderecos", value = "/enderecos/insert")
public class ServletCriarEndereco extends HttpServlet {
    private EnderecoDAO enderecoDAO;

    @Override
    public void init(){
        enderecoDAO = new EnderecoDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String cep = request.getParameter("cep");
        String rua = request.getParameter("rua");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String numero = request.getParameter("numero");

        if(
                cep == null || cep.isBlank()
                || rua == null || rua.isBlank()
                || bairro == null || bairro.isBlank()
                || cidade == null || cidade.isBlank()
                || numero == null || numero.isBlank()
                || estado == null || estado.isBlank()
        ){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Preencha todos os campos");
            response.sendRedirect(request.getContextPath() + "/enderecos");
        }

        if (!Regex.validarCep(cep)){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "O formato do cep está inválido");
        }

        Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, cep);

        boolean inserido = enderecoDAO.inserir(endereco);

        if (inserido) {

            response.sendRedirect(
                    request.getContextPath() + "/enderecos"
            );

        } else {

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível inserir a categoria"
            );
        }
    }

}
