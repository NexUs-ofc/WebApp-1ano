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
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");

        String idCasaTexto = request.getParameter("idCasa");
        String idAlimentoTexto = request.getParameter("idAlimento");
        String quantidadeTexto = request.getParameter("quantidade");
        String validadeTexto = request.getParameter("validade");


        // Converte a categoria para inteiro
        int idCasa;
        int idAlimento;
        int quantidade;
        Date validade;

        try {
            idCasa = Integer.parseInt(idCasaTexto);
        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "O ID da casa deve ser numérico."
            );
            return;
        }

        try {
            idAlimento = Integer.parseInt(idAlimentoTexto);

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "O ID do alimento deve ser numérico."
            );
            return;
        }

        try {
            quantidade = Integer.parseInt(quantidadeTexto);

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "A quantidade deve ser numérica."
            );
            return;
        }



        // Cria o objeto Alimento
        Alimento alimento = new Alimento();

        alimento.setCodigoBarras(codigoBarras);
        alimento.setNome(nome);
        alimento.setMarca(marca);
        alimento.setIdCategoria(idCategoria);

        // Chama o DAO para inserir no banco

        boolean inserido = alimentoDAO.inserir(alimento);

        if (inserido) {

            // Redireciona para a listagem
            response.sendRedirect(
                    request.getContextPath() + "/alimentos"
            );

        } else {

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível inserir o alimento."
            );
        }

    }

    @Override
    public void destroy(){
        GerenteConexao.desconectar();
    }

}
