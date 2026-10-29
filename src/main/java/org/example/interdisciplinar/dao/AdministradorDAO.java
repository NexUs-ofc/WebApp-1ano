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

        // Controle da quantidade de linhas alteradas no banco de dados
        int linhasAfetadas = 0;

        // Inicia o executor que adiciona os valores e executa o comando SQL
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Seta os valores nos '?' do comando SQL
            pstmt.setString(1, administrador.getNome());
            pstmt.setString(2, administrador.getEmail());
            pstmt.setString(3, administrador.getSenha());
            pstmt.setString(4, administrador.getTelefone());
            pstmt.setString(5, administrador.getFotoPerfil());

            // Executa o comando e armazena a quantidade de linhas inseridas
            linhasAfetadas = pstmt.executeUpdate();

            // Se algum registro foi inserido, confirma a ação e retorna true
            if (linhasAfetadas > 0) {
                GerenteConexao.commit();
                return true;
            }

            // Se nenhum registro foi inserido, desfaz a ação e retorna false
            GerenteConexao.rollback();
            return false;

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
                // Cria um administrador temporário para receber os dados da linha atual
                Administrador administradorTemporario = new Administrador();

                // Preenche o objeto com os valores retornados pelo banco de dados
                administradorTemporario.setId(rs.getInt("id_administrador"));
                administradorTemporario.setNome(rs.getString("nome"));
                administradorTemporario.setEmail(rs.getString("email"));
                administradorTemporario.setSenha(rs.getString("senha"));
                administradorTemporario.setTelefone(rs.getString("telefone"));
                administradorTemporario.setFotoPerfil(rs.getString("url_foto"));

                // Adiciona o administrador preenchido à lista
                administradores.add(administradorTemporario);
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
        // Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        // Comando para buscar um administrador pelo id
        String sqlSelect = "SELECT id_administrador, nome, email, senha, telefone, url_foto " +
                "FROM tb_administrador WHERE id_administrador=?";

        // Cria o executor que recebe o comando e executa a consulta
        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {

            // Seta o id do administrador no '?' do comando SQL
            pstmt.setInt(1, idAdministrador);

            // Executa a consulta e armazena o resultado
            try (ResultSet rs = pstmt.executeQuery()) {

                // Se encontrou um registro, cria e preenche o administrador
                if (rs.next()) {
                    Administrador administrador = new Administrador();
                    administrador.setId(rs.getInt("id_administrador"));
                    administrador.setNome(rs.getString("nome"));
                    administrador.setEmail(rs.getString("email"));
                    administrador.setSenha(rs.getString("senha"));
                    administrador.setTelefone(rs.getString("telefone"));
                    administrador.setFotoPerfil(rs.getString("url_foto"));

                    // Confirma a consulta no banco de dados
                    GerenteConexao.commit();

                    // Retorna o administrador encontrado
                    return administrador;
                }

                // Confirma a consulta mesmo quando não encontra um registro
                GerenteConexao.commit();

                // Retorna null quando o administrador não existe
                return null;
            }

        // Em casos de erros no banco de dados, desfaz a ação
        } catch (SQLException sqle) {

            // Lista todos os erros
            sqle.printStackTrace();

            // Desfaz a ação
            GerenteConexao.rollback();

            // Retorna null
            return null;
        }
    }

    // Método para atualizar os dados do administrador pelo id
    public boolean atualizar(Administrador administrador) {
        // Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        // Comando de atualizar em SQL
        String sqlUpdate = "UPDATE tb_administrador " +
                "SET nome=?, email=?, senha=?, telefone=?, url_foto=? WHERE id_administrador=?";

        // Controle da quantidade de linhas alteradas no banco de dados
        int linhasAfetadas = 0;

        // Inicia o executor que adiciona os valores e executa o comando SQL
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {

            // Seta os valores nos '?' do comando SQL
            pstmt.setString(1, administrador.getNome());
            pstmt.setString(2, administrador.getEmail());
            pstmt.setString(3, administrador.getSenha());
            pstmt.setString(4, administrador.getTelefone());
            pstmt.setString(5, administrador.getFotoPerfil());
            pstmt.setInt(6, administrador.getId());

            // Executa o comando e armazena a quantidade de linhas atualizadas
            linhasAfetadas = pstmt.executeUpdate();

            // Se algum registro foi atualizado, confirma a ação e retorna true
            if (linhasAfetadas > 0) {
                GerenteConexao.commit();
                return true;
            }

            // Se nenhum registro foi atualizado, desfaz a ação e retorna false
            GerenteConexao.rollback();
            return false;

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

    // Método para deletar um administrador pelo id
    public boolean deletar(int idAdministrador) {
        // Comando de deletar em SQL
        String sqlDelete = "DELETE FROM tb_administrador WHERE id_administrador=?";

        // Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        // Controle da quantidade de linhas alteradas no banco de dados
        int linhasAfetadas = 0;

        // Inicia o executor que atribui o valor e executa o comando SQL
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
            // Seta o valor no '?' do comando SQL
            pstmt.setInt(1, idAdministrador);

            // Executa o comando e armazena a quantidade de linhas excluídas
            linhasAfetadas = pstmt.executeUpdate();

            // Se algum registro foi excluído, confirma a ação e retorna true
            if (linhasAfetadas > 0) {
                GerenteConexao.commit();
                return true;
            }

            // Se nenhum registro foi excluído, desfaz a ação e retorna false
            GerenteConexao.rollback();
            return false;

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

}
