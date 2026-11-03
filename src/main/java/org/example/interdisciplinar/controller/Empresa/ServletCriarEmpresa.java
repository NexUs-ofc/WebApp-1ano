package org.example.interdisciplinar.controller.Empresa;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import org.example.interdisciplinar.dao.EmpresaDAO;
import org.example.interdisciplinar.model.Empresa;
import org.example.interdisciplinar.utils.Regex;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// Define o endereço responsável pelo cadastro das empresas
@WebServlet(name = "CriarEmpresa", value = "/admin/empresas/criar")
public class ServletCriarEmpresa extends HttpServlet {

    private EmpresaDAO empresaDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        empresaDAO = new EmpresaDAO();
    }

    // Método que recebe os dados do formulário e cadastra uma empresa
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Recupera os parâmetros enviados pelo formulário
        String nome = request.getParameter("nome");
        String cnpj = request.getParameter("cnpj");
        String idEndereco = request.getParameter("idEndereco");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");

        // Verifica se todos os campos foram preenchidos
        if (nome == null || nome.isBlank()
                || cnpj == null || cnpj.isBlank()
                || idEndereco == null || idEndereco.isBlank()
                || email == null || email.isBlank()
                || telefone == null || telefone.isBlank()) {

            request.setAttribute("erro", "Preencha todos os campos.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
            return;
        }

        // Remove espaços desnecessários dos campos
        nome = nome.trim();
        cnpj = cnpj.trim();
        idEndereco = idEndereco.trim();
        email = email.trim();
        telefone = telefone.trim();

        // Valida os campos utilizando os métodos da classe Regex
        if (!Regex.validarCnpj(cnpj)
                || !Regex.validarEmail(email)
                || !Regex.validarTelefone(telefone)) {

            request.setAttribute("erro", "Verifique os dados informados.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
            return;
        }

        try {
            // Converte o ID do endereço para inteiro
            int idEnderecoConvertido = Integer.parseInt(idEndereco);

            // Verifica se o ID do endereço é válido
            if (idEnderecoConvertido <= 0) {
                request.setAttribute("erro", "Endereço inválido.");
                request.getRequestDispatcher("WEB-INF/errorpage.jsp").forward(request, response);
                return;
            }

            // Cria o objeto empresa com os dados recebidos
            Empresa empresa = new Empresa();

            empresa.setNome(nome);
            empresa.setCnpj(cnpj);
            empresa.setIdEndereco(idEnderecoConvertido);
            empresa.setEmail(email);
            empresa.setTelefone(telefone);

            // Envia a empresa para ser cadastrada no banco
            boolean inserido = empresaDAO.inserir(empresa);

            // Verifica se o cadastro foi realizado
            if (inserido) {

                // Redireciona para a listagem após o cadastro
                response.sendRedirect(request.getContextPath() + "/admin/empresas");

            } else {
                request.setAttribute("erro", "Não foi possível cadastrar a empresa.");
                request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            // Registra o erro de conversão no log
            getServletContext().log("Erro ao converter o ID do endereço", e);

            request.setAttribute("erro", "ID do endereço inválido.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (FalhaConexaoException e) {
            // Registra os detalhes da falha de conexão no log
            getServletContext().log("Falha na conexão com o banco de dados", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao cadastrar empresa", e);

            request.setAttribute("erro", "Não foi possível cadastrar a empresa.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
        }
    }
}