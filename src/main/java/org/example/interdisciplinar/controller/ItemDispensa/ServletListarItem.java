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
        try {


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
            String ordenar = request.getParameter("ordenar");

            if(idInicioTexto != null && !idInicioTexto.isBlank()){
                idInicio = Integer.parseInt(idInicioTexto);
            }
            if(idFimTexto != null && !idFimTexto.isBlank()){
                idFim = Integer.parseInt(idFimTexto);
            }
            if(qtdMinimaTexto != null && !qtdMinimaTexto.isBlank()){
                qtdMinima = Integer.parseInt(qtdMinimaTexto);
            }
            if(qtdMaximaTexto != null && !qtdMaximaTexto.isBlank()){
                qtdMaxima = Integer.parseInt(qtdMaximaTexto);
            }
            if(dataInicioTexto != null && !dataInicioTexto.isBlank()){
                dataInicio = LocalDate.parse(dataInicioTexto);
            }
            if(dataFimTexto != null && !dataFimTexto.isBlank()){
                dataFim = LocalDate.parse(dataFimTexto);
            }

            if (idInicio != null && idFim != null && idInicio > idFim) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "O ID inicial não pode ser maior que o ID final."
                );
                return;
            }

            if (qtdMinima != null && qtdMaxima != null && qtdMinima > qtdMaxima) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "A quantidade mínima não pode ser maior que a máxima."
                );
                return;
            }

            if (dataInicio != null && dataFim != null && dataInicio.isAfter(dataFim)) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "A data de início não pode ser maior que a data de fim"
                );
                return;
            }

            List<ItemDespensa> itens = itemDispensaDAO.listarFiltrado(idInicio, idFim, qtdMinima, qtdMaxima, dataInicio, dataFim, ordenar);
            request.setAttribute("itens", itens);
            request.getRequestDispatcher("/WEB-INF/Views/itensDespensa.jsp").forward(request, response);

        } catch (NumberFormatException nfe){
            request.setAttribute("erro", "O ID dos itens precisa ser um número inteiro");
            request.getRequestDispatcher("/WEB-INF/Views/ErrorPage.jsp").forward(request, response);

        } catch (RuntimeException re) {
            request.setAttribute("erro", "Não foi possível carregar os itens. Tente novamente.");
            request.getRequestDispatcher("/WEB-INF/Views/ErrorPage.jsp").forward(request, response);
        }
    }

    public void destroy(){
        GerenteConexao.desconectar();
    }
}
