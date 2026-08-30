package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private final static Connection conn = GerenteConexao.conectar();

    public boolean insert(Categoria categoria){
        String sqlInsert = "INSERT INTO tb_categoria(nome, descricao) VALUES (?, ?)";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)){

            pstmt.setString(1, categoria.getNome());
            pstmt.setString(2, categoria.getDescricao());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean delete(Categoria categoria){
        String sqlDelete = "DELETE FROM tb_categoria WHERE id=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)){
            pstmt.setInt(1, categoria.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public List<Categoria> select (Categoria categoria){
        String sqlSelect = "SELECT * FROM tb_categoria";
        List<Categoria> categorias = new ArrayList<>();

        try (Statement stmt = conn.prepareStatement(sqlSelect)){
            ResultSet rs = stmt.executeQuery(sqlSelect);

            while(rs.next()){
                Categoria categoriaTemporaria = new Categoria();
                categoriaTemporaria.setId(rs.getInt(1));
                categoriaTemporaria.setNome(rs.getString(2));
                categoriaTemporaria.setDescricao(rs.getString(3));

                categorias.add(categoriaTemporaria);
            }
            return categorias;
        } catch(SQLException sqle){
            sqle.printStackTrace();
            return categorias;
        }
    }

    public boolean updateName(Categoria categoria){
        String sqlUpdateName = "UPDATE tb_categoria SET nome=? WHERE id=?";
        int linhasAfetadas = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateName)){
            pstmt.setString(1, categoria.getNome());
            pstmt.setInt(2, categoria.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean updateDescription(Categoria categoria){
        String sqlUpdateDescription = "UPDATE tb_categoria SET descricao=? WHERE id=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateDescription)){
            pstmt.setString(1, categoria.getDescricao());
            pstmt.setInt(2, categoria.getId());
            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }
}
