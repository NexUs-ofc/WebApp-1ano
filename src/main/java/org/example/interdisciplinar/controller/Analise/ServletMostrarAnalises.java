package org.example.interdisciplinar.controller.Analise;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.example.interdisciplinar.dao.AnaliseDAO;
import org.example.interdisciplinar.dto.CadastroMensal;
import org.example.interdisciplinar.dto.CategoriaPopular;
import org.example.interdisciplinar.dto.EmpresaRecente;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "MostrarAnalises", value = "/analises")
public class ServletMostrarAnalises extends HttpServlet {

    private AnaliseDAO analiseDAO;

    @Override
    public void init() {
        analiseDAO = new AnaliseDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // A mesma identificação de sessão utilizada no ServletLogin.
        HttpSession sessao = request.getSession(false);

        if (sessao == null ||
                !(sessao.getAttribute("administradorId") instanceof Integer)) {

            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        try {
            List<CadastroMensal> cadastrosMensais =
                    analiseDAO.buscarCadastrosPorMes();

            int novosCadastrosMes =
                    analiseDAO.contarNovosCadastrosMes();

            int cadastrosMesAnterior =
                    analiseDAO.contarCadastrosMesAnterior();

            int totalAdministradores =
                    analiseDAO.contarAdministradores();

            int totalItens =
                    analiseDAO.contarTotalItens();

            int itensProximosVencimento =
                    analiseDAO.contarItensProximosVencimento();

            int totalUsuarios =
                    analiseDAO.contarUsuarios();

            int totalEmpresas =
                    analiseDAO.contarEmpresas();

            int empresasSemData =
                    analiseDAO.contarEmpresasSemData();

            CategoriaPopular categoriaPopular =
                    analiseDAO.buscarCategoriaMaisPopular();

            EmpresaRecente empresaRecente =
                    analiseDAO.buscarEmpresaMaisRecente();

            // Calcula a variação dos cadastros em relação ao mês anterior.
            Integer variacaoCadastros = cadastrosMesAnterior == 0
                    ? null
                    : Math.round((novosCadastrosMes - cadastrosMesAnterior)
                    * 100f / cadastrosMesAnterior);

            // Envia todos os dados para a JSP.
            request.setAttribute("cadastrosMensais", cadastrosMensais);
            request.setAttribute("novosCadastrosMes", novosCadastrosMes);
            request.setAttribute("cadastrosMesAnterior", cadastrosMesAnterior);
            request.setAttribute("variacaoCadastros", variacaoCadastros);
            request.setAttribute("totalAdministradores", totalAdministradores);
            request.setAttribute("totalItens", totalItens);
            request.setAttribute("itensProximosVencimento", itensProximosVencimento);
            request.setAttribute("totalUsuarios", totalUsuarios);
            request.setAttribute("totalEmpresas", totalEmpresas);
            request.setAttribute("empresasSemData", empresasSemData);
            request.setAttribute("categoriaPopular", categoriaPopular);
            request.setAttribute("empresaRecente", empresaRecente);
            request.setAttribute("analisesCarregadas", true);

            // Encaminha para a index.jsp com os dados carregados.
            request.getRequestDispatcher("/index.jsp")
                    .forward(request, response);

        } catch (FalhaConexaoException | ErroPersistenciaException e) {

            getServletContext().log("Erro ao carregar as análises.", e);

            request.setAttribute("erro",
                    "Não foi possível carregar as análises.");

            request.getRequestDispatcher("/WEB-INF/errorpage.jsp")
                    .forward(request, response);
        }
    }
}
