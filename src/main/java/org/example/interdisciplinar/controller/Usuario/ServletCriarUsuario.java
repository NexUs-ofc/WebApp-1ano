package org.example.interdisciplinar.controller.Usuario;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.dao.UsuarioDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.ConfiguracaoAlerta;
import org.example.interdisciplinar.model.Usuario;
import org.example.interdisciplinar.utils.Regex;
import org.example.interdisciplinar.utils.Criptografia;

import java.io.IOException;

// Define o endereço responsável pelo cadastro dos usuários
@WebServlet(name = "CriarUsuarios", value = "/admin/usuarios/insert")
public class ServletCriarUsuario extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    // Método que recebe os dados do formulário e cadastra um usuário
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Recupera os dados enviados pelo formulário
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        String preferencias = request.getParameter("preferenciasConsumo");
        String configAlerta = request.getParameter("configuracaoAlerta");
        String senha = request.getParameter("senha");

        // Verifica se todos os campos foram preenchidos
        if (nome == null || nome.isBlank()
                || email == null || email.isBlank()
                || telefone == null || telefone.isBlank()
                || preferencias == null || preferencias.isBlank()
                || configAlerta == null || configAlerta.isBlank()
                || senha == null || senha.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Todos os campos precisam ser preenchidos!"
            );
            return;
        }

        // Remove espaços desnecessários dos campos de texto
        nome = nome.trim();
        email = email.trim();
        telefone = telefone.replaceAll("\\D", "");
        preferencias = preferencias.trim();
        configAlerta = configAlerta.trim();

        // Valida o email, telefone e senha utilizando a classe Regex
        if (!Regex.validarEmail(email)
                || !Regex.validarTelefone(telefone)
                || !Regex.validarSenha(senha)) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Um ou mais campos possuem valores inválidos."
            );
            return;
        }

        // Converte o texto recebido para a configuração de alerta
        ConfiguracaoAlerta configuracao;

        try {
            configuracao = ConfiguracaoAlerta.valueOf(configAlerta.toUpperCase());
        } catch (IllegalArgumentException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Configuração de alerta inválida."
            );
            return;
        }

        try {
            // Criptografa a senha antes de armazená-la
            String senhaCriptografada = Criptografia.criptografar(senha);

            // Cria o objeto usuário com os dados recebidos
            Usuario usuario = new Usuario();

            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setTelefone(telefone);
            usuario.setPreferenciasConsumo(preferencias);
            usuario.setConfiguracaoAlerta(configuracao);
            usuario.setSenha(senhaCriptografada);

            // Envia o usuário para ser cadastrado no banco
            boolean inserido = usuarioDAO.inserir(usuario);

            // Verifica se o cadastro foi realizado
            if (inserido) {

                // Redireciona para a listagem após o cadastro
                response.sendRedirect(request.getContextPath() + "/admin/usuarios");

            } else {
                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Não foi possível inserir o usuário."
                );
            }

        } catch (FalhaConexaoException e) {
            // Registra os detalhes da falha de conexão no log
            getServletContext().log("Falha ao conectar ao banco de dados", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao cadastrar usuário", e);

            request.setAttribute("erro", "Não foi possível cadastrar o usuário.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
        }
    }
}
