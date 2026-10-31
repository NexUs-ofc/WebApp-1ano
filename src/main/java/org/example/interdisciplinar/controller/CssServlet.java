package org.example.interdisciplinar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "Css", value = "/css/*")
public class CssServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String arquivo = request.getPathInfo();

        if (arquivo == null || !arquivo.matches("/[a-zA-Z0-9_-]+\\.css")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try (InputStream css = getServletContext().getResourceAsStream("/WEB-INF/css" + arquivo)) {
            if (css == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            response.setContentType("text/css");
            response.setCharacterEncoding("UTF-8");
            css.transferTo(response.getOutputStream());
        }
    }
}
