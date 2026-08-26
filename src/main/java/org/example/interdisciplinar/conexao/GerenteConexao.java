package org.example.interdisciplinar.conexao;


import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class GerenteConexao {

    private static Connection conn;

    public static Connection conectar(){

        try {

            if(conn.isClosed() || conn == null){

                Conexao conexao = new Conexao();
                conn = conexao.getConnection();

            }
        } catch (SQLException sqle) {
            System.out.println("Erro no SQL: " + sqle.getMessage());
        }

        return conn;

    }

    public static void desconectar(){
        try {
            if (! conn.isClosed() || conn != null){

                conn.close();

            }
        } catch (SQLException sqle){

            System.out.println("Erro no SQL: " + sqle.getMessage());

        } finally {

            conn = null;
        }
    }
}
