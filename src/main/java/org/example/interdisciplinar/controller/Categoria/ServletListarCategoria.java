package org.example.interdisciplinar.controller.Categoria;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.CategoriaDAO;
import org.example.interdisciplinar.model.Categoria;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListarCategorias", value = "/categorias")
public class ServletListarCategoria extends HttpServlet {
    private CategoriaDAO categoriaDAO;

    @Override
    public void init(){
        categoriaDAO = new CategoriaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Integer idInicio = null;
        Integer idFim = null;

        try {
            String idInicioTexto = request.getParameter("idInicio");
            String idFimTexto = request.getParameter("idFim");
            String nome = request.getParameter("nome");
            String ordenar = request.getParameter("ordenar");

            if (idInicioTexto != null && !idInicioTexto.isBlank()) {
                idInicio = Integer.parseInt(idInicioTexto);
            }

            if (idFimTexto != null && !idFimTexto.isBlank()) {
                idFim = Integer.parseInt(idFimTexto);
            }

            if (idInicio != null && idFim != null && idInicio > idFim) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "O ID inicial não pode ser maior que o ID final."
                );
                return;
            }

            List<Categoria> categorias = categoriaDAO.listarFiltrado(idInicio, idFim, nome, ordenar);
            request.setAttribute("categorias", categorias);
            request.getRequestDispatcher("/WEB-INF/Views/categorias.jsp").forward(request, response);

        } catch (NumberFormatException nfe){
            request.setAttribute("erro", "O ID das categorias precisa ser um número inteiro");
            request.getRequestDispatcher("/WEB-INF/Views/ErrorPage.jsp").forward(request, response);

        } catch (RuntimeException re){
            request.setAttribute("erro", "Não foi possível carregar as categorias. Tente novamente.");
            request.getRequestDispatcher("/WEB-INF/Views/ErrorPage.jsp").forward(request, response);

        }
    }


    public void destroy(){
        GerenteConexao.desconectar();
    }
}
