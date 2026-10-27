package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.ConfiguracaoAlerta;
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
            pstmt.setString(3, usuario.getPreferenciasConsumo());
            pstmt.setString(4, usuario.getConfiguracaoAlerta().name());

            linhasAfetadas = pstmt.executeUpdate();

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
        String sqlDelete = "DELETE FROM tb_usuario WHERE id_usuario=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)){

            //Seta valor no '?' do comando SQL
            pstmt.setInt(1, idUsuario);

            linhasAfetadas = pstmt.executeUpdate();

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
                usuarioTemporario.setPreferenciasConsumo(rs.getString(4));
                usuarioTemporario.setConfiguracaoAlerta(converterConfiguracaoAlerta(rs.getString(5)));
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

    public boolean atualizar(Usuario usuario){
        String sql = "UPDATE tb_usuario SET nome=?, email=?, preferencias_consumo=?, configuracao_alerta=? WHERE id_usuario=?";
        Connection conn = GerenteConexao.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getPreferenciasConsumo());
            pstmt.setString(4, usuario.getConfiguracaoAlerta().name());
            pstmt.setInt(5, usuario.getId());
            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                GerenteConexao.commit();
                return true;
            }

            GerenteConexao.rollback();
            return false;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            GerenteConexao.rollback();
            return false;
        }
    }

    public List<Usuario> listarFiltrado(
          Integer idUsuarioInicio,
          Integer idUsuarioFim,
          String nome,
          String email,
          String ordenar
    ){
        StringBuilder sql = new StringBuilder("SELECT * FROM tb_usuario WHERE 1=1");
        List<Object> parametrosDoFiltro = new ArrayList<>();
        List<Usuario> usuarios = new ArrayList<>();

        if (idUsuarioInicio != null) {
            sql.append(" AND id_usuario >=?");
            parametrosDoFiltro.add(idUsuarioInicio);
        }
        if (idUsuarioFim != null) {
            sql.append(" AND id_usuario <=?");
            parametrosDoFiltro.add(idUsuarioFim);
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
                sql.append(" ORDER BY id_usuario");
                break;

            case "nome":
                sql.append(" ORDER BY nome");
                break;

            case "email":
                sql.append(" ORDER BY email");
                break;

            default:
                sql.append(" ORDER BY id_usuario");
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
                while (rs.next()){
                    Usuario usuarioTemporario = new Usuario();
                    usuarioTemporario.setId(rs.getInt(1));
                    usuarioTemporario.setNome(rs.getString(2));
                    usuarioTemporario.setEmail(rs.getString(3));
                    usuarioTemporario.setPreferenciasConsumo(rs.getString(4));
                    usuarioTemporario.setConfiguracaoAlerta(converterConfiguracaoAlerta(rs.getString(5)));
                    usuarios.add(usuarioTemporario);
                }

                // Retrona a lista de administradores
                return usuarios;
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return usuarios;
        }
    }

    public Usuario buscarporId(int id){
        String sql = "SELECT * FROM tb_usuario WHERE id=?";
        try(
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, id);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    Usuario usuario = new Usuario(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("preferencias_consumo"),
                            converterConfiguracaoAlerta(rs.getString("configuracao_alerta"))
                    );
                    return usuario;
                }
            }

        } catch (SQLException sqle){
            System.out.println("Erro de SQL: " + sqle.getMessage());
        }
        return null;
    }

    // Converte o texto salvo no banco para o enum usado pelo model
    private ConfiguracaoAlerta converterConfiguracaoAlerta(String configuracaoAlerta) {
        return ConfiguracaoAlerta.valueOf(configuracaoAlerta.toUpperCase());
    }

}
