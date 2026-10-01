package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.*;
import org.example.interdisciplinar.model.*;
import java.util.*;

import java.sql.Connection;
import java.time.LocalDate;

public class MainTesteCRUD {
    public static void main(String[] args) {
        Connection conn = GerenteConexao.conectar();
        System.out.println(conn);

        AlimentoDAO alimentoDAO = new AlimentoDAO();
        List<Alimento> alimentos = alimentoDAO.listar();

        for (Alimento a : alimentos){
            System.out.println(a);
        }
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        List<Endereco> enderecos = enderecoDAO.listar();

        for (Endereco e : enderecos){
            System.out.println(e);
        }


    }
}