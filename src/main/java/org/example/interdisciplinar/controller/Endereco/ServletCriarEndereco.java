package org.example.interdisciplinar.controller.Endereco;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.dao.EnderecoDAO;
import org.example.interdisciplinar.model.Endereco;
import org.example.interdisciplinar.utils.Regex;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import java.io.IOException;

// Define o endereço responsável pelo cadastro dos endereços
@WebServlet(name = "CriarEnderecos", value = "/admin/enderecos/insert")
public class ServletCriarEndereco extends HttpServlet {

    private EnderecoDAO enderecoDAO;

    // Método que inicializa a DAO
    @Override
    public void init(){
        enderecoDAO = new EnderecoDAO();
    }

    // Método que recebe os dados do formulário e cadastra um endereço
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");

        // Recupera os dados enviados pelo formulário
        String cep = request.getParameter("cep");
        String rua = request.getParameter("rua");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String numero = request.getParameter("numero");

        // Verifica se todos os campos foram preenchidos
        if (cep == null || cep.isBlank()
                || rua == null || rua.isBlank()
                || bairro == null || bairro.isBlank()
                || cidade == null || cidade.isBlank()
                || numero == null || numero.isBlank()
                || estado == null || estado.isBlank()) {

            request.setAttribute("erro", "Preencha todos os campos");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
            return;
        }

        // Remove espaços desnecessários dos campos
        cep = cep.trim();
        rua = rua.trim();
        bairro = bairro.trim();
        cidade = cidade.trim();
        numero = numero.trim();
        estado = estado.trim().toUpperCase();

        // Valida o CEP utilizando o método da classe Regex
        if (!Regex.validarCep(cep)){
            request.setAttribute("erro", "O formato do CEP está inválido");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
            return;
        }

        try {
            // Cria o objeto endereço com os dados recebidos
            Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, cep);

            // Envia o endereço para ser cadastrado no banco
            boolean inserido = enderecoDAO.inserir(endereco);

            // Verifica se o cadastro foi realizado
            if (inserido) {

                // Redireciona para a listagem após o cadastro
                response.sendRedirect(
                        request.getContextPath() + "/admin/enderecos"
                );

            } else {
                request.setAttribute("erro", "Não foi possível cadastrar o endereço");
                request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
            }

        } catch (FalhaConexaoException e) {
            // Registra os detalhes da falha de conexão no log
            getServletContext().log("Falha na conexão com o banco de dados", e);
            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao cadastrar endereço", e);
            request.setAttribute("erro", "Não foi possível cadastrar o endereço.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
        }
    }
}