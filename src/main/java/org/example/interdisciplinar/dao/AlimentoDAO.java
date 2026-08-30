package org.example.interdisciplinar.dao;


import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.Alimento;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class AlimentoDAO {
    private final Connection conn = GerenteConexao.conectar();

    public boolean inserir(Alimento alimento){

        int linhasAfeteadas = 0;
        String sqlInsert = "INSERT INTO tb_alimento (codigo_barras, nome, marca, id_categoria) VALUES (?, ?, ?, ?)";

        try(PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {


            pstmt.setString(1, alimento.getCodigoBarras());
            pstmt.setString(2, alimento.getNome());
            pstmt.setString(3, alimento.getMarca());
            pstmt.setInt(4, alimento.getIdCategoria());

            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean delete(Alimento alimento){
        String sqlDelete = "DELETE FROM tb_alimento WHERE id=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)){
            pstmt.setInt(1, alimento.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }
    public List<Alimento> select (Alimento alimento){
        String sqlSelect = "SELECT * FROM tb_alimento";
        List<Alimento> alimentos = new ArrayList<>();

        try(Statement stmt = conn.prepareStatement(sqlSelect)) {
            ResultSet rs  = stmt.executeQuery(sqlSelect);

            while(rs.next()){
                Alimento alimentoTemporario = new Alimento();
                alimentoTemporario.setId(rs.getInt(1));
                alimentoTemporario.setCodigoBarras(rs.getString(2));
                alimentoTemporario.setNome(rs.getString(3));
                alimentoTemporario.setMarca(rs.getString(4));
                alimentoTemporario.setIdCategoria(rs.getInt(5));

                alimentos.add(alimentoTemporario);
            }
            return alimentos;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return alimentos;
        }
    }

    public boolean updateNome(Alimento alimento){
        String sqlUpdateName = "UPDATE tb_alimento SET nome=? WHERE id=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateName)){
            pstmt.setString(1, alimento.getNome());
            pstmt.setInt(2, alimento.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean updateCodigo(Alimento alimento){
        String sqlUpdateCod = "UPDATE tb_alimento SET codigo_barras=? WHERE id=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateCod)){
            pstmt.setString(1, alimento.getCodigoBarras());
            pstmt.setInt(2, alimento.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }
    public boolean updateMarca(Alimento alimento){
        String sqlUpdateMarca = "UPDATE tb_alimento SET marca=? WHERE id=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateMarca)){
            pstmt.setString(1, alimento.getMarca());
            pstmt.setInt(2, alimento.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }
}
