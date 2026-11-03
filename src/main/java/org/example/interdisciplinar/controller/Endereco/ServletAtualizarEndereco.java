package org.example.interdisciplinar.controller.Endereco;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import org.example.interdisciplinar.dao.EnderecoDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Endereco;
import org.example.interdisciplinar.utils.Regex;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// Define o endereço responsável pela atualização dos endereços
@WebServlet(name = "AtualizarEndereco", value = "/admin/enderecos/editar")
public class ServletAtualizarEndereco extends HttpServlet {

    private EnderecoDAO enderecoDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        enderecoDAO = new EnderecoDAO();
    }

    // Método que busca um endereço para preencher o formulário de edição
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Recupera o ID enviado pela página
            String idParametro = request.getParameter("id");

            // Verifica se o ID foi informado
            if (idParametro == null || idParametro.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Converte o ID para inteiro
            int id = Integer.parseInt(idParametro.trim());

            // Verifica se o ID é válido
            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Busca o endereço pelo ID
            Endereco endereco = enderecoDAO.buscarporId(id);

            // Verifica se o endereço foi encontrado
            if (endereco == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Envia o endereço encontrado para a página
            request.setAttribute("endereco", endereco);

            // Encaminha para a listagem de endereços
            request.getRequestDispatcher("/enderecos").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            // Registra os detalhes do erro de conexão no log
            getServletContext().log("Erro de conexão com o banco", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro de persistência", e);

            request.setAttribute("erro", "Não foi possível buscar o endereço.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }

    // Método que recebe os dados do formulário e atualiza um endereço
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // Recupera os dados enviados pelo formulário
            String idParametro = request.getParameter("id");
            String rua = request.getParameter("rua");
            String numero = request.getParameter("numero");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            String cep = request.getParameter("cep");

            // Verifica se todos os campos foram preenchidos
            if (idParametro == null || idParametro.isBlank()
                    || rua == null || rua.isBlank()
                    || numero == null || numero.isBlank()
                    || bairro == null || bairro.isBlank()
                    || cidade == null || cidade.isBlank()
                    || estado == null || estado.isBlank()
                    || cep == null || cep.isBlank()) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Preencha todos os campos");
                return;
            }

            // Converte o ID para inteiro
            int id = Integer.parseInt(idParametro.trim());

            // Verifica se o ID é válido
            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Remove espaços desnecessários dos campos
            rua = rua.trim();
            numero = numero.trim();
            bairro = bairro.trim();
            cidade = cidade.trim();
            estado = estado.trim().toUpperCase();
            cep = cep.trim();

            // Valida o CEP utilizando o método da classe Regex
            if (!Regex.validarCep(cep)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "O formato do CEP está inválido");
                return;
            }

            // Cria o objeto endereço com os novos dados
            Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, cep);
            endereco.setId(id);

            // Envia os novos dados para a DAO atualizar no banco
            boolean atualizado = enderecoDAO.atualizar(endereco);

            // Verifica se o endereço foi encontrado e atualizado
            if (!atualizado) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Endereço não encontrado");
                return;
            }

            // Redireciona para a listagem após a atualização
            response.sendRedirect(request.getContextPath() + "/enderecos");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            // Registra os detalhes do erro de conexão no log
            getServletContext().log("Erro de conexão com o banco", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro de persistência", e);

            request.setAttribute("erro", "Não foi possível atualizar o endereço.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}