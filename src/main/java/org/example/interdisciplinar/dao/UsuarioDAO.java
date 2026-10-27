package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dto.UsuarioView;
import org.example.interdisciplinar.model.Alimento;
import org.example.interdisciplinar.model.ConfiguracaoAlerta;
import org.example.interdisciplinar.model.Endereco;
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
        String sqlInsert = "INSERT INTO tb_usuario (nome, email, preferencias_consumo, configuracao_alerta, senha, telefone) VALUES (?, ?, ?, ?, ?, ?)";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: adicionará valores no comando SQL e executará o comando
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getPreferenciasConsumo());
            pstmt.setString(4, usuario.getConfiguracaoAlerta().name());
            pstmt.setString(5, usuario.getTelefone());
            pstmt.setString(6, usuario.getSenha());

            //Se ação tiver dados certo commita e retorna true
            linhasAfetadas = pstmt.executeUpdate();

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
    public List<UsuarioView> listar(){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de listar em SQL
        String sqlSelect = "SELECT * FROM tb_usuario_view";

        // Criando uma lista para guardar os registros dos usuários no bd
        List<UsuarioView> view = new ArrayList<>();

        //Cria executor que receberá comando e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
             ResultSet rs = pstmt.executeQuery()) {

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while (rs.next()){
                UsuarioView usuarioTemporario = new UsuarioView(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        ConfiguracaoAlerta.valueOf(rs.getString(6))
                );
                view.add(usuarioTemporario);
            }
            // Commita a ação no banco de dados
            GerenteConexao.commit();

            // Retrona a lista de administradores
            return view;

        // Em casos de erro no banco de dados desfaz a ação
        } catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna a lista
            return view;
        }
    }

    public boolean atualizar(Usuario usuario){
        String sql = "UPDATE tb_usuario SET nome=?, email=?, preferencias_consumo=?, configuracao_alerta=?, telefone=?, senha=? WHERE id_usuario=?";
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getPreferenciasConsumo());
            pstmt.setString(4, usuario.getConfiguracaoAlerta().name());
            pstmt.setString(5, usuario.getTelefone());
            pstmt.setString(6, usuario.getEmail());
            pstmt.setInt(7, usuario.getId());
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public List<UsuarioView> listarFiltrado(
          Integer idUsuarioInicio,
          Integer idUsuarioFim,
          String nome,
          String email,
          String ordenar
    ){
        StringBuilder sql = new StringBuilder("SELECT * FROM tb_usuario_view WHERE 1=1");
        List<Object> parametrosDoFiltro = new ArrayList<>();
        List<UsuarioView> view = new ArrayList<>();

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
                    UsuarioView usuarioTemporario = new UsuarioView(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            ConfiguracaoAlerta.valueOf(rs.getString(6))
                    );
                    view.add(usuarioTemporario);
                }

                // Retrona a lista de administradores
                return view;
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return view;
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
                            ConfiguracaoAlerta.valueOf(rs.getString("configuracao_alerta")),
                            rs.getString("telefone"),
                            rs.getString("senha")
                    );
                    return usuario;
                }
            }

        } catch (SQLException sqle){
            System.out.println("Erro de SQL: " + sqle.getMessage());
        }
        return null;
    }

}