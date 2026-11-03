package org.example.interdisciplinar.controller.Categoria;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.dao.CategoriaDAO;
import org.example.interdisciplinar.model.Categoria;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import java.io.IOException;

// Define o endereço responsável pelo cadastro de categorias
@WebServlet(name = "CriarCategorias", value = "/categorias/insert")
public class ServletCriarCategoria extends HttpServlet {

    private CategoriaDAO categoriaDAO;

    // Método que inicializa a DAO
    @Override
    public void init(){
        categoriaDAO = new CategoriaDAO();
    }

    // Método que recebe os dados do formulário e cadastra uma categoria
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");

        // Recupera os dados enviados pelo formulário
        String nomeCategoria = request.getParameter("nomeCategoria");
        String descricaoCategoria = request.getParameter("descricaoCategoria");

        // Verifica se todos os campos foram preenchidos
        if (nomeCategoria == null || nomeCategoria.isBlank()
                || descricaoCategoria == null || descricaoCategoria.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Preencha todos os campos."
            );
            return;
        }

        try {
            // Cria o objeto categoria com os dados recebidos
            Categoria categoria = new Categoria();

            categoria.setNome(nomeCategoria);
            categoria.setDescricao(descricaoCategoria);

            // Envia a categoria para ser cadastrada no banco
            boolean inserido = categoriaDAO.inserir(categoria);

            // Verifica se o cadastro foi realizado
            if (inserido) {

                // Redireciona para a listagem de categorias
                response.sendRedirect(
                        request.getContextPath() + "/admin/categorias"
                );

            } else {

                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Não foi possível inserir a categoria."
                );
            }

        } catch (FalhaConexaoException e) {
            // Registra os detalhes da falha de conexão no log
            getServletContext().log("Falha ao conectar ao banco de dados", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao cadastrar categoria", e);

            request.setAttribute("erro", "Não foi possível cadastrar a categoria.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
        }
    }
}