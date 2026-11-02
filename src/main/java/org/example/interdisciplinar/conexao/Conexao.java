package org.example.interdisciplinar.conexao;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // Carrega as configurações do arquivo .env.
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    // Método responsável por estabelecer uma conexão com o banco e propaga SQLException caso tenha algum problema na conexão.
    public Connection getConnection() throws SQLException {

        // Recupera a URL do banco de dados na .env
        String url = dotenv.get("DB_URL", System.getenv("DB_URL"));

        // Recupera o usuário do banco de dados na .env
        String user = dotenv.get("DB_USER", System.getenv("DB_USER"));

        // Recupera a senha do banco de dados na .env
        String password = dotenv.get("DB_PASSWORD", System.getenv("DB_PASSWORD"));

        // Estabelece e retorna uma nova conexão com o banco.
        return DriverManager.getConnection(url, user, password);
    }
}