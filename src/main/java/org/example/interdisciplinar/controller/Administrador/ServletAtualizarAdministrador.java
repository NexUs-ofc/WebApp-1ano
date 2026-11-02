package org.example.interdisciplinar.controller.Administrador;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import org.example.interdisciplinar.dao.AdministradorDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Administrador;
import org.example.interdisciplinar.utils.Regex;
import org.example.interdisciplinar.utils.Criptografia;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

// Define o endereço responsável pela atualização dos administradores
@WebServlet(name = "AtualizarAdministrador", value = "/admin/administradores/editar")
public class ServletAtualizarAdministrador extends HttpServlet {

    // Instância responsável pelas operações no banco de dados
    private AdministradorDAO administradorDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        administradorDAO = new AdministradorDAO();
    }

    // Método que busca os dados do administrador para preencher o formulário de edição
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String idTexto = request.getParameter("id");

            // Verifica se o ID foi informado
            if (idTexto == null || idTexto.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            int id = Integer.parseInt(idTexto);

            // Verifica se o ID é válido
            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Busca o administrador pelo ID
            Administrador administrador = administradorDAO.buscarporId(id);

            // Verifica se o administrador foi encontrado
            if (administrador == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Envia o administrador encontrado para a página
            request.setAttribute("administrador", administrador);

            // Encaminha para a página de administradores
            request.getRequestDispatcher("/admin/administradores").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            // Registra o erro de conexão no log do servidor
            getServletContext().log("Erro de conexão com o banco", e);

            // Envia uma mensagem amigável para a página de erro
            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra o erro de persistência no log do servidor
            getServletContext().log("Erro de persistência", e);

            // Informa que não foi possível buscar o administrador
            request.setAttribute("erro", "Não foi possível buscar o administrador pelo ID.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }

    // Método que recebe os dados do formulário e atualiza o administrador
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            // Recebe os dados enviados pelo formulário
            String idTexto = request.getParameter("id");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String telefone = request.getParameter("telefone");
            String cpf = request.getParameter("cpf");
            String urlFoto = request.getParameter("urlFoto");

            // Verifica se os campos obrigatórios foram preenchidos
            if (idTexto == null || idTexto.isBlank() ||
                    nome == null || nome.isBlank() ||
                    email == null || email.isBlank() ||
                    telefone == null || telefone.isBlank() ||
                    cpf == null || cpf.isBlank()) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Preencha todos os campos obrigatórios");
                return;
            }

            int id = Integer.parseInt(idTexto.trim());

            // Verifica se o ID é válido
            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Busca o administrador que será atualizado
            Administrador administradorExistente = administradorDAO.buscarporId(id);

            // Verifica se o administrador existe no banco
            if (administradorExistente == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Remove espaços desnecessários dos campos
            nome = nome.trim();
            email = email.trim();
            telefone = telefone.trim();
            cpf = cpf.trim();
            urlFoto = urlFoto == null ? "" : urlFoto.trim();

            // Valida os campos utilizando os métodos da classe Regex
            if (!Regex.validarTelefone(telefone) ||
                    !Regex.validarEmail(email) ||
                    !Regex.validarCpf(cpf)) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Um ou mais campos possuem valores inválidos");
                return;
            }

            // Valida a URL da foto, caso tenha sido informada
            if (!urlFoto.isBlank()) {
                try {
                    URI uri = URI.create(urlFoto);

                    // Verifica se a URL possui um endereço e protocolo válidos
                    if (!uri.isAbsolute() ||
                            uri.getHost() == null ||
                            (!"https".equalsIgnoreCase(uri.getScheme()) &&
                                    !"http".equalsIgnoreCase(uri.getScheme()))) {

                        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                                "URL da foto inválida");
                        return;
                    }

                } catch (IllegalArgumentException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "URL da foto inválida");
                    return;
                }

            } else {
                // Mantém a foto antiga caso nenhuma nova seja informada
                urlFoto = administradorExistente.getUrlFoto();
            }

            // Mantém a senha atual caso uma nova não seja informada
            String senhaCriptografada = administradorExistente.getSenha();

            // Verifica se uma nova senha foi informada
            if (senha != null && !senha.isBlank()) {

                // Valida os requisitos da nova senha
                if (!Regex.validarSenha(senha)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "A senha não atende aos requisitos");
                    return;
                }

                // Criptografa a nova senha antes de armazená-la
                senhaCriptografada = Criptografia.criptografar(senha);
            }

            // Cria um objeto com os novos dados do administrador
            Administrador administrador = new Administrador();

            // Preenche os atributos do administrador
            administrador.setId(id);
            administrador.setNome(nome);
            administrador.setEmail(email);
            administrador.setSenha(senhaCriptografada);
            administrador.setTelefone(telefone);
            administrador.setCpf(cpf);
            administrador.setUrlFoto(urlFoto);

            // Envia os novos dados para a DAO atualizar no banco
            administradorDAO.atualizar(administrador);

            // Redireciona para a página de administradores após a atualização
            response.sendRedirect(request.getContextPath() + "/admin/administradores");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            // Registra o erro de conexão no log do servidor
            getServletContext().log("Erro de conexão com o banco", e);

            // Envia uma mensagem amigável para a página de erro
            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra o erro de persistência no log do servidor
            getServletContext().log("Erro de persistência", e);

            // Informa que não foi possível atualizar o administrador
            request.setAttribute("erro", "Não foi possível atualizar o administrador.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}