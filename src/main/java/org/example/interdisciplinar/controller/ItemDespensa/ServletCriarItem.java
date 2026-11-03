package org.example.interdisciplinar.controller.ItemDespensa;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import org.example.interdisciplinar.dao.ItemDespensaDAO;
import org.example.interdisciplinar.model.ItemDespensa;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// Define o endereço responsável pelo cadastro dos itens da despensa
@WebServlet(name = "CriarItem", value = "/itens/insert")
public class ServletCriarItem extends HttpServlet {

    private ItemDespensaDAO itemDespensaDAO;

    // Método que inicializa a DAO
    @Override
    public void init(){
        itemDespensaDAO = new ItemDespensaDAO();
    }

    // Método que recebe os dados do formulário e cadastra um item
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");

        // Recupera os dados enviados pelo formulário
        String idUsuarioTexto = request.getParameter("idUsuario");
        String idAlimentoTexto = request.getParameter("idAlimento");
        String quantidadeTexto = request.getParameter("quantidade");
        String validadeTexto = request.getParameter("validade");

        // Verifica se todos os campos foram preenchidos
        if (idUsuarioTexto == null || idUsuarioTexto.isBlank()
                || idAlimentoTexto == null || idAlimentoTexto.isBlank()
                || quantidadeTexto == null || quantidadeTexto.isBlank()
                || validadeTexto == null || validadeTexto.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Preencha todos os campos"
            );
            return;
        }

        try {
            // Converte os valores recebidos para seus respectivos tipos
            int idUsuario = Integer.parseInt(idUsuarioTexto.trim());
            int idAlimento = Integer.parseInt(idAlimentoTexto.trim());
            int quantidade = Integer.parseInt(quantidadeTexto.trim());
            LocalDate validade = LocalDate.parse(validadeTexto.trim());

            // Verifica se os IDs e a quantidade são válidos
            if (idUsuario <= 0 || idAlimento <= 0 || quantidade <= 0) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Os IDs e a quantidade precisam ser maiores que zero"
                );
                return;
            }

            // Cria o objeto item da despensa com os dados recebidos
            ItemDespensa itemDespensa = new ItemDespensa();
            itemDespensa.setIdUsuario(idUsuario);
            itemDespensa.setIdAlimento(idAlimento);
            itemDespensa.setQuantidade(quantidade);
            itemDespensa.setValidade(validade);

            // Envia o item para ser cadastrado no banco
            boolean inserido = itemDespensaDAO.inserir(itemDespensa);

            // Verifica se o cadastro foi realizado
            if (inserido) {

                // Redireciona para a listagem após o cadastro
                response.sendRedirect(
                        request.getContextPath() + "/itens"
                );

            } else {
                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Não foi possível cadastrar o item da despensa"
                );
            }

        } catch (NumberFormatException e) {
            // Registra o erro de conversão no log
            getServletContext().log("Erro ao converter os dados numéricos", e);

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Os IDs e a quantidade precisam ser números inteiros"
            );

        } catch (DateTimeParseException e) {
            // Registra o erro de conversão da data no log
            getServletContext().log("Erro ao converter a data de validade", e);

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Formato de validade inválido"
            );

        } catch (FalhaConexaoException e) {
            // Registra os detalhes da falha de conexão no log
            getServletContext().log("Falha ao conectar ao banco de dados", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao cadastrar item da despensa", e);

            request.setAttribute("erro", "Não foi possível cadastrar o item da despensa.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}