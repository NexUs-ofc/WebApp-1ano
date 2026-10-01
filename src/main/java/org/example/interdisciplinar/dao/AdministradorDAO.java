package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.Administrador;

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
    // Método para buscar Administrador por id
    public Administrador listarPorId(int idAdm){

        //Comando de listar pelo id em SQL
        String sqlSelect = "SELECT * FROM tb_administrador WHERE id_admin=?";

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Iniciando executor que: Atribuirá valores ao comando sql e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect)){

            //Seta valores nos '?' do comando SQL
            pstmt.setInt(1, idAdm);

            //Executa a consulta e atribui o valor retornado a variável de resultSet
            ResultSet rs = pstmt.executeQuery();

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            if (rs.next()) {
                Administrador admTemporario = new Administrador();
                admTemporario.setId(rs.getInt(1));
                admTemporario.setNome(rs.getString(2));
                admTemporario.setEmail(rs.getString(3));
                admTemporario.setSenha(rs.getString(4));

                // Commita a ação no banco de dados e retorna o Admin encontrado
                GerenteConexao.commit();
                return admTemporario;
            }
            // Commita a ação no banco de dados e retorna nulo
            GerenteConexao.commit();
            return null;



        //Em casos de erros no banco de dados desfaz a ação
        } catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna nulo
            return null;
        }

    }

    /* Método para atualizar nome do administrador
          requisitando a senha para fazer a mudança */
    public boolean atualizarNome(String senha, String nomeNovo){

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Comando de atualizar em SQL
        String sqlUpdate = "UPDATE tb_administrador SET nome=? WHERE senha=?";

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, nomeNovo);
            pstmt.setString(2, senha);

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

    /* Método para atualizar email do administrador
          requisitando a senha para fazer a mudança */

    public boolean atualizarEmail(String senha, String emailNovo){

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Comando de atualizar em SQL
        String sql = "UPDATE tb_administrador SET email=? WHERE senha=?";

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, emailNovo);
            pstmt.setString(2, senha);
            GerenteConexao.commit();

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
