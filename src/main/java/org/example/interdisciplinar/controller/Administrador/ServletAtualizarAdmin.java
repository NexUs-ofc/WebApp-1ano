package org.example.interdisciplinar.controller.Administrador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.AdministradorDAO;
import org.example.interdisciplinar.model.Administrador;

import java.io.IOException;

// Recebe os dados enviados pelo pop-up de edição
@WebServlet(name = "AtualizarAdministrador", value = "/administradores/atualizar")
public class ServletAtualizarAdmin extends HttpServlet {
    private AdministradorDAO administradorDAO;

    @Override
    public void init() {
        administradorDAO = new AdministradorDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            Administrador administrador = new Administrador();
            administrador.setId(Integer.parseInt(request.getParameter("id")));
            administrador.setNome(request.getParameter("nome"));
            administrador.setEmail(request.getParameter("email"));
            administrador.setSenha(request.getParameter("senha"));
            administrador.setTelefone(juntarTelefone(
                    request.getParameter("ddd"),
                    request.getParameter("telefone")
            ));
            administrador.setFotoPerfil(valorOpcional(request.getParameter("fotoPerfil")));

            if (administrador.getNome() == null || administrador.getNome().isBlank()
                    || administrador.getEmail() == null || administrador.getEmail().isBlank()
                    || administrador.getSenha() == null || administrador.getSenha().isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Preencha os campos obrigatórios.");
                return;
            }

            if (!administradorDAO.atualizar(administrador)) {
                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Não foi possível atualizar o administrador."
                );
                return;
            }

            response.sendRedirect(request.getContextPath() + "/administradores");
        } catch (NumberFormatException excecao) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Administrador inválido.");
        }
    }

    // Junta o DDD e o telefone, mantendo somente números
    private String juntarTelefone(String ddd, String telefone) {
        String numeroCompleto = (ddd == null ? "" : ddd) + (telefone == null ? "" : telefone);
        numeroCompleto = numeroCompleto.replaceAll("\\D", "");
        return numeroCompleto.isBlank() ? null : numeroCompleto;
    }

    // Converte campos opcionais vazios em null
    private String valorOpcional(String valor) {
        return valor == null || valor.isBlank() ? null : valor;
    }

    @Override
    public void destroy() {
        GerenteConexao.desconectar();
    }
}
