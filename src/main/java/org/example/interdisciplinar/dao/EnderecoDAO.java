package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.Alimento;
import org.example.interdisciplinar.model.Categoria;
import org.example.interdisciplinar.model.Endereco;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Abertura da classe
public class EnderecoDAO {

    // Método de inserir um enderço no bd
    public boolean inserir(Endereco endereco){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de inserir em SQL
        String sqlInsert = "INSERT INTO tb_endereco (rua, numero, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?)";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai adicionar valores no comando SQL e executará o comando
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, endereco.getRua());
            pstmt.setString(2, endereco.getNumero());
            pstmt.setString(3, endereco.getBairro());
            pstmt.setString(4, endereco.getCidade());
            pstmt.setString(5, endereco.getEstado());
            pstmt.setString(6, endereco.getCep());

            linhasAfetadas = pstmt.executeUpdate();

            //Se ação tiver dados certo commita e retorna true
            if (linhasAfetadas > 0) {
                GerenteConexao.commit();
                return true;
            }

            //Se não desfaz a ação e retorna false
            GerenteConexao.rollback();
            return false;


        //Em casos de erros no banco de dados desfaz a ação
        } catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna false
            return false;
        }
    }

    // Método de deletar um endereço do bd
    public boolean deletar(int idEndereco){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de deletar em SQL
        String sqlDelete = "DELETE FROM tb_endereco WHERE id_endereco=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)){

            //Seta valor no '?' do comando SQL
            pstmt.setInt(1, idEndereco);

            linhasAfetadas = pstmt.executeUpdate();

            //Se ação tiver dados certo commita e retorna true
            if (linhasAfetadas > 0) {
                GerenteConexao.commit();
                return true;
            }

            //Se não desfaz a ação e retorna false
            GerenteConexao.rollback();
            return false;

        //Em casos de erros no banco de dados desfaz a ação
        } catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna false
            return false;
        }
    }

    // Método de listar todos os endereços do bd
    public List<Endereco> listar(){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de listar em SQL
        String sqlSelect = "SELECT * FROM tb_endereco";
        List<Endereco> enderecos = new ArrayList<>();

        //Cria executor que receberá comando e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
            ResultSet rs  = pstmt.executeQuery();

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
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
            // Commita a ação no banco de dados
            GerenteConexao.commit();

            // Retrona a lista de administradores
            return enderecos;

        //Em casos de erros no banco de dados desfaz a ação
        } catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            // Retorna a lista
            return enderecos;
        }
    }

    public boolean atualizar(Endereco endereco){
        String sql = "UPDATE tb_endereco SET rua=?, numero=?, bairro=?, cidade=?, estado=?, cep=? WHERE id_endereco=?";
        Connection conn = GerenteConexao.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, endereco.getRua());
            pstmt.setString(2, endereco.getNumero());
            pstmt.setString(3, endereco.getBairro());
            pstmt.setString(4, endereco.getCidade());
            pstmt.setString(5, endereco.getEstado());
            pstmt.setString(6, endereco.getCep());
            pstmt.setInt(7, endereco.getId());
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

    public List<Endereco> listarFiltrado(
            Integer idEnderecoInicio,
            Integer idEnderecoFim,
            String cidade,
            String estado,
            String ordenar
    ) {
        StringBuilder sql = new StringBuilder("SELECT * FROM tb_endereco WHERE 1=1");
        List<Object> parametrosDoFiltro = new ArrayList<>();
        List<Endereco> enderecos = new ArrayList<>();

        if (idEnderecoInicio != null) {
            sql.append(" AND id_endereco >=?");
            parametrosDoFiltro.add(idEnderecoInicio);
        }
        if (idEnderecoFim != null) {
            sql.append(" AND id_endereco <=?");
            parametrosDoFiltro.add(idEnderecoFim);
        }
        if (cidade != null && !cidade.isBlank()) {
            sql.append(" AND cidade ILIKE ?");
            parametrosDoFiltro.add("%" + cidade + "%");
        }

        if (estado != null && !estado.isBlank()) {
            sql.append(" AND estado ILIKE ?");
            parametrosDoFiltro.add("%" + cidade + "%");
        }

        switch (ordenar) {
            case "id":
                sql.append(" ORDER BY id_endereco");
                break;

            case "cidade":
                sql.append(" ORDER BY cidade");
                break;

            case "estado":
                sql.append(" ORDER BY estado");
                break;

            default:
                sql.append(" ORDER BY id_endereco");
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
                // Commita a ação no banco de dados
                GerenteConexao.commit();

                // Retrona a lista de administradores
                return enderecos;
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return enderecos;
        }
    }

    public Endereco buscarporId(int id){
        String sql = "SELECT * FROM tb_endereco WHERE id=?";
        try(
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, id);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    Endereco endereco = new Endereco(
                            rs.getString("rua"),
                            rs.getString("numero"),
                            rs.getString("bairro"),
                            rs.getString("cidade"),
                            rs.getString("estado"),
                            rs.getString("cep")
                    );
                    return endereco;
                }
            }

        } catch (SQLException sqle){
            System.out.println("Erro de SQL: " + sqle.getMessage());
        }
        return null;
    }

}
