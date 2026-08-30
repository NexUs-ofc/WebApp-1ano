package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.Endereco;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {

    private final static Connection conn = GerenteConexao.conectar();

    public boolean insert(Endereco endereco){
        String sqlInsert = "INSERT INTO tb_endereco (rua, numero, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?)";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)){

            pstmt.setString(1, endereco.getRua());
            pstmt.setString(2, endereco.getNumero());
            pstmt.setString(3, endereco.getBairro());
            pstmt.setString(4, endereco.getCidade());
            pstmt.setString(5, endereco.getEstado());
            pstmt.setString(6, endereco.getCep());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean delete(Endereco endereco){
        String sqlDelete = "DELETE FROM tb_endereco WHERE id=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)){
            pstmt.setInt(1, endereco.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public List<Endereco> select (Endereco endereco){
        String sqlSelect = "SELECT * FROM tb_endereco";
        List<Endereco> enderecos = new ArrayList<>();

        try (Statement stmt = conn.prepareStatement(sqlSelect)){
            ResultSet rs  = stmt.executeQuery(sqlSelect);

            while (rs.next()){
                Endereco enderecoTemporario = new Endereco();
                enderecoTemporario.setId(rs.getInt(1));
                enderecoTemporario.setRua(rs.getString(2));
                enderecoTemporario.setNumero(rs.getString(3));
                enderecoTemporario.setBairro(rs.getString(4));
                enderecoTemporario.setCidade(rs.getString(5));
                enderecoTemporario.setEstado(rs.getString(6));
                enderecoTemporario.setCep(rs.getString(7));

                enderecos.add(enderecoTemporario);
            }
            return enderecos;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return enderecos;
        }
    }

    public boolean updateRua(Endereco endereco){
        String sqlUpdateRua = "UPDATE tb_endereco SET rua=? WHERE id=?";
        int linhasAfetadas = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateRua)){
            pstmt.setString(1, endereco.getRua());
            pstmt.setInt(2, endereco.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean updateNumero(Endereco endereco){
        String sqlUpdateNumero = "UPDATE tb_endereco SET numero=? WHERE id=?";
        int linhasAfetadas = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateNumero)){
            pstmt.setString(1, endereco.getNumero());
            pstmt.setInt(2, endereco.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean updateBairro(Endereco endereco){
        String sqlUpdatebairro = "UPDATE tb_endereco SET bairro=? WHERE id=?";
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdatebairro)){
            pstmt.setString(1, endereco.getBairro());
            pstmt.setInt(2, endereco.getId());
            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean updateCidade(Endereco endereco){
        String sqlUpdateCidade = "UPDATE tb_endereco SET cidade=? WHERE id=?";
        int linhasAfetadas = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateCidade)){
            pstmt.setString(1, endereco.getCidade());
            pstmt.setInt(2, endereco.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }
    public boolean updateEstado(Endereco endereco){
        String sqlUpdateEstado = "UPDATE tb_endereco SET estado=? WHERE id=?";
        int linhasAfetadas = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateEstado)){
            pstmt.setString(1, endereco.getEstado());
            pstmt.setInt(2, endereco.getId());

            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean updateCep(Endereco endereco){
        String sqlUpdateCep = "UPDATE tb_endereco SET cep=? WHERE id=?";
        int linhasAfetadas = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateCep)){
            pstmt.setString(1, endereco.getCep());
            pstmt.setInt(2, endereco.getId());
            linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

}
