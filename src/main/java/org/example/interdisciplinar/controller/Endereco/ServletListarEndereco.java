package org.example.interdisciplinar.controller.Endereco;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.dao.EnderecoDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Endereco;

import java.io.IOException;
import java.util.List;

// Define o endereço responsável pela listagem dos endereços
@WebServlet(name = "ListarEnderecos", value = "/admin/enderecos")
public class ServletListarEndereco extends HttpServlet {

    private EnderecoDAO enderecoDAO;

    // Método que inicializa a DAO
    @Override
    public void init(){
        enderecoDAO = new EnderecoDAO();
    }

    // Método que recebe os filtros e lista os endereços
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        // Inicializa os IDs como nulos caso nenhum filtro seja informado
        Integer idInicio = null;
        Integer idFim = null;

        try {
            // Recupera os filtros enviados pela página
            String idInicioTexto = request.getParameter("idInicio");
            String idFimTexto = request.getParameter("idFim");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            String ordenar = request.getParameter("ordenar");

            // Converte o ID inicial para inteiro, caso tenha sido informado
            if (idInicioTexto != null && !idInicioTexto.isBlank()){
                idInicio = Integer.parseInt(idInicioTexto.trim());
            }

            // Converte o ID final para inteiro, caso tenha sido informado
            if (idFimTexto != null && !idFimTexto.isBlank()){
                idFim = Integer.parseInt(idFimTexto.trim());
            }

            // Verifica se os IDs informados são válidos
            if ((idInicio != null && idInicio <= 0) ||
                    (idFim != null && idFim <= 0)) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Verifica se o ID inicial é maior que o ID final
            if (idInicio != null && idFim != null && idInicio > idFim) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "O ID inicial não pode ser maior que o ID final."
                );
                return;
            }

            // Define uma ordenação padrão caso nenhuma seja informada
            if (ordenar == null || ordenar.isBlank()) {
                ordenar = "id";
            }

            // Busca os endereços de acordo com os filtros informados
            List<Endereco> enderecos = enderecoDAO.listarFiltrado(idInicio, idFim, cidade, estado, ordenar);

            // Envia a lista de endereços para a página
            request.setAttribute("enderecos", enderecos);

            // Encaminha para a página de listagem
            request.getRequestDispatcher("/WEB-INF/Views/enderecos.jsp").forward(request, response);

        } catch (NumberFormatException e){

            // Registra o erro de conversão no log do servidor
            getServletContext().log("Erro ao formatar o número", e);

            request.setAttribute("erro", "O ID do endereço precisa ser um número inteiro");
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (FalhaConexaoException e) {

            // Registra os detalhes da falha de conexão no log
            getServletContext().log("Falha ao conectar ao banco de dados", e);

            request.setAttribute(
                    "erro",
                    "Não foi possível estabelecer conexão com o banco de dados."
            );

            // Encaminha para a página de erro
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {

            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao listar os endereços", e);

            request.setAttribute(
                    "erro",
                    "Ocorreu um erro ao listar os endereços."
            );

            // Encaminha para a página de erro
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);
        }
    }
}