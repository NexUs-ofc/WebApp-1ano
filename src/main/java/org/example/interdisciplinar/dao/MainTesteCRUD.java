package org.example.interdisciplinar.dao;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.Alimento;

import java.sql.Connection;

public class MainTesteCRUD {
    public static void main(String[] args) {
        Connection conn = GerenteConexao.conectar();

        try {

            AlimentoDAO alimentoDAO = new AlimentoDAO();
            Alimento alimento = new Alimento("7777 5678 345678 7890", "Norgets", "Seara", 1);

            boolean sucesso = alimentoDAO.inserir(alimento);
            System.out.println("Inserção: " + sucesso);

        } finally {
            GerenteConexao.desconectar();
        }
    }
}
