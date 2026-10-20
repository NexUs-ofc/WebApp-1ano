package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.Administrador;
import org.example.interdisciplinar.model.ItemDespensa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Abertura da classe
public class AdministradorDAO {

    // Método para inserir um Administrador no banco de dados
    public boolean inserir(Administrador administrador){

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Comando de inserir em SQL
        String sqlInsert = "INSERT INTO tb_administrador(nome, email, senha) VALUES (?, ?, ?)";

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();


        //Iniciando executor que vai adicionar valores no comando SQL e executará o comando
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, administrador.getNome());
            pstmt.setString(2, administrador.getEmail());
            pstmt.setString(3, administrador.getSenha());

            //Se ação tiver dados certo commita e retorna true
            if (linhasAfetadas > 0) {
                GerenteConexao.commit();
                return true;
            }

            //Se não desfaz a ação e retorna false
            GerenteConexao.rollback();
            return false;

        //Em casos de erros no banco de dados desfaz a ação
        } catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna false
            return false;
        }
    }

    // Método para listar todos os administradores do banco de dados
    public List<Administrador> listar(){

        //Comando de listar tudo em SQL
        String sqlSelect = "SELECT * FROM tb_administrador";

        // Criando a lista para armazenar os registros da consulta
        List<Administrador> administradores = new ArrayList<>();

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Cria executor que receberá comando e executará ele
        try (Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(sqlSelect);

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while(rs.next()){
                Administrador admTemporario = new Administrador();
                admTemporario.setId(rs.getInt(1));
                admTemporario.setNome(rs.getString(2));
                admTemporario.setEmail(rs.getString(3));
                admTemporario.setSenha(rs.getString(4));
                administradores.add(admTemporario);
            }

            // Commita a ação no banco de dados
            GerenteConexao.commit();

            // Retrona a lista de administradores
            return administradores;

        //Em casos de erros no banco de dados desfaz a ação
        } catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna a lista
            return administradores;
        }
    }

    public boolean atualizar(Administrador administrador){
        String sql = "UPDATE tb_administrador SET nome=?, email=?, senha=? WHERE id_admin=?";
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setString(1, administrador.getNome());
            pstmt.setString(2, administrador.getEmail());
            pstmt.setString(3, administrador.getSenha());
            pstmt.setInt(4, administrador.getId());
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }


    /* Método para deletar um administrador do banco de dados
        requisitando a senha para deletar */
    public boolean deletar(String senha){

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Comando de deletar em SQL
        String sqlDelete = "DELETE FROM tb_adminstrador WHERE senha=?";

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)){
            //Seta valor nos '?' do comando SQL
            pstmt.setString(1, senha);

            //Se ação tiver dados certo commita e retorna true
            if (linhasAfetadas > 0) {
                GerenteConexao.commit();
                return true;
            }

            //Se não desfaz a ação e retorna false
            GerenteConexao.rollback();
            return false;

        //Em casos de erros no banco de dados desfaz a ação
        } catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna false
            return false;
        }
    }
}
