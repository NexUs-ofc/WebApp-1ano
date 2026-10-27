package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Abertura da classe
public class AdministradorDAO {

    // Método para inserir um administrador no banco de dados
    public boolean inserir(Administrador administrador) {
        // Comando de inserir em SQL
        String sql = "INSERT INTO tb_administrador " +
                "(nome, email, senha, telefone, url_foto) VALUES (?, ?, ?, ?, ?)";

        // Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        // Inicia o executor que adiciona os valores e executa o comando SQL
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Seta os valores nos '?' do comando SQL
            pstmt.setString(1, administrador.getNome());
            pstmt.setString(2, administrador.getEmail());
            pstmt.setString(3, administrador.getSenha());
            pstmt.setString(4, administrador.getTelefone());
            pstmt.setString(5, administrador.getFotoPerfil());

            return finalizarAlteracao(pstmt.executeUpdate());

        // Em casos de erros no banco de dados, desfaz a ação
        } catch (SQLException sqle) {
            // Lista todos os erros
            sqle.printStackTrace();

            // Desfaz a ação
            GerenteConexao.rollback();

            // Retorna false
            return false;
        }
    }

    // Método para listar todos os administradores do banco de dados
    public List<Administrador> listar() {
        // Comando de listar tudo em SQL
        String sql = "SELECT id_administrador, nome, email, senha, telefone, url_foto " +
                "FROM tb_administrador ORDER BY id_administrador";

        // Cria a lista para armazenar os registros da consulta
        List<Administrador> administradores = new ArrayList<>();

        // Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        // Cria o executor que recebe o comando e executa a consulta
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Enquanto tiver registros, cria os objetos e adiciona à lista
            while (rs.next()) {
                administradores.add(criarAdministrador(rs));
            }

            // Confirma a ação no banco de dados
            GerenteConexao.commit();

        // Em casos de erros no banco de dados, desfaz a ação
        } catch (SQLException sqle) {
            // Lista todos os erros
            sqle.printStackTrace();

            // Desfaz a ação
            GerenteConexao.rollback();
        }

        // Retorna a lista de administradores
        return administradores;
    }

    // Método para buscar um administrador pelo id
    public Administrador listarPorId(int idAdministrador) {
        String sql = "SELECT id_administrador, nome, email, senha, telefone, url_foto " +
                "FROM tb_administrador WHERE id_administrador=?";
        Connection conn = GerenteConexao.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idAdministrador);

            try (ResultSet rs = pstmt.executeQuery()) {
                Administrador administrador = rs.next() ? criarAdministrador(rs) : null;
                GerenteConexao.commit();
                return administrador;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            GerenteConexao.rollback();
            return null;
        }
    }

    // Método para atualizar os dados do administrador pelo id
    public boolean atualizar(Administrador administrador) {
        String sql = "UPDATE tb_administrador " +
                "SET nome=?, email=?, senha=?, telefone=?, url_foto=? WHERE id_administrador=?";
        Connection conn = GerenteConexao.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, administrador.getNome());
            pstmt.setString(2, administrador.getEmail());
            pstmt.setString(3, administrador.getSenha());
            pstmt.setString(4, administrador.getTelefone());
            pstmt.setString(5, administrador.getFotoPerfil());
            pstmt.setInt(6, administrador.getId());

            return finalizarAlteracao(pstmt.executeUpdate());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            GerenteConexao.rollback();
            return false;
        }
    }

    // Método para deletar um administrador pelo id
    public boolean deletar(int idAdministrador) {
        // Comando de deletar em SQL
        String sql = "DELETE FROM tb_administrador WHERE id_administrador=?";

        // Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        // Inicia o executor que atribui o valor e executa o comando SQL
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Seta o valor no '?' do comando SQL
            pstmt.setInt(1, idAdministrador);
            return finalizarAlteracao(pstmt.executeUpdate());

        // Em casos de erros no banco de dados, desfaz a ação
        } catch (SQLException sqle) {
            // Lista todos os erros
            sqle.printStackTrace();

            // Desfaz a ação
            GerenteConexao.rollback();

            // Retorna false
            return false;
        }
    }

    // Evita repetir a mesma confirmação em todas as alterações
    private boolean finalizarAlteracao(int linhasAfetadas) {
        // Se a ação alterou dados, confirma e retorna true
        if (linhasAfetadas > 0) {
            GerenteConexao.commit();
            return true;
        }

        // Se não alterou dados, desfaz a ação e retorna false
        GerenteConexao.rollback();
        return false;
    }

    // Converte uma linha do banco em um administrador
    private Administrador criarAdministrador(ResultSet rs) throws SQLException {
        Administrador administrador = new Administrador();
        administrador.setId(rs.getInt("id_administrador"));
        administrador.setNome(rs.getString("nome"));
        administrador.setEmail(rs.getString("email"));
        administrador.setSenha(rs.getString("senha"));
        administrador.setTelefone(rs.getString("telefone"));
        administrador.setFotoPerfil(rs.getString("url_foto"));
        return administrador;
    }
}
