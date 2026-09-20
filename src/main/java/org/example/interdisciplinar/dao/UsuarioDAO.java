package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final static Connection conn = GerenteConexao.conectar();

    public boolean insert(Usuario usuario){
        String sqlInsert = "INSERT INTO tb_usuario (nome, email, preferencias_consumo, configuracao_alerta) VALUES (?, ?, ?, ?)";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)){

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getPreferenciasCosnumo());
            pstmt.setString(4, usuario.getConfiguracaoAlerta());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean delete(Usuario usuario){
        String sqlDelete = "DELETE FROM tb_usuario WHERE id=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)){
            pstmt.setInt(1, usuario.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;

        }
    }

    public List<Usuario> select(){
        String sqlSelect = "SELECT * FROM tb_usuario";
        List<Usuario> usuarios = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()){
                Usuario usuarioTemporario = new Usuario();
                usuarioTemporario.setId(rs.getInt(1));
                usuarioTemporario.setNome(rs.getString(2));
                usuarioTemporario.setEmail(rs.getString(3));
                usuarioTemporario.setPreferenciasCosnumo(rs.getString(4));
                usuarioTemporario.setConfiguracaoAlerta(rs.getString(5));
                usuarios.add(usuarioTemporario);
            }
            return usuarios;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return usuarios;
        }
    }
    public boolean updateEmail(Usuario usuario){
        String sqlUpdateEmail = "UPDATE tb_usuario SET email=? WHERE id=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateEmail)){
            pstmt.setString(1, usuario.getEmail());
            pstmt.setInt(2, usuario.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean updatePreferencias(Usuario usuario){
        String sqlUpdatePreferencias = "UPDATE tb_usuario SET preferencias_consumo=? WHERE id=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdatePreferencias)){
            pstmt.setString(1, usuario.getPreferenciasCosnumo());
            pstmt.setInt(2, usuario.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean updateConfiguracao(Usuario usuario){
        String sqlUpdateConfiguracao = "UPDATE tb_usuario SET configuracao_alerta=? WHERE id=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateConfiguracao)){
            pstmt.setString(1, usuario.getConfiguracaoAlerta());
            pstmt.setInt(2, usuario.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }
}