package org.example.interdisciplinar.controller.Autenticacao;

// Importações necessárias para o funcionamento do servlet
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;
import org.example.interdisciplinar.dao.AdministradorDAO;
import org.example.interdisciplinar.model.Administrador;
import org.example.interdisciplinar.utils.Criptografia;
import org.example.interdisciplinar.utils.Regex;

import java.io.IOException;

@WebServlet("/login")
public class ServletLogin extends HttpServlet {

    private AdministradorDAO administradorDAO;

    // Método que inicializa o DAO
    @Override
    public void init() {
        administradorDAO = new AdministradorDAO();
    }

    // Método que realiza a autenticação do administrador
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // Verifica se os campos foram preenchidos
        if (email == null || email.isBlank() ||
                senha == null || senha.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Preencha todos os campos"
            );
            return;
        }

        // Valida o formato dos dados informados
        if (!Regex.validarEmail(email) ||
                !Regex.validarSenha(senha)) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Formato de email ou senha inválido"
            );
            return;
        }

        // Busca o administrador pelo email
        Administrador administrador =
                administradorDAO.buscarPorEmail(email.trim());

        // Verifica se o administrador existe
        if (administrador == null) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Email ou senha incorretos"
            );
            return;
        }

        // Verifica se a senha informada corresponde ao hash armazenado
        if (!Criptografia.verificar(senha, administrador.getSenha())) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Email ou senha incorretos"
            );
            return;
        }

        // Encerra uma sessão anterior, caso exista
        HttpSession sessaoAntiga = request.getSession(false);

        if (sessaoAntiga != null) {
            sessaoAntiga.invalidate();
        }

        // Cria uma nova sessão para o administrador autenticado
        HttpSession sessao = request.getSession(true);

        // Armazena os dados necessários para identificar o administrador
        sessao.setAttribute("administradorId", administrador.getId());
        sessao.setAttribute("administradorNome", administrador.getNome());
        sessao.setAttribute("administradorEmail", administrador.getEmail());
        sessao.setAttribute("administradorFoto", administrador.getUrlFoto());
        sessao.setAttribute("administrador", administrador);

        // Redireciona para a tela de análises
        response.sendRedirect(request.getContextPath() + "/admin/analises");
    }
}
