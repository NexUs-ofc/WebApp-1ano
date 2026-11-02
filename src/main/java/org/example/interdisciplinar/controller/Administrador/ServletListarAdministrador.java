package org.example.interdisciplinar.controller.Administrador;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.dao.AdministradorDAO;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import org.example.interdisciplinar.model.Administrador;

import java.io.IOException;
import java.util.List;

// Define o endereço responsável pela listagem dos administradores
@WebServlet(name = "ListarAdministradores", value = "/administradores")
public class ServletListarAdministrador extends HttpServlet {

    // Instância responsável pelas operações no banco de dados
    private AdministradorDAO administradorDAO;

    // Método que inicializa a DAO
    @Override
    public void init(){
        administradorDAO = new AdministradorDAO();
    }

    // Método que recebe os filtros e lista os administradores
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Inicializa os IDs como nulos caso nenhum filtro seja informado
        Integer idInicio = null;
        Integer idFim = null;

        try {

            // Recebe os parâmetros enviados pela página
            String idInicioTexto = request.getParameter("idInicio");
            String idFimTexto = request.getParameter("idFim");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String ordenar = request.getParameter("ordenar");

            // Converte o ID inicial para inteiro caso tenha sido informado
            if(idInicioTexto != null && !idInicioTexto.isBlank()){
                idInicio = Integer.parseInt(idInicioTexto);
            }

            // Converte o ID final para inteiro caso tenha sido informado
            if(idFimTexto != null && !idFimTexto.isBlank()){
                idFim = Integer.parseInt(idFimTexto);
            }

            // Verifica se o ID inicial é maior que o ID final
            if (idInicio != null && idFim != null && idInicio > idFim) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "O ID inicial não pode ser maior que o ID final."
                );
                return;
            }

            // Busca os administradores de acordo com os filtros informados
            List<Administrador> administradores = administradorDAO.listarFiltrado(idInicio, idFim, nome, email, ordenar);

            // Envia a lista de administradores para a página
            request.setAttribute("administradores", administradores);

            // Encaminha para a página de administradores
            request.getRequestDispatcher("/WEB-INF/Views/administradores.jsp").forward(request, response);

        } catch (NumberFormatException nfe){

            // Registra o erro de conversão no log do servidor
            getServletContext().log("Erro ao formatar o número.", nfe);

            // Informa que os IDs precisam ser números inteiros
            request.setAttribute("erro", "O ID dos administradores precisa ser um número inteiro");

            // Encaminha para a página de erro
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (FalhaConexaoException e) {

            // Registra os detalhes da falha de conexão no log
            getServletContext().log("Falha ao conectar ao banco de dados.", e);

            // Define uma mensagem compreensível para o administrador
            request.setAttribute(
                    "erro",
                    "Não foi possível estabelecer conexão com o banco de dados."
            );

            // Encaminha para a página de erro
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {

            // Registra os detalhes do erro de persistência no log
            getServletContext().log("Erro ao listar os administradores", e);

            // Informa que ocorreu um problema durante a listagem
            request.setAttribute(
                    "erro",
                    "Ocorreu um erro ao listar os administradores."
            );

            // Encaminha para a página de erro
            request.getRequestDispatcher("/WEB-INF/errorpage.jsp").forward(request, response);

        }
    }
}