package org.example.interdisciplinar.controller.Alimento;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.dao.AlimentoDAO;
import org.example.interdisciplinar.model.Alimento;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import java.io.IOException;

// Define o endereço responsável pelo cadastro de alimentos
@WebServlet(name = "CriarAlimentos", value = "/admin/alimentos/insert")
public class ServletCriarAlimento extends HttpServlet {

    private AlimentoDAO alimentoDAO;

    // Método que inicializa a DAO
    @Override
    public void init(){
        alimentoDAO = new AlimentoDAO();
    }

    // Método que recebe os dados do formulário e cadastra um alimento
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            // Recupera os parâmetros enviados pelo formulário
            String codigoBarras = request.getParameter("codigoBarras");
            String nome = request.getParameter("nome");
            String marca = request.getParameter("marca");
            String idCategoriaTexto = request.getParameter("idCategoria");

            // Verifica se os campos obrigatórios foram preenchidos
            if (codigoBarras == null || codigoBarras.isBlank()
                    || nome == null || nome.isBlank()
                    || marca == null || marca.isBlank()
                    || idCategoriaTexto == null || idCategoriaTexto.isBlank()){

                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Todos os campos precisam ser preenchidos!");
                return;
            }

            // Converte o ID da categoria para inteiro
            int idCategoria = Integer.parseInt(idCategoriaTexto);

            // Verifica se o ID da categoria é válido
            if (idCategoria <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da categoria inválido!");
                return;
            }

            // Cria o objeto alimento com os dados recebidos
            Alimento alimento = new Alimento(codigoBarras, nome, marca, idCategoria);

            // Envia o alimento para ser cadastrado no banco
            boolean inserido = alimentoDAO.inserir(alimento);

            // Verifica se o cadastro foi realizado
            if(inserido){

                // Redireciona para a listagem de alimentos
                response.sendRedirect(
                        request.getContextPath() + "/admin/alimentos"
                );

            } else {
                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Não foi possível inserir o alimento."
                );
            }

        } catch (NumberFormatException e) {
            // Registra o erro de conversão no log
            getServletContext().log("Erro ao converter o ID da categoria", e);

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "O ID da categoria precisa ser um número inteiro");

        } catch (FalhaConexaoException e) {
            // Registra os detalhes da falha de conexão no log
            getServletContext().log("Falha ao conectar ao banco de dados", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao cadastrar alimento", e);

            request.setAttribute("erro", "Não foi possível cadastrar o alimento.");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
        }
    }
}