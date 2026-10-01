package org.example.interdisciplinar.controller.Administrador;

// Importando os pacotes
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

// Definindo um nome interno para o servlet e a url que ativa ele
@WebServlet(name = "ListarAdministradores", value = "/administradores")

// Abertura da classe de servlet
public class ServletListarAdmin extends HttpServlet {


    private AdministradorDAO administradorDAO;

    @Override
    public void init(){
        administradorDAO = new AdministradorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Administrador> administradores = administradorDAO.listar();

        request.setAttribute("administradores", administradores);
        request.getRequestDispatcher("/WEB-INF/Views/administradores.jsp").forward(request, response);
    }
    @Override
    public void destroy(){
        GerenteConexao.desconectar();
    }
}
