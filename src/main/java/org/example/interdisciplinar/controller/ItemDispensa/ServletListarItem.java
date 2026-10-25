package org.example.interdisciplinar.controller.ItemDispensa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.ItemDespensaDAO;
import org.example.interdisciplinar.model.ItemDespensa;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "ListarItens", value = "/itensDispensa")
public class ServletListarItem extends HttpServlet {
    private ItemDespensaDAO itemDispensaDAO;

    @Override
    public void init(){
        itemDispensaDAO = new ItemDespensaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Integer idInicio = null;
        Integer idFim = null;
        Integer qtdMinima = null;
        Integer qtdMaxima = null;
        LocalDate dataInicio = null;
        LocalDate dataFim = null;

        String idInicioTexto = request.getParameter("idInicio");
        String idFimTexto = request.getParameter("idFim");
        String qtdMinimaTexto = request.getParameter("qtdMinima");
        String qtdMaximaTexto = request.getParameter("qtdMaxima");
        String dataInicioTexto = request.getParameter("dataInicio");
        String dataFimTexto = request.getParameter("dataFim");
    }

    public void destroy(){
        GerenteConexao.desconectar();
    }
}
