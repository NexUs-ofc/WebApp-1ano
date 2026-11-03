package org.example.interdisciplinar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "Js", value = "/JS/*")
public class JsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String arquivo = request.getPathInfo();

        if (arquivo == null || !arquivo.matches("/[a-zA-Z0-9_-]+\\.js")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try (InputStream javascript = getServletContext().getResourceAsStream("/WEB-INF/JS" + arquivo)) {
            if (javascript == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            response.setContentType("application/javascript");
            response.setCharacterEncoding("UTF-8");
            javascript.transferTo(response.getOutputStream());
        }
    }
}
