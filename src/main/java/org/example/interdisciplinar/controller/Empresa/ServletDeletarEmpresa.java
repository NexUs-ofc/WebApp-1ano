package org.example.interdisciplinar.controller.Empresa;

// Todos os imports necessários
import org.example.interdisciplinar.dao.EmpresaDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeletarEmpresa", value = "/empresas/deletar")
public class ServletDeletarEmpresa extends HttpServlet {

    private EmpresaDAO empresaDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        empresaDAO = new EmpresaDAO();
    }

    // Método que recebe a solicitação de exclusão
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupera o ID enviado pelo formulário
        String idParametro = request.getParameter("id");

        // Verifica se o ID foi informado
        if (idParametro == null || idParametro.isBlank()) {
            request.setAttribute("erro", "ID da empresa não informado.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }

        try {
            // Converte o ID para inteiro
            int id = Integer.parseInt(idParametro);

            // Verifica se o ID é válido
            if (id <= 0) {
                request.setAttribute("erro", "ID da empresa inválido.");
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                return;
            }

            // Realiza a exclusão da empresa
            empresaDAO.deletar(id);

            // Redireciona após a exclusão
            response.sendRedirect(request.getContextPath() + "/empresas");

        } catch (NumberFormatException e) {
            // Trata IDs que não possuem formato numérico
            request.setAttribute("erro", "ID da empresa inválido.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (FalhaConexaoException e) {
            // Registra problemas de conexão com o banco
            getServletContext().log("Falha na conexão com o banco de dados", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra falhas durante a exclusão
            getServletContext().log("Erro ao deletar empresa", e);

            request.setAttribute("erro", "Não foi possível deletar a empresa.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}
