package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Abertura da classe
public class UsuarioDAO {

    // Método para inserir um usuário no banco de dados
    public boolean inserir(Usuario usuario){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de inserir em SQL
        String sqlInsert = "INSERT INTO tb_usuario (nome, email, preferencias_consumo, configuracao_alerta) VALUES (?, ?, ?, ?)";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: adicionará valores no comando SQL e executará o comando
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getPreferenciasCosnumo());
            pstmt.setString(4, usuario.getConfiguracaoAlerta());

            //Se ação tiver dados certo commita e retorna true
            if (linhasAfetadas > 0) {
                GerenteConexao.commit();
                return true;
            }

            //Se não desfaz a ação e retorna false
            GerenteConexao.rollback();
            return false;


        //Em casos de erro no banco de dados desfaz a ação
        } catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna false
            return false;
        }
    }

    // Método para deletar um usuário do BD
    public boolean deletar(int idUsuario){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de deletar em SQL
        String sqlDelete = "DELETE FROM tb_usuario WHERE id=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)){

            //Seta valor no '?' do comando SQL
            pstmt.setInt(1, idUsuario);

            //Se ação tiver dados certo commita e retorna true
            if (linhasAfetadas > 0) {
                GerenteConexao.commit();
                return true;
            }

            //Se não desfaz a ação e retorna false
            GerenteConexao.rollback();
            return false;

        } catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna false
            return false;
        }
    }

    // Método para listar todos os usuários cadastrados no bd
    public List<Usuario> listar(){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de listar em SQL
        String sqlSelect = "SELECT * FROM tb_usuario";

        // Criando uma lista para guardar os registros dos usuários no bd
        List<Usuario> usuarios = new ArrayList<>();

        //Cria executor que receberá comando e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
             ResultSet rs = pstmt.executeQuery()) {

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while (rs.next()){
                Usuario usuarioTemporario = new Usuario();
                usuarioTemporario.setId(rs.getInt(1));
                usuarioTemporario.setNome(rs.getString(2));
                usuarioTemporario.setEmail(rs.getString(3));
                usuarioTemporario.setPreferenciasCosnumo(rs.getString(4));
                usuarioTemporario.setConfiguracaoAlerta(rs.getString(5));
                usuarios.add(usuarioTemporario);
            }
            // Commita a ação no banco de dados
            GerenteConexao.commit();

            // Retrona a lista de administradores
            return usuarios;

        // Em casos de erro no banco de dados desfaz a ação
        } catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna a lista
            return usuarios;
        }
    }

    // Método para atualizar o email do usuário
    public boolean atualizarEmail(String emailNovo, int id){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de atualizar em SQL
        String sqlUpdateEmail = "UPDATE tb_usuario SET email=? WHERE id=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateEmail)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, emailNovo);
            pstmt.setInt(2, id);

            //Se ação tiver dados certo commita e retorna true
            if (linhasAfetadas > 0) {
                GerenteConexao.commit();
                return true;
            }

            //Se não desfaz a ação e retorna false
            GerenteConexao.rollback();
            return false;

        } catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna false
            return false;
        }
    }

    // Método para atualizar as preferências do usuário
    public boolean atualizarPreferencias(String prefNova, int id){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de atualizar em SQL
        String sqlUpdatePreferencias = "UPDATE tb_usuario SET preferencias_consumo=? WHERE id=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdatePreferencias)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, prefNova);
            pstmt.setInt(2, id);

            //Se ação tiver dados certo commita e retorna true
            if (linhasAfetadas > 0) {
                GerenteConexao.commit();
                return true;
            }

            //Se não desfaz a ação e retorna false
            GerenteConexao.rollback();
            return false;

        } catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna false
            return false;
        }
    }

    // Método para atualizar as configurações de alerta
    public boolean atualizarConfiguracao(String configNova, int id){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de inserir em SQL
        String sqlUpdateConfiguracao = "UPDATE tb_usuario SET configuracao_alerta=? WHERE id=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateConfiguracao)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, configNova);
            pstmt.setInt(2, id);

            //Se ação tiver dados certo commita e retorna true
            if (linhasAfetadas > 0) {
                GerenteConexao.commit();
                return true;
            }

            //Se não desfaz a ação e retorna false
            GerenteConexao.rollback();
            return false;

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