package org.example.interdisciplinar.conexao;

import java.sql.Connection;
import java.sql.SQLException;

import org.example.interdisciplinar.exception.FalhaConexaoException;

public class GerenteConexao {

    // Método responsável por estabelecer uma conexão com o banco de dados.
    public static Connection conectar() {

        try {

            // Cria um objeto da classe Conexao, que tem informações necessárias para acessar o banco.
            Conexao conexao = new Conexao();

            // Obtém e retorna uma nova conexão com o banco.
            return conexao.getConnection();

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra algum problema ao estabelecer a conexão.
            throw new FalhaConexaoException(
                    "Não foi possível estabelecer conexão com o banco de dados.",
                    e
            );
        }
    }
}