package org.example.interdisciplinar.controller.Administrador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.AdministradorDAO;
import org.example.interdisciplinar.model.Administrador;
import org.example.interdisciplinar.utils.Cloudinary;

import java.io.IOException;

// Recebe os dados enviados pelo pop-up de edição
@WebServlet(name = "AtualizarAdministrador", value = "/administradores/atualizar")
@MultipartConfig(
        maxFileSize = 5L * 1024 * 1024,
        maxRequestSize = 6L * 1024 * 1024
)
public class ServletAtualizarAdmin extends HttpServlet {
    private AdministradorDAO administradorDAO;

    @Override
    public void init() {
        administradorDAO = new AdministradorDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            int idAdministrador = Integer.parseInt(request.getParameter("id"));

            // Busca o registro atual para manter a foto quando nenhum arquivo novo for enviado
            Administrador administradorAtual = administradorDAO.listarPorId(idAdministrador);
            if (administradorAtual == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Administrador não encontrado.");
                return;
            }

            Administrador administrador = new Administrador();
            administrador.setId(idAdministrador);
            administrador.setNome(request.getParameter("nome"));
            administrador.setEmail(request.getParameter("email"));
            administrador.setSenha(request.getParameter("senha"));
            administrador.setTelefone(juntarTelefone(
                    request.getParameter("ddd"),
                    request.getParameter("telefone")
            ));

            if (administrador.getNome() == null || administrador.getNome().isBlank()
                    || administrador.getEmail() == null || administrador.getEmail().isBlank()
                    || administrador.getSenha() == null || administrador.getSenha().isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Preencha os campos obrigatórios.");
                return;
            }

            // O Part representa o arquivo enviado pelo input fotoPerfilArquivo
            Part fotoPerfil;
            try {
                fotoPerfil = request.getPart("fotoPerfilArquivo");
            } catch (IllegalStateException excecao) {
                response.sendError(413, "A imagem deve ter no máximo 5 MB.");
                return;
            }
            String urlFoto = administradorAtual.getFotoPerfil();

            // Só envia ao Cloudinary quando o usuário selecionar uma nova imagem
            if (arquivoFoiEnviado(fotoPerfil)) {
                if (!fotoValida(fotoPerfil)) {
                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Envie uma imagem PNG, JPEG ou WEBP de até 5 MB."
                    );
                    return;
                }

                try {
                    urlFoto = Cloudinary.enviarFotoAdministrador(
                            fotoPerfil,
                            idAdministrador
                    );
                } catch (IOException excecao) {
                    log("Erro ao enviar a foto do administrador ao Cloudinary.", excecao);
                    response.sendError(
                            HttpServletResponse.SC_BAD_GATEWAY,
                            "Não foi possível enviar a foto do administrador."
                    );
                    return;
                }
            }

            // Salva no banco a URL nova ou mantém a URL que já estava cadastrada
            administrador.setFotoPerfil(urlFoto);

            if (!administradorDAO.atualizar(administrador)) {
                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Não foi possível atualizar o administrador."
                );
                return;
            }

            response.sendRedirect(request.getContextPath() + "/administradores");
        } catch (NumberFormatException excecao) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Administrador inválido.");
        }
    }

    // Junta o DDD e o telefone, mantendo somente números
    private String juntarTelefone(String ddd, String telefone) {
        String numeroCompleto = (ddd == null ? "" : ddd) + (telefone == null ? "" : telefone);
        numeroCompleto = numeroCompleto.replaceAll("\\D", "");
        return numeroCompleto.isBlank() ? null : numeroCompleto;
    }

    // Verifica se o campo de arquivo recebeu uma foto
    private boolean arquivoFoiEnviado(Part arquivo) {
        return arquivo != null && arquivo.getSize() > 0;
    }

    // Aceita somente os formatos permitidos pelo formulário
    private boolean fotoValida(Part arquivo) {
        String tipoDoArquivo = arquivo.getContentType();

        return "image/png".equals(tipoDoArquivo)
                || "image/jpeg".equals(tipoDoArquivo)
                || "image/webp".equals(tipoDoArquivo);
    }

    @Override
    public void destroy() {
        GerenteConexao.desconectar();
    }
}
