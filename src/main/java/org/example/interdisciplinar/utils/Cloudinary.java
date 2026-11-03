
package org.example.interdisciplinar.utils;

import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

public class Cloudinary {

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private static final com.cloudinary.Cloudinary cloudinary =
            new com.cloudinary.Cloudinary(
                    ObjectUtils.asMap(
                            "cloud_name", obterConfiguracao("CLOUDINARY_CLOUD_NAME"),
                            "api_key", obterConfiguracao("CLOUDINARY_API_KEY"),
                            "api_secret", obterConfiguracao("CLOUDINARY_API_SECRET"),
                            "secure", true
                    )
            );

    private Cloudinary() {
    }

    // Envia uma nova foto ao Cloudinary.
    public static String enviarFotoAdministrador(Part arquivo) throws IOException {

        if (arquivo == null || arquivo.getSize() == 0) {
            return null;
        }

        if (arquivo.getSize() > 5 * 1024 * 1024) {
            throw new IOException("A imagem deve ter no máximo 5 MB.");
        }

        String tipo = arquivo.getContentType();

        if (!"image/jpeg".equalsIgnoreCase(tipo)
                && !"image/png".equalsIgnoreCase(tipo)
                && !"image/webp".equalsIgnoreCase(tipo)) {
            throw new IOException("Formato de imagem inválido.");
        }

        byte[] conteudoDaImagem;

        try (InputStream entradaDaImagem = arquivo.getInputStream()) {
            conteudoDaImagem = entradaDaImagem.readAllBytes();
        }

        Map<?, ?> resultado = cloudinary.uploader().upload(
                conteudoDaImagem,
                ObjectUtils.asMap(
                        "folder", "administradores",
                        "public_id", UUID.randomUUID().toString(),
                        "resource_type", "image"
                )
        );

        Object urlSegura = resultado.get("secure_url");

        if (urlSegura == null) {
            throw new IOException("O Cloudinary não retornou a URL da imagem.");
        }

        return urlSegura.toString();
    }

    // Mantém o método original que utiliza o ID do administrador.
    public static String enviarFotoAdministrador(Part arquivo, int idAdministrador)
            throws IOException {

        if (arquivo == null || arquivo.getSize() == 0) {
            return null;
        }

        if (arquivo.getSize() > 5 * 1024 * 1024) {
            throw new IOException("A imagem deve ter no máximo 5 MB.");
        }

        String tipo = arquivo.getContentType();

        if (!"image/jpeg".equalsIgnoreCase(tipo)
                && !"image/png".equalsIgnoreCase(tipo)
                && !"image/webp".equalsIgnoreCase(tipo)) {
            throw new IOException("Formato de imagem inválido.");
        }

        byte[] conteudoDaImagem;

        try (InputStream entradaDaImagem = arquivo.getInputStream()) {
            conteudoDaImagem = entradaDaImagem.readAllBytes();
        }

        Map<?, ?> resultado = cloudinary.uploader().upload(
                conteudoDaImagem,
                ObjectUtils.asMap(
                        "folder", "administradores",
                        "public_id", "administrador-" + idAdministrador,
                        "resource_type", "image",
                        "overwrite", true,
                        "invalidate", true
                )
        );

        Object urlSegura = resultado.get("secure_url");

        if (urlSegura == null) {
            throw new IOException("O Cloudinary não retornou a URL da imagem.");
        }

        return urlSegura.toString();
    }

    private static String obterConfiguracao(String nome) {
        String valor = dotenv.get(nome, System.getenv(nome));

        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException("Configuração ausente: " + nome);
        }

        return valor;
    }
}
