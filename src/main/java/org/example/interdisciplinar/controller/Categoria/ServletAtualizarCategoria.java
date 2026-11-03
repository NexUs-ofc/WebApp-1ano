package org.example.interdisciplinar.controller.Categoria;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import org.example.interdisciplinar.dao.CategoriaDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Categoria;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// Define o endereço responsável pela atualização das categorias
@WebServlet(name = "AtualizarCategoria", value = "/admin/categorias/editar")
public class ServletAtualizarCategoria extends HttpServlet {

    private CategoriaDAO categoriaDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        categoriaDAO = new CategoriaDAO();
    }

    // Método que busca uma categoria para preencher o formulário de edição
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // Recupera o ID enviado pela página
            String idParametro = request.getParameter("id");

            // Verifica se o ID foi informado
            if (idParametro == null || idParametro.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Converte o ID para inteiro
            int id = Integer.parseInt(idParametro);

            // Verifica se o ID é válido
            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Busca a categoria pelo ID
            Categoria categoria = categoriaDAO.buscarporId(id);

            // Verifica se a categoria foi encontrada
            if (categoria == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Envia a categoria encontrada para a página
            request.setAttribute("categoria", categoria);

            // Encaminha para a página de categorias
            request.getRequestDispatcher("/admin/categorias").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            // Registra o erro de conexão no log do servidor
            getServletContext().log("Erro de conexão com o banco", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra o erro de persistência no log do servidor
            getServletContext().log("Erro de persistência", e);

            request.setAttribute("erro", "Não foi possível buscar a categoria.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
        }
    }

    // Método que recebe os dados do formulário e atualiza uma categoria
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            // Recupera os dados enviados pelo formulário
            String idParametro = request.getParameter("id");
            String nome = request.getParameter("nome");
            String descricao = request.getParameter("descricao");

            // Verifica se todos os campos foram preenchidos
            if (idParametro == null || idParametro.isBlank()
                    || nome == null || nome.isBlank()
                    || descricao == null || descricao.isBlank()) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Converte o ID para inteiro
            int id = Integer.parseInt(idParametro);

            // Verifica se o ID é válido
            if (id <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Busca a categoria que será atualizada
            Categoria categoriaExistente = categoriaDAO.buscarporId(id);

            // Verifica se a categoria existe no banco
            if (categoriaExistente == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Atualiza os dados da categoria
            categoriaExistente.setNome(nome.trim());
            categoriaExistente.setDescricao(descricao.trim());

            // Envia a categoria para a DAO realizar a atualização
            categoriaDAO.atualizar(categoriaExistente);

            // Redireciona para a listagem após a atualização
            response.sendRedirect(request.getContextPath() + "/admin/categorias");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (FalhaConexaoException e) {
            // Registra o erro de conexão no log do servidor
            getServletContext().log("Erro de conexão com o banco", e);
            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra o erro de persistência no log do servidor
            getServletContext().log("Erro de persistência", e);
            request.setAttribute("erro", "Não foi possível atualizar a categoria.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
        }
    }
}
