package org.example.interdisciplinar.controller.Analise;

// Todos os imports necessários estão incluídos
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interdisciplinar.dao.AnaliseDAO;
import org.example.interdisciplinar.dto.CadastroMensal;
import org.example.interdisciplinar.dto.CategoriaPopular;
import org.example.interdisciplinar.dto.EmpresaRecente;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MostrarAnalises", value = "/analises")
public class ServletMostrarAnalises extends HttpServlet {

    private AnaliseDAO analiseDAO;

    // Inicializa a DAO
    @Override
    public void init() {
        analiseDAO = new AnaliseDAO();
    }

    // Método que busca as análises e encaminha para a JSP
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            // Busca os cadastros mensais
            List<CadastroMensal> cadastrosMensais = analiseDAO.buscarCadastrosPorMes();

            // Busca as quantidades
            int totalAdministradores = analiseDAO.contarAdministradores();
            int totalItens = analiseDAO.contarTotalItens();
            int itensProximosVencimento = analiseDAO.contarItensProximosVencimento();
            int totalUsuarios = analiseDAO.contarUsuarios();
            int totalEmpresas = analiseDAO.contarEmpresas();

            // Busca informações específicas
            CategoriaPopular categoriaPopular = analiseDAO.buscarCategoriaMaisPopular();
            EmpresaRecente empresaRecente = analiseDAO.buscarEmpresaMaisRecente();

            // Envia os resultados para a JSP
            request.setAttribute("cadastrosMensais", cadastrosMensais);
            request.setAttribute("totalAdministradores", totalAdministradores);
            request.setAttribute("totalItens", totalItens);
            request.setAttribute("itensProximosVencimento", itensProximosVencimento);
            request.setAttribute("totalUsuarios", totalUsuarios);
            request.setAttribute("totalEmpresas", totalEmpresas);
            request.setAttribute("categoriaPopular", categoriaPopular);
            request.setAttribute("empresaRecente", empresaRecente);

            // Encaminha para a página de análises
            request.getRequestDispatcher("/analises.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {

            // Registra o erro de persistência
            getServletContext().log("Erro ao carregar as análises.", e);

            request.setAttribute("erro", "Não foi possível carregar as análises.");
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        }
    }
}
