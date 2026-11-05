
package org.example.interdisciplinar.controller.Alimento;

// Todos os imports necessários
import org.example.interdisciplinar.dao.AlimentoDAO;
import org.example.interdisciplinar.model.Alimento;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AtualizarAlimento", value = "/admin/alimentos/atualizar")
public class ServletAtualizarAlimento extends HttpServlet {

    private AlimentoDAO alimentoDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        alimentoDAO = new AlimentoDAO();
    }

    // Método que recebe a solicitação de atualização
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupera os parâmetros enviados pelo formulário
        String idParametro = request.getParameter("id");
        String codigoBarras = request.getParameter("codigoBarras");
        String nome = request.getParameter("nome");
        String marca = request.getParameter("marca");
        String idCategoriaParametro = request.getParameter("idCategoria");

        // Verifica se todos os campos foram preenchidos
        if (idParametro == null || idParametro.isBlank()
                || codigoBarras == null || codigoBarras.isBlank()
                || nome == null || nome.isBlank()
                || marca == null || marca.isBlank()
                || idCategoriaParametro == null || idCategoriaParametro.isBlank()) {

            request.setAttribute("erro", "Preencha todos os campos.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
            return;
        }

        try {
            // Converte o ID da categoria para inteiro
            int id = Integer.parseInt(idParametro);
            int idCategoria = Integer.parseInt(idCategoriaParametro);

            // Verifica se o ID da categoria é válido
            if (id <= 0 || idCategoria <= 0) {
                request.setAttribute("erro", "Categoria inválida.");
                request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
                return;
            }

            // Cria o objeto alimento com os novos dados
            Alimento alimento = new Alimento();
            alimento.setId(id);
            alimento.setCodigoBarras(codigoBarras);
            alimento.setNome(nome);
            alimento.setMarca(marca);
            alimento.setIdCategoria(idCategoria);

            // Realiza a atualização do alimento
            boolean atualizado = alimentoDAO.atualizar(alimento);

            if (!atualizado) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Redireciona após a atualização
            response.sendRedirect(request.getContextPath() + "/admin/alimentos");

        } catch (NumberFormatException e) {
            // Trata IDs de categoria que não possuem formato numérico
            request.setAttribute("erro", "ID da categoria inválido.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (FalhaConexaoException e) {
            // Registra problemas de conexão com o banco
            getServletContext().log("Falha na conexão com o banco de dados", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra falhas durante a atualização
            getServletContext().log("Erro ao atualizar alimento", e);
            request.setAttribute("erro", "Não foi possível atualizar o alimento.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
        }
    }
}
