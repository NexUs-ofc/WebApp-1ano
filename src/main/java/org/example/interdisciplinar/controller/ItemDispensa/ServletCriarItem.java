package org.example.interdisciplinar.controller.ItemDispensa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.AlimentoDAO;
import org.example.interdisciplinar.dao.ItemDispensaDAO;
import org.example.interdisciplinar.model.ItemDispensa;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

// Definindo um nome interno para o servlet e a url que ativa ele
@WebServlet(name = "CriarItem", value = "/itens/insert")


public class ServletCriarItem extends HttpServlet {
    private ItemDispensaDAO itemDispensaDAO;

    @Override
    public void init(){
        itemDispensaDAO = new ItemDispensaDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                "/WEB-INF/Views/itensDespensa.jsp"
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
        LocalDate validade;

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

        try {
            validade = LocalDate.parse(validadeTexto);
        } catch (Exception e){
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST, "A data precisa ser em formato de data."
            );
            return;
        }



        // Cria o objeto Alimento
        ItemDispensa itemDispensa = new ItemDispensa();
        itemDispensa.setIdCasa(idCasa);
        itemDispensa.setIdAlimento(idAlimento);
        itemDispensa.setQuantidade(quantidade);
        itemDispensa.setValidade(validade);

        // Chama o DAO para inserir no banco

        boolean inserido = itemDispensaDAO.inserir(itemDispensa);

        if (inserido) {

            // Redireciona para a listagem
            response.sendRedirect(
                    request.getContextPath() + "/itens"
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
