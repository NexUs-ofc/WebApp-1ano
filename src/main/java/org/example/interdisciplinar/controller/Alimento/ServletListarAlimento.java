package org.example.interdisciplinar.controller.Alimento;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.AlimentoDAO;
import org.example.interdisciplinar.dao.UsuarioDAO;
import org.example.interdisciplinar.model.Usuario;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListarAlimentos", value = "/alimentos")
public class ServletListarAlimento extends HttpServlet {
    private AlimentoDAO alimentoDAO;

    @Override
    public void init(){
        alimentoDAO = new AlimentoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer idInicio = null;
        Integer idFim = null;
        String idInicioTexto = request.getParameter("idInicio");
        String idFimTexto = request.getParameter("idFim");
        String nome = request.getParameter("nome");
        String marca = request.getParameter("marca");
        String categoria = request.getParameter("categoria");

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

    }
    public void destroy(){
        GerenteConexao.desconectar();
    }
}
