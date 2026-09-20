package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.model.Casa;
import org.example.interdisciplinar.conexao.GerenteConexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CasaDAO {
    private final Connection conn = GerenteConexao.conectar();
    public boolean inserir(Casa casa){
        String sqlInsert = "INSERT INTO tb_casa(nome, id_usuario, id_endereco) VALUES (?, ?, ?)";
        int linhasAfetadas = 0;

        try(PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            pstmt.setString(1, casa.getNome());
            pstmt.setInt(2, casa.getIdUsuario());
            pstmt.setInt(3, casa.getIdEndereco());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }

    }

    public boolean delete(Casa casa){
        String sqlDelete = "DELETE FROM tb_casa WHERE id_casa=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)){
            pstmt.setInt(1, casa.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public List<Casa> select(){
        String sqlSelect = "SELECT * FROM tb_casa";
        List <Casa> casas = new ArrayList<>();

        try (Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(sqlSelect);

            while(rs.next()){
                Casa casaTemporaria = new Casa();
                casaTemporaria.setId(rs.getInt(1));
                casaTemporaria.setNome(rs.getString(2));
                casaTemporaria.setIdUsuario(rs.getInt(3));
                casaTemporaria.setIdEndereco(rs.getInt(4));
                casas.add(casaTemporaria);
            }

            return casas;

        } catch(SQLException sqle){
            sqle.printStackTrace();
            return casas;
        }
    }

    public boolean updateNome(Casa casa){
        String sqlUpdateName = "UPDATE tb_casa SET nome=? WHERE id_casa=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateName)){
            pstmt.setString(1, casa.getNome());
            pstmt.setInt(2, casa.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }
}