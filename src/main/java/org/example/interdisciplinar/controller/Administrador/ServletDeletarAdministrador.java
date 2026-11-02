
package org.example.interdisciplinar.controller.Administrador;

// Todos os imports necessários
import org.example.interdisciplinar.dao.AdministradorDAO;
import org.example.interdisciplinar.model.Administrador;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "DeletarAdministrador", value = "/admin/administradores/deletar")
public class ServletDeletarAdministrador extends HttpServlet {

    private AdministradorDAO administradorDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        administradorDAO = new AdministradorDAO();
    }

    // Método que recebe a solicitação de exclusão
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupera a sessão atual
        HttpSession sessao = request.getSession(false);

        // Verifica se existe um administrador autenticado
        if (sessao == null || sessao.getAttribute("administrador") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Recupera o administrador autenticado
        Administrador administradorLogado =
                (Administrador) sessao.getAttribute("administrador");

        // Recupera o ID enviado pelo formulário
        String idParametro = request.getParameter("id");

        // Verifica se o ID foi informado
        if (idParametro == null || idParametro.isBlank()) {
            request.setAttribute("erro", "Administrador inválido.");
            request.getRequestDispatcher("/administradores.jsp").forward(request, response);
            return;
        }

        try {
            // Converte o ID para inteiro
            int id = Integer.parseInt(idParametro);

            // Verifica se o ID é válido
            if (id <= 0) {
                request.setAttribute("erro", "Administrador inválido.");
                request.getRequestDispatcher("/administradores.jsp").forward(request, response);
                return;
            }

            // Impede que o administrador exclua a própria conta
            if (id == administradorLogado.getId()) {
                request.setAttribute("erro", "Você não pode excluir sua própria conta.");
                request.getRequestDispatcher("/administradores.jsp").forward(request, response);
                return;
            }

            // Realiza a exclusão do administrador
            administradorDAO.deletar(id);

            // Redireciona após a exclusão
            response.sendRedirect(request.getContextPath() + "/admin/administradores");

        } catch (NumberFormatException e) {
            // Trata IDs que não possuem formato numérico
            request.setAttribute("erro", "ID do administrador inválido.");
            request.getRequestDispatcher("/administradores.jsp").forward(request, response);

        } catch (FalhaConexaoException e) {
            // Registra problemas de conexão com o banco
            getServletContext().log("Falha na conexão com o banco de dados", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/administradores.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra falhas durante a exclusão
            getServletContext().log("Erro ao deletar administrador", e);

            request.setAttribute("erro", "Não foi possível deletar o administrador.");
            request.getRequestDispatcher("/administradores.jsp").forward(request, response);
        }
    }
}
