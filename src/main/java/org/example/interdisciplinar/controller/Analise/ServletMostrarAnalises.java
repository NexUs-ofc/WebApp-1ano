package org.example.interdisciplinar.controller.Analise;

// Importa as bibliotecas e classes necessárias para o funcionamento do Servlet
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

// Define o endereço responsável por exibir as análises
@WebServlet(name = "MostrarAnalises", value = "/analises")
public class ServletMostrarAnalises extends HttpServlet {

    // Instância responsável pelas consultas de análise no banco
    private AnaliseDAO analiseDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        analiseDAO = new AnaliseDAO();
    }

    // Método que busca as análises e encaminha os resultados para a JSP
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            // Busca a quantidade de cadastros realizados em cada mês
            List<CadastroMensal> cadastrosMensais = analiseDAO.buscarCadastrosPorMes();

            // Busca os totais de cada tipo de registro
            int totalAdministradores = analiseDAO.contarAdministradores();
            int totalItens = analiseDAO.contarTotalItens();
            int itensProximosVencimento = analiseDAO.contarItensProximosVencimento();
            int totalUsuarios = analiseDAO.contarUsuarios();
            int totalEmpresas = analiseDAO.contarEmpresas();

            // Busca as informações mais relevantes
            CategoriaPopular categoriaPopular = analiseDAO.buscarCategoriaMaisPopular();
            EmpresaRecente empresaRecente = analiseDAO.buscarEmpresaMaisRecente();

            // Envia os resultados das consultas para a JSP
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

            // Registra os detalhes do erro no log do servidor
            getServletContext().log("Erro ao carregar as análises.", e);

            // Informa que não foi possível carregar os dados
            request.setAttribute("erro", "Não foi possível carregar as análises.");

            // Encaminha para a página de erro
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        }
    }
}