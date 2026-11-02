
package org.example.interdisciplinar.controller.Empresa;

// Todos os imports necessários
import org.example.interdisciplinar.dao.EmpresaDAO;
import org.example.interdisciplinar.model.Empresa;
import org.example.interdisciplinar.utils.Regex;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.exception.FalhaConexaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CriarEmpresa", value = "/empresas/criar")
public class ServletCriarEmpresa extends HttpServlet {

    private EmpresaDAO empresaDAO;

    // Método que inicializa a DAO
    @Override
    public void init() {
        empresaDAO = new EmpresaDAO();
    }

    // Método que recebe a solicitação de cadastro
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupera os parâmetros enviados pelo formulário
        String nome = request.getParameter("nome");
        String cnpj = request.getParameter("cnpj");
        String idEndereco = request.getParameter("idEndereco");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");

        // Verifica se todos os campos foram preenchidos
        if (nome == null || nome.isBlank()
                || cnpj == null || cnpj.isBlank()
                || idEndereco == null || idEndereco.isBlank()
                || email == null || email.isBlank()
                || telefone == null || telefone.isBlank()) {

            request.setAttribute("erro", "Preencha todos os campos.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }

        // Valida os campos utilizando Regex
        if (!Regex.validarCnpj(cnpj)
                || !Regex.validarEmail(email)
                || !Regex.validarTelefone(telefone)) {

            request.setAttribute("erro", "Verifique os dados informados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }

        try {
            // Converte o ID do endereço para inteiro
            int idEnderecoConvertido = Integer.parseInt(idEndereco);

            // Verifica se o ID do endereço é válido
            if (idEnderecoConvertido <= 0) {
                request.setAttribute("erro", "Endereço inválido.");
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                return;
            }

            // Cria o objeto empresa com os dados informados
            Empresa empresa = new Empresa();
            empresa.setNome(nome);
            empresa.setCnpj(cnpj);
            empresa.setIdEndereco(idEnderecoConvertido);
            empresa.setEmail(email);
            empresa.setTelefone(telefone);

            // Realiza o cadastro da empresa
            empresaDAO.inserir(empresa);

            // Redireciona após o cadastro
            response.sendRedirect(request.getContextPath() + "/empresas");

        } catch (NumberFormatException e) {
            // Trata IDs que não possuem formato numérico
            request.setAttribute("erro", "ID do endereço inválido.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (FalhaConexaoException e) {
            // Registra problemas de conexão com o banco
            getServletContext().log("Falha na conexão com o banco de dados", e);

            request.setAttribute("erro", "Não foi possível conectar ao banco de dados.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);

        } catch (ErroPersistenciaException e) {
            // Registra falhas durante o cadastro
            getServletContext().log("Erro ao criar empresa", e);

            request.setAttribute("erro", "Não foi possível cadastrar a empresa.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}
