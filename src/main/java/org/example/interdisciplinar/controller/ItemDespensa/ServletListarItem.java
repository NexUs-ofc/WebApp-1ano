package org.example.interdisciplinar.controller.ItemDespensa;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.dao.ItemDespensaDAO;
import org.example.interdisciplinar.dto.ItemDespensaView;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

// Define o endereço responsável pela listagem dos itens da despensa
@WebServlet(name = "ListarItens", value = "/itensDispensa")
public class ServletListarItem extends HttpServlet {

    private ItemDespensaDAO itemDispensaDAO;

    // Método que inicializa a DAO
    @Override
    public void init(){
        itemDispensaDAO = new ItemDespensaDAO();
    }

    // Método que recebe os filtros e lista os itens da despensa
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        // Inicializa os filtros como nulos caso não sejam informados
        Integer idInicio = null;
        Integer idFim = null;
        Integer qtdMinima = null;
        Integer qtdMaxima = null;
        LocalDate dataInicio = null;
        LocalDate dataFim = null;

        try {

            // Recupera os filtros enviados pela página
            String idInicioTexto = request.getParameter("idInicio");
            String idFimTexto = request.getParameter("idFim");
            String qtdMinimaTexto = request.getParameter("qtdMinima");
            String qtdMaximaTexto = request.getParameter("qtdMaxima");
            String dataInicioTexto = request.getParameter("dataInicio");
            String dataFimTexto = request.getParameter("dataFim");
            String ordenar = request.getParameter("ordenar");

            // Converte o ID inicial para inteiro, caso tenha sido informado
            if(idInicioTexto != null && !idInicioTexto.isBlank()){
                idInicio = Integer.parseInt(idInicioTexto.trim());
            }

            // Converte o ID final para inteiro, caso tenha sido informado
            if(idFimTexto != null && !idFimTexto.isBlank()){
                idFim = Integer.parseInt(idFimTexto.trim());
            }

            // Converte a quantidade mínima para inteiro
            if(qtdMinimaTexto != null && !qtdMinimaTexto.isBlank()){
                qtdMinima = Integer.parseInt(qtdMinimaTexto.trim());
            }

            // Converte a quantidade máxima para inteiro
            if(qtdMaximaTexto != null && !qtdMaximaTexto.isBlank()){
                qtdMaxima = Integer.parseInt(qtdMaximaTexto.trim());
            }

            // Converte a data inicial para LocalDate
            if(dataInicioTexto != null && !dataInicioTexto.isBlank()){
                dataInicio = LocalDate.parse(dataInicioTexto.trim());
            }

            // Converte a data final para LocalDate
            if(dataFimTexto != null && !dataFimTexto.isBlank()){
                dataFim = LocalDate.parse(dataFimTexto.trim());
            }

            // Verifica se os IDs informados são válidos
            if ((idInicio != null && idInicio <= 0) ||
                    (idFim != null && idFim <= 0)) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Verifica se as quantidades informadas são válidas
            if ((qtdMinima != null && qtdMinima < 0) ||
                    (qtdMaxima != null && qtdMaxima < 0)) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Verifica se o ID inicial é maior que o ID final
            if (idInicio != null && idFim != null && idInicio > idFim) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "O ID inicial não pode ser maior que o ID final."
                );
                return;
            }

            // Verifica se a quantidade mínima é maior que a máxima
            if (qtdMinima != null && qtdMaxima != null && qtdMinima > qtdMaxima) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "A quantidade mínima não pode ser maior que a máxima."
                );
                return;
            }

            // Verifica se a data inicial é posterior à data final
            if (dataInicio != null && dataFim != null && dataInicio.isAfter(dataFim)) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "A data de início não pode ser maior que a data de fim."
                );
                return;
            }

            // Define uma ordenação padrão caso nenhuma seja informada
            if (ordenar == null || ordenar.isBlank()) {
                ordenar = "id";
            }

            // Busca os itens de acordo com os filtros informados
            List<ItemDespensaView> itens = itemDispensaDAO.listarFiltrado(
                    idInicio,
                    idFim,
                    qtdMinima,
                    qtdMaxima,
                    dataInicio,
                    dataFim,
                    ordenar
            );

            // Envia a lista de itens para a página
            request.setAttribute("itens", itens);

            // Encaminha para a página de listagem
            request.getRequestDispatcher("/WEB-INF/Views/itensDespensa.jsp").forward(request, response);

        } catch (NumberFormatException e){

            // Registra o erro de conversão no log do servidor
            getServletContext().log("Erro ao formatar os números", e);

            request.setAttribute("erro", "Os IDs e as quantidades precisam ser números inteiros");
            request.getRequestDispatcher("/WEB-INF/erro.jsp").forward(request, response);

        } catch (DateTimeParseException e) {

            // Registra o erro de conversão da data no log
            getServletContext().log("Erro ao formatar as datas", e);

            request.setAttribute("erro", "Uma ou mais datas possuem formato inválido");
            request.getRequestDispatcher("/WEB-INF/erro.jsp").forward(request, response);

        } catch (FalhaConexaoException e) {

            // Registra os detalhes da falha de conexão no log
            getServletContext().log("Falha ao conectar ao banco de dados", e);

            request.setAttribute(
                    "erro",
                    "Não foi possível estabelecer conexão com o banco de dados."
            );

            // Encaminha para a página de erro
            request.getRequestDispatcher("/WEB-INF/erro.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {

            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao listar os itens", e);

            request.setAttribute(
                    "erro",
                    "Ocorreu um erro ao listar os itens."
            );

            // Encaminha para a página de erro
            request.getRequestDispatcher("/WEB-INF/erro.jsp").forward(request, response);
        }
    }
}