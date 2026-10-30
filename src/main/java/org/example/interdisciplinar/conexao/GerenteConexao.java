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
    public static void commit(){
        try{
            if(conn != null && !conn.isClosed()){
                conn.commit();
            }
        } catch (SQLException sqle){
            System.out.println("Erro ao commitar comando no banco: " + sqle.getMessage());
        }
    }

    public static void rollback(){
        try{
            if(conn != null && !conn.isClosed()){
                conn.rollback();
            }

        } catch (SQLException sqle){
            System.out.println("Erro no rollback: " + sqle.getMessage());
        }

    }

    public static void desconectar(){
        try {
            if (conn != null && !conn.isClosed()){

                conn.close();

            }
        } catch (SQLException sqle){

            System.out.println("Erro no SQL: " + sqle.getMessage());

        } finally {

            conn = null;
        }
    }
}
