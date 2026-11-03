package org.example.interdisciplinar.controller.Autenticacao;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/admin/*")
public class ServletFiltroAutenticacao implements Filter {

    // Método que verifica se o administrador está autenticado
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest requisicao = (HttpServletRequest) request;
        HttpServletResponse resposta = (HttpServletResponse) response;

        // Recupera a sessão sem criar uma nova
        HttpSession sessao = requisicao.getSession(false);

        // Verifica se existe um administrador autenticado
        boolean autenticado = sessao != null &&
                sessao.getAttribute("administrador") != null;

        if (autenticado) {

            // Permite que a requisição continue
            chain.doFilter(request, response);

        } else {

            // Redireciona para o login
            resposta.sendRedirect(
                    requisicao.getContextPath() + "/restricao.html"
            );
        }
    }
}
