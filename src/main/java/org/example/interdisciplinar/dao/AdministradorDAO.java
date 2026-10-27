package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.Administrador;
import org.example.interdisciplinar.model.ConfiguracaoAlerta;
import org.example.interdisciplinar.model.ItemDespensa;
import org.example.interdisciplinar.model.Usuario;

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
        String sqlInsert = "INSERT INTO tb_administrador(nome, email, senha, telefone) VALUES (?, ?, ?, ?)";

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();


        //Iniciando executor que vai adicionar valores no comando SQL e executará o comando
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, administrador.getNome());
            pstmt.setString(2, administrador.getEmail());
            pstmt.setString(3, administrador.getSenha());
            pstmt.setString(4, administrador.getTelefone());

            //Se ação tiver dados certo commita e retorna true
            linhasAfetadas = pstmt.executeUpdate();

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
                admTemporario.setSenha(rs.getString(5));
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
        String sql = "UPDATE tb_administrador SET nome=?, email=?, senha=?, telefone=? WHERE id_admin=?";
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setString(1, administrador.getNome());
            pstmt.setString(2, administrador.getEmail());
            pstmt.setString(3, administrador.getSenha());
            pstmt.setString(4, administrador.getTelefone());
            pstmt.setInt(5, administrador.getId());
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
    public List<Administrador> listarFiltrado(
            Integer idAdminInicio,
            Integer idAdminFim,
            String nome,
            String email,
            String ordenar
    ){
        StringBuilder sql = new StringBuilder("SELECT * FROM tb_administrador WHERE 1=1");
        List<Object> parametrosDoFiltro = new ArrayList<>();
        List<Administrador> administradores = new ArrayList<>();

        if (idAdminInicio != null) {
            sql.append(" AND id_admin >=?");
            parametrosDoFiltro.add(idAdminInicio);
        }
        if (idAdminFim != null) {
            sql.append(" AND id_admin <=?");
            parametrosDoFiltro.add(idAdminFim);
        }
        if (nome != null && !nome.isBlank()) {
            sql.append(" AND nome ILIKE ?");
            parametrosDoFiltro.add("%" + nome + "%");
        }

        if (email != null && !email.isBlank()) {
            sql.append(" AND email ILIKE ?");
            parametrosDoFiltro.add("%" + email + "%");
        }

        switch (ordenar) {
            case "id":
                sql.append(" ORDER BY id_admin");
                break;

            case "nome":
                sql.append(" ORDER BY nome");
                break;

            case "email":
                sql.append(" ORDER BY email");
                break;

            default:
                sql.append(" ORDER BY id_admin");
                break;
        }

        //Cria executor que receberá comando e executará ele
        try (Connection conn = GerenteConexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())
        ) {

            for (int i = 0; i < parametrosDoFiltro.size(); i++) {
                pstmt.setObject(i + 1, parametrosDoFiltro.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                //Enquanto tiver mais registros cria novos objetos e adiciona à lista
                while(rs.next()){
                    Administrador admTemporario = new Administrador();
                    admTemporario.setId(rs.getInt(1));
                    admTemporario.setNome(rs.getString(2));
                    admTemporario.setEmail(rs.getString(3));
                    admTemporario.setSenha(rs.getString(4));
                    admTemporario.setTelefone(rs.getString(5));
                    administradores.add(admTemporario);
                }

                // Retrona a lista de administradores
                return administradores;

            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return administradores;
        }
    }

    public Administrador buscarporId(int id){
        String sql = "SELECT * FROM tb_administrador WHERE id=?";
        try(
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, id);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    Administrador administrador = new Administrador(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                    return administrador;
                }
            }

        } catch (SQLException sqle){
            System.out.println("Erro de SQL: " + sqle.getMessage());
        }
        return null;
    }
}
