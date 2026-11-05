package org.example.interdisciplinar.controller.Usuario;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import org.example.interdisciplinar.dao.UsuarioDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Usuario;
import org.example.interdisciplinar.model.ConfiguracaoAlerta;
import org.example.interdisciplinar.utils.Regex;
import org.example.interdisciplinar.utils.Criptografia;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// Define o endereço responsável pela atualização dos usuários
@WebServlet(name = "AtualizarUsuario", value = "/admin/usuarios/editar")
public class ServletAtualizarUsuario extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    // Método que busca um usuário para preencher o formulário de edição
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // Recupera o ID enviado pela página
            String idTexto = request.getParameter("id");

            // Verifica se o ID foi informado
            if (idTexto == null || idTexto.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Converte o ID para inteiro
            int id = Integer.parseInt(idTexto.trim());

            // Verifica se o ID é válido
            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Busca o usuário pelo ID
            Usuario usuario = usuarioDAO.buscarporId(id);

            // Verifica se o usuário foi encontrado
            if (usuario == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Envia o usuário encontrado para a página
            request.setAttribute("usuario", usuario);

            // Encaminha para a página de usuários
            request.getRequestDispatcher("/admin/usuarios").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            // Registra os detalhes do erro de conexão no log
            getServletContext().log("Erro de conexão com o banco", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao buscar usuário", e);

            request.setAttribute("erro", "Não foi possível buscar o usuário pelo ID.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
        }
    }

    // Método que recebe os dados do formulário e atualiza um usuário
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            // Recupera os dados enviados pelo formulário
            String idTexto = request.getParameter("id");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String preferenciasConsumo = request.getParameter("preferenciasConsumo");
            String configuracaoAlertaTexto = request.getParameter("configuracaoAlerta");
            String telefone = request.getParameter("telefone");
            String senha = request.getParameter("senha");

            // Verifica se todos os campos obrigatórios foram preenchidos
            if (idTexto == null || idTexto.isBlank()
                    || nome == null || nome.isBlank()
                    || email == null || email.isBlank()
                    || configuracaoAlertaTexto == null || configuracaoAlertaTexto.isBlank()
                    || telefone == null || telefone.isBlank()) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Preencha todos os campos obrigatórios");
                return;
            }

            // Converte o ID para inteiro
            int id = Integer.parseInt(idTexto.trim());

            // Verifica se o ID é válido
            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Busca o usuário que será atualizado
            Usuario usuario = usuarioDAO.buscarporId(id);

            // Verifica se o usuário existe no banco
            if (usuario == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Remove espaços desnecessários dos campos
            nome = nome.trim();
            email = email.trim();
            preferenciasConsumo = preferenciasConsumo == null ? "" : preferenciasConsumo.trim();
            configuracaoAlertaTexto = configuracaoAlertaTexto.trim();
            telefone = telefone.trim();

            // Valida o email e o telefone utilizando a classe Regex
            if (!Regex.validarEmail(email) || !Regex.validarTelefone(telefone)) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Um ou mais campos possuem valores inválidos"
                );
                return;
            }

            // Converte o texto recebido para a configuração de alerta
            ConfiguracaoAlerta configuracaoAlerta;

            try {
                configuracaoAlerta = ConfiguracaoAlerta.valueOf(configuracaoAlertaTexto);
            } catch (IllegalArgumentException e) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Configuração de alerta inválida"
                );
                return;
            }

            // Mantém a senha atual caso uma nova não seja informada
            String senhaCriptografada = usuario.getSenha();

            // Verifica se uma nova senha foi informada
            if (senha != null && !senha.isBlank()) {

                // Valida os requisitos da nova senha
                if (!Regex.validarSenha(senha)) {
                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "A senha informada não atende aos requisitos"
                    );
                    return;
                }

                // Criptografa a nova senha
                senhaCriptografada = Criptografia.criptografar(senha);
            }

            // Atualiza os dados do usuário encontrado
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setPreferenciasConsumo(preferenciasConsumo);
            usuario.setConfiguracaoAlerta(configuracaoAlerta);
            usuario.setTelefone(telefone);
            usuario.setSenha(senhaCriptografada);

            // Envia os novos dados para a DAO atualizar no banco
            boolean atualizado = usuarioDAO.atualizar(usuario);

            // Verifica se a atualização foi realizada
            if (!atualizado) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Não foi possível encontrar o usuário para atualização"
                );
                return;
            }

            // Redireciona para a listagem após a atualização
            response.sendRedirect(request.getContextPath() + "/admin/usuarios");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            // Registra os detalhes do erro de conexão no log
            getServletContext().log("Erro de conexão com o banco", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao atualizar usuário", e);

            request.setAttribute("erro", "Não foi possível atualizar o usuário.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
        }
    }
}
