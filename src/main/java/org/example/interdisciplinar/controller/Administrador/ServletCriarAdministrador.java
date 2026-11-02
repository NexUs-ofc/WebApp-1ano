
package org.example.interdisciplinar.controller.Administrador;

// Todos os imports necessários
import org.example.interdisciplinar.dao.AdministradorDAO;
import org.example.interdisciplinar.model.Administrador;
import org.example.interdisciplinar.utils.Regex;
import org.example.interdisciplinar.utils.Criptografia;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CriarAdministrador", value = "/admin/administradores/criar")
public class ServletCriarAdministrador extends HttpServlet {

    private AdministradorDAO administradorDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        administradorDAO = new AdministradorDAO();
    }

    // Método que recebe a solicitação de cadastro
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupera os parâmetros enviados pelo formulário
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String telefone = request.getParameter("telefone");
        String cpf = request.getParameter("cpf");
        String urlFoto = request.getParameter("urlFoto");

        // Verifica se os campos obrigatórios foram preenchidos
        if (nome == null || nome.isBlank()
                || email == null || email.isBlank()
                || senha == null || senha.isBlank()
                || telefone == null || telefone.isBlank()
                || cpf == null || cpf.isBlank()) {

            request.setAttribute("erro", "Preencha todos os campos obrigatórios.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }

        // Valida os campos utilizando Regex
        if (!Regex.validarEmail(email)
                || !Regex.validarSenha(senha)
                || !Regex.validarTelefone(telefone)
                || !Regex.validarCpf(cpf)) {

            request.setAttribute("erro", "Verifique os dados informados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }

        try {
            // Criptografa a senha antes de armazená-la
            String senhaCriptografada = Criptografia.criptografar(senha);

            // Cria o objeto administrador com os dados informados
            Administrador administrador = new Administrador();
            administrador.setNome(nome);
            administrador.setEmail(email);
            administrador.setSenha(senhaCriptografada);
            administrador.setTelefone(telefone);
            administrador.setCpf(cpf);
            administrador.setUrlFoto(urlFoto);

            // Realiza o cadastro do administrador
            administradorDAO.inserir(administrador);

            // Redireciona após o cadastro
            response.sendRedirect(request.getContextPath() + "/admin/administradores");

        } catch (FalhaConexaoException e) {
            // Registra problemas de conexão com o banco
            getServletContext().log("Falha na conexão com o banco de dados", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra falhas durante o cadastro
            getServletContext().log("Erro ao criar administrador", e);

            request.setAttribute("erro", "Não foi possível cadastrar o administrador.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}
