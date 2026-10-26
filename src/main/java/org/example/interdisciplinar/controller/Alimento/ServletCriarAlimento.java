package org.example.interdisciplinar.controller.Alimento;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.AlimentoDAO;
import org.example.interdisciplinar.model.Alimento;

import java.io.IOException;


// Definindo um nome interno para o servlet e a url que ativa ele
@WebServlet(name = "CriarAlimentos", value = "/alimentos/insert")


public class ServletCriarAlimento extends HttpServlet {
    private AlimentoDAO alimentoDAO;

    @Override
    public void init(){
        alimentoDAO = new AlimentoDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                "/WEB-INF/Views/alimentos.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer idCategoria = null;
        String codigoBarras = request.getParameter("codigoBarras");
        String nome = request.getParameter("nome");
        String marca = request.getParameter("marca");
        String idCategoriaTexto = request.getParameter("idCategoria");

        if (codigoBarras == null || codigoBarras.isBlank()
                || nome == null || nome.isBlank()
                || marca == null || marca.isBlank()){

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Todos os campos precisam ser preenchidos!");
            request.getRequestDispatcher("/alimentos").forward(request, response);
            return;
        }

        if(idCategoriaTexto != null && !idCategoriaTexto.isBlank()){
            idCategoria = Integer.parseInt(idCategoriaTexto);
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "O id da categoria deve ser um número inteiro!");
        }



    }

    @Override
    public void destroy(){
        GerenteConexao.desconectar();
    }

}

