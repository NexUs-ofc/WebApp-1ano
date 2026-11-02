package org.example.interdisciplinar.controller.Administrador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.AdministradorDAO;
import org.example.interdisciplinar.model.Administrador;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListarAdministradores", value = "/administradores")
public class ServletListarAdministrador extends HttpServlet {
    private AdministradorDAO administradorDAO;

    @Override
    public void init(){
        administradorDAO = new AdministradorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer idInicio = null;
        Integer idFim = null;

        try {

            String idInicioTexto = request.getParameter("idInicio");
            String idFimTexto = request.getParameter("idFim");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String ordenar = request.getParameter("ordenar");

            if(idInicioTexto != null && !idInicioTexto.isBlank()){
                idInicio = Integer.parseInt(idInicioTexto);
            }

            if(idFimTexto != null && !idFimTexto.isBlank()){
                idFim = Integer.parseInt(idFimTexto);
            }

            if (idInicio != null && idFim != null && idInicio > idFim) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "O ID inicial não pode ser maior que o ID final."
                );
                return;
            }

            List<Administrador> administradores = administradorDAO.listarFiltrado(idInicio, idFim, nome, email, ordenar);
            request.setAttribute("administradores", administradores);
            request.getRequestDispatcher("/WEB-INF/Views/administradores.jsp").forward(request, response);

        } catch (NumberFormatException nfe){
            request.setAttribute("erro", "O ID dos administradores precisa ser um número inteiro");
            request.getRequestDispatcher("/WEB-INF/Views/ErrorPage.jsp").forward(request, response);

        } catch (RuntimeException re) {
            request.setAttribute("erro", "Não foi possível carregar os Administradores. Tente novamente.");
            request.getRequestDispatcher("/WEB-INF/Views/ErrorPage.jsp").forward(request, response);
        }
    }
    public void destroy(){
        GerenteConexao.desconectar();
    }
}
