package org.example.interdisciplinar.conexao;


import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class GerenteConexao {

    private static Connection conn;

    public static Connection conectar(){

        try {

            if(conn == null || conn.isClosed()){

                Conexao conexao = new Conexao();
                conn = conexao.getConnection();
                // Desativa o AutoCommit
                if (conn != null) {
                    conn.setAutoCommit(false);
                }

            }
        } catch (SQLException sqle) {
            System.out.println("Erro no SQL: " + sqle.getMessage());
        }

        return conn;

    }
}
