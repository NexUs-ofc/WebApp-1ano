package org.example.interdisciplinar.controller.ItemDespensa;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import org.example.interdisciplinar.dao.ItemDespensaDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.ItemDespensa;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// Define o endereço responsável pela atualização dos itens da despensa
@WebServlet(name = "AtualizarItem", value = "/admin/itens/editar")
public class ServletAtualizarItem extends HttpServlet {

    private ItemDespensaDAO itemDespensaDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        itemDespensaDAO = new ItemDespensaDAO();
    }

    // Método que busca um item para preencher o formulário de edição
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // Recupera o ID enviado pela página
            String idItemTexto = request.getParameter("id");

            // Verifica se o ID foi informado
            if (idItemTexto == null || idItemTexto.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Converte o ID para inteiro
            int idItem = Integer.parseInt(idItemTexto.trim());

            // Verifica se o ID é válido
            if (idItem <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Busca o item pelo ID
            ItemDespensa itemDespensa = itemDespensaDAO.buscarporId(idItem);

            // Verifica se o item foi encontrado
            if (itemDespensa == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Envia o item encontrado para a página
            request.setAttribute("itemDespensa", itemDespensa);

            // Encaminha para a página de itens
            request.getRequestDispatcher("/admin/itens").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            // Registra os detalhes do erro de conexão no log
            getServletContext().log("Erro de conexão com o banco", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao buscar item da despensa", e);

            request.setAttribute("erro", "Não foi possível buscar o item pelo ID.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }

    // Método que recebe os dados do formulário e atualiza um item da despensa
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            // Recupera os dados enviados pelo formulário
            String idParametro = request.getParameter("id");
            String idUsuarioTexto = request.getParameter("idCasa");
            String idAlimentoTexto = request.getParameter("idAlimento");
            String quantidadeTexto = request.getParameter("quantidade");
            String validadeTexto = request.getParameter("validade");

            // Verifica se todos os campos foram preenchidos
            if (idParametro == null || idParametro.isBlank()
                    || idUsuarioTexto == null || idUsuarioTexto.isBlank()
                    || idAlimentoTexto == null || idAlimentoTexto.isBlank()
                    || quantidadeTexto == null || quantidadeTexto.isBlank()
                    || validadeTexto == null || validadeTexto.isBlank()) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Preencha todos os campos");
                return;
            }

            // Converte os valores recebidos para seus respectivos tipos
            int id = Integer.parseInt(idParametro.trim());
            int idUsuario = Integer.parseInt(idUsuarioTexto.trim());
            int idAlimento = Integer.parseInt(idAlimentoTexto.trim());
            int quantidade = Integer.parseInt(quantidadeTexto.trim());
            LocalDate validade = LocalDate.parse(validadeTexto.trim());

            // Verifica se os IDs e a quantidade são válidos
            if (id <= 0 || idUsuario <= 0 || idAlimento <= 0 || quantidade <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Busca o item que será atualizado
            ItemDespensa itemExistente = itemDespensaDAO.buscarporId(id);

            // Verifica se o item existe no banco
            if (itemExistente == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Atualiza os dados do item encontrado
            itemExistente.setIdUsuario(idUsuario);
            itemExistente.setIdAlimento(idAlimento);
            itemExistente.setQuantidade(quantidade);
            itemExistente.setValidade(validade);

            // Envia os novos dados para a DAO atualizar no banco
            boolean atualizado = itemDespensaDAO.atualizar(itemExistente);

            // Verifica se a atualização foi realizada
            if (!atualizado) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Não foi possível encontrar o item para atualização"
                );
                return;
            }

            // Redireciona para a listagem após a atualização
            response.sendRedirect(request.getContextPath() + "/admin/itens");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Um ou mais campos numéricos são inválidos");

        } catch (DateTimeParseException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Formato de validade inválido"
            );

        } catch (FalhaConexaoException e) {
            // Registra os detalhes do erro de conexão no log
            getServletContext().log("Erro de conexão com o banco", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao atualizar item da despensa", e);

            request.setAttribute("erro", "Não foi possível atualizar o item da despensa.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}