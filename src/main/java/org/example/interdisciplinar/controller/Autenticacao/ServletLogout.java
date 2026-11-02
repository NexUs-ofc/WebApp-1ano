
package org.example.interdisciplinar.controller.Autenticacao;

// Importações necessárias para o funcionamento do servlet
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class ServletLogout extends HttpServlet {

    // Método que encerra a sessão do administrador
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Recupera a sessão atual sem criar uma nova
        HttpSession sessao = request.getSession(false);

        // Encerra a sessão caso ela exista
        if (sessao != null) {
            sessao.invalidate();
        }

        // Redireciona para a página inicial
        response.sendRedirect(request.getContextPath() + "/index.html");
    }

    // Método que impede o logout por requisições GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
