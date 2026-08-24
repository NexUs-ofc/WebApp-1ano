package org.example.interdisciplinar.dao;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;

public class ConexaoDAO {

        private static Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();


        public Connection getConnection () {
            try {


                Class.forName("org.postgresql.Driver");


                String url = dotenv.get("DB_URL", System.getenv("DB_URL"));
                String user = dotenv.get("DB_USER", System.getenv("DB_USER"));
                String password = dotenv.get("DB_PASSWORD", System.getenv("DB_PASSWORD"));

                System.out.println("Você se conectou ao banco do Ceris!");
                return DriverManager.getConnection(url, user, password);

            } catch (SQLException sqle) {
                sqle.printStackTrace();
                System.out.println("Erro ao se conectar sql");
                return null;

            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
                System.out.println("Erro ao se conectar cnf");
                return null;
            }
        }
        // Método para fechar a conexão
        public void closeConnection(Connection conn){
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqle){
                    sqle.printStackTrace();
                }
            }
        }

}