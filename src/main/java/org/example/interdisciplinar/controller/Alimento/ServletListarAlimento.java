package org.example.interdisciplinar.controller.Alimento;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.AlimentoDAO;
import org.example.interdisciplinar.model.Alimento;

import java.io.IOException;
import java.util.List;

// Define a URL que abre a listagem de alimentos
@WebServlet(name = "ListarAlimentos", value = "/alimentos")
public class ServletListarAlimento extends HttpServlet {
    private AlimentoDAO alimentoDAO;

    @Override
    public void init() {
        alimentoDAO = new AlimentoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Alimento> alimentos = alimentoDAO.listar();
        request.setAttribute("alimentos", alimentos);
        request.getRequestDispatcher("/WEB-INF/Views/alimentos.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        GerenteConexao.desconectar();
    }
}
