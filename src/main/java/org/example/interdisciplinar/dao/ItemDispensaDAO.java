package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.ItemDispensa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDispensaDAO {

    private final static Connection conn = GerenteConexao.conectar();

    public boolean insert(ItemDispensa itemDispensa){
        String sqlInsert = "INSERT INTO tb_item_dispensa (id_casa, id_alimento, quantidade, data_validade) VALUES (?, ?, ?, ?)";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

            pstmt.setInt(1, itemDispensa.getIdCasa());
            pstmt.setInt(2, itemDispensa.getIdAlimento());
            pstmt.setInt(3, itemDispensa.getQuantidade());
            pstmt.setDate(4, Date.valueOf(itemDispensa.getValidade()));

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean delete(ItemDispensa itemDispensa){
        String sqlDelete = "DELETE FROM tb_item_dispensa WHERE id_item=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)){

            pstmt.setInt(1, itemDispensa.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public List<ItemDispensa> select(){
        String sqlSelect = "SELECT * FROM tb_item_dispensa";
        List<ItemDispensa> itensDispensa = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()){
                ItemDispensa itemdispensa = new ItemDispensa();
                itemdispensa.setId(rs.getInt(1));
                itemdispensa.setIdCasa(rs.getInt(2));
                itemdispensa.setIdAlimento(rs.getInt(3));
                itemdispensa.setQuantidade(rs.getInt(4));

                if (rs.getDate(5) != null) {
                    itemdispensa.setValidade(rs.getDate(5).toLocalDate());
                }

                itensDispensa.add(itemdispensa);
            }
            return itensDispensa;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return itensDispensa;
        }
    }

    public boolean updateQuantidade(ItemDispensa itemDispensa){
        String sqlUpdateQtd = "UPDATE tb_item_dispensa SET quantidade=? WHERE id_item=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateQtd)){

            pstmt.setInt(1, itemDispensa.getQuantidade());
            pstmt.setInt(2, itemDispensa.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean updateValidade(ItemDispensa itemDispensa){
        String sqlUpdateValidade = "UPDATE tb_item_dispensa SET data_validade=? WHERE id_item=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateValidade)){

            pstmt.setDate(1, Date.valueOf(itemDispensa.getValidade()));
            pstmt.setInt(2, itemDispensa.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }
}