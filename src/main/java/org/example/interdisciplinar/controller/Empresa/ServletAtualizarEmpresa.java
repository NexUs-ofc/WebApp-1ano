package org.example.interdisciplinar.controller.Empresa;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import org.example.interdisciplinar.dao.EmpresaDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Empresa;
import org.example.interdisciplinar.utils.Regex;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// Define o endereço responsável pela atualização das empresas
@WebServlet(name = "AtualizarEmpresa", value = "/admin/empresas/editar")
public class ServletAtualizarEmpresa extends HttpServlet {

    private EmpresaDAO empresaDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        empresaDAO = new EmpresaDAO();
    }

    // Método que busca uma empresa para preencher o formulário de edição
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

            // Busca a empresa pelo ID
            Empresa empresa = empresaDAO.buscarporId(id);

            // Verifica se a empresa foi encontrada
            if (empresa == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Envia a empresa encontrada para a página
            request.setAttribute("empresa", empresa);

            // Encaminha para a página de empresas
            request.getRequestDispatcher("/admin/empresas").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            // Registra os detalhes do erro de conexão no log
            getServletContext().log("Erro de conexão com o banco", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro de persistência", e);

            request.setAttribute("erro", "Não foi possível buscar a empresa.");
            request.getRequestDispatcher("WEB-INF/errorpage.jsp").forward(request, response);
        }
    }

    // Método que recebe os dados do formulário e atualiza uma empresa
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            // Recupera os dados enviados pelo formulário
            String idParametro = request.getParameter("id");
            String nome = request.getParameter("nome");
            String cnpj = request.getParameter("cnpj");
            String idEnderecoParametro = request.getParameter("idEndereco");
            String email = request.getParameter("email");
            String telefone = request.getParameter("telefone");

            // Verifica se todos os campos obrigatórios foram preenchidos
            if (idParametro == null || idParametro.isBlank()
                    || nome == null || nome.isBlank()
                    || cnpj == null || cnpj.isBlank()
                    || idEnderecoParametro == null || idEnderecoParametro.isBlank()
                    || email == null || email.isBlank()
                    || telefone == null || telefone.isBlank()) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Preencha todos os campos obrigatórios"
                );
                return;
            }

            // Converte os IDs para inteiro
            int id = Integer.parseInt(idParametro.trim());
            int idEndereco = Integer.parseInt(idEnderecoParametro.trim());

            // Verifica se os IDs são válidos
            if (id <= 0 || idEndereco <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Busca a empresa que será atualizada
            Empresa empresaExistente = empresaDAO.buscarporId(id);

            // Verifica se a empresa existe no banco
            if (empresaExistente == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Remove espaços desnecessários dos campos
            nome = nome.trim();
            cnpj = cnpj.trim();
            email = email.trim();
            telefone = telefone.trim();

            // Valida os campos utilizando os métodos da classe Regex
            if (!Regex.validarCnpj(cnpj)
                    || !Regex.validarEmail(email)
                    || !Regex.validarTelefone(telefone)) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Um ou mais campos possuem valores inválidos"
                );
                return;
            }

            // Atualiza os dados da empresa encontrada
            empresaExistente.setNome(nome);
            empresaExistente.setCnpj(cnpj);
            empresaExistente.setIdEndereco(idEndereco);
            empresaExistente.setEmail(email);
            empresaExistente.setTelefone(telefone);

            // Envia os novos dados para a DAO atualizar no banco
            boolean atualizado = empresaDAO.atualizar(empresaExistente);

            // Verifica se a atualização foi realizada
            if (!atualizado) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Não foi possível encontrar a empresa para atualização"
                );
                return;
            }

            // Redireciona para a listagem após a atualização
            response.sendRedirect(request.getContextPath() + "/admin/empresas");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            // Registra os detalhes do erro de conexão no log
            getServletContext().log("Erro de conexão com o banco", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro de persistência", e);
            request.setAttribute("erro", "Não foi possível atualizar a empresa.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
        }
    }
}