package org.example.interdisciplinar.utils;

import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

// Classe responsável por enviar as imagens do sistema para o Cloudinary
public class Cloudinary {

    // Carrega as configurações do arquivo .env ou das variáveis do ambiente
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    // Cria a conexão com o Cloudinary usando as credenciais do projeto
    private static final com.cloudinary.Cloudinary cloudinary = new com.cloudinary.Cloudinary(
            ObjectUtils.asMap(
                    "cloud_name", obterConfiguracao("CLOUDINARY_CLOUD_NAME"),
                    "api_key", obterConfiguracao("CLOUDINARY_API_KEY"),
                    "api_secret", obterConfiguracao("CLOUDINARY_API_SECRET"),
                    "secure", true
            )
    );

    // Impede a criação de objetos, pois todos os métodos desta classe são estáticos
    private Cloudinary() {
    }

    // Envia a foto para a pasta administradores e retorna a URL segura gerada
    public static String enviarFotoAdministrador(Part arquivo, int idAdministrador) throws IOException {

        // Converte o arquivo recebido pelo servlet nos bytes enviados ao Cloudinary
        byte[] conteudoDaImagem;
        try (InputStream entradaDaImagem = arquivo.getInputStream()) {
            conteudoDaImagem = entradaDaImagem.readAllBytes();
        }

        // Usa o id do administrador para substituir a foto anterior em vez de criar duplicatas
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

        // O secure_url é o endereço HTTPS que será armazenado no banco de dados
        Object urlSegura = resultado.get("secure_url");
        if (urlSegura == null) {
            throw new IOException("O Cloudinary não retornou a URL da imagem.");
        }

        return urlSegura.toString();
    }

    // Busca primeiro no .env e depois nas variáveis configuradas no servidor
    private static String obterConfiguracao(String nome) {
        String valor = dotenv.get(nome, System.getenv(nome));

        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException("Configuração ausente: " + nome);
        }

        return valor;
    }
}
