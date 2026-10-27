package org.example.interdisciplinar.dao;


import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dto.AlimentoView;
import org.example.interdisciplinar.model.Administrador;
import org.example.interdisciplinar.model.Alimento;
import org.example.interdisciplinar.model.ItemDespensa;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

// Abertura de classe
public class AlimentoDAO {

    // Método de inserir um alimento no banco de dados
    public boolean inserir(Alimento alimento) {

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Comando de inserir em SQL
        String sqlInsert = "INSERT INTO tb_alimento (codigo_barras, nome, marca, id_categoria) VALUES (?, ?, ?, ?)";

        //Iniciando executor que: adicionará valores no comando SQL e executará o comando
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, alimento.getCodigoBarras());
            pstmt.setString(2, alimento.getNome());
            pstmt.setString(3, alimento.getMarca());
            pstmt.setInt(4, alimento.getIdCategoria());

            // Executa o comando e recebe a quantidade de linhas afetadas
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

    // Método de deletar um alimento do banco de dados
    public boolean deletar(Alimento alimento) {

        // Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de deletar em SQL
        String sqlDelete = "DELETE FROM tb_alimento WHERE id_alimento=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai adicionar valores no comando SQL e executará o comando
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {

            //Seta valor no '?' do comando SQL
            pstmt.setInt(1, alimento.getId());

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

    // Método de listar todos os alimentos
    public List<AlimentoView> listar() {

        Connection conn = GerenteConexao.conectar();
        String sqlSelect = "SELECT * FROM tb_alimento_view";
        List<AlimentoView> alimentos = new ArrayList<>();

        //Iniciando executor que: adicionará valores no comando SQL e executará o comando
        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
            ResultSet rs = pstmt.executeQuery();

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while (rs.next()) {
                AlimentoView alimentoTemporario = new AlimentoView(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                );
                alimentos.add(alimentoTemporario);
            }
            // Commita a ação no banco de dados
            GerenteConexao.commit();

            // Retrona a lista de administradores
            return alimentos;

            //Em casos de erros no banco de dados desfaz a ação
        } catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            // Retorna a lista
            return alimentos;
        }
    }

    public boolean atualizar(Alimento alimento) {
        String sql = "UPDATE tb_alimento SET codigo_barras=?, nome=?, marca=?, id_categoria=? WHERE id_alimento=?";
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, alimento.getCodigoBarras());
            pstmt.setString(2, alimento.getNome());
            pstmt.setString(3, alimento.getMarca());
            pstmt.setInt(4, alimento.getIdCategoria());
            pstmt.setInt(5, alimento.getId());
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }

    public List<Alimento> listarFiltrado(
            Integer idAlimentoInicio,
            Integer idAlimentoFim,
            String nome,
            String categoria,
            String marca,
            String ordenar
    ) {
        StringBuilder sql = new StringBuilder("SELECT * FROM tb_alimento WHERE 1=1");
        List<Object> parametrosDoFiltro = new ArrayList<>();
        List<Alimento> alimentos = new ArrayList<>();

        if (idAlimentoInicio != null) {
            sql.append(" AND id_alimento >=?");
            parametrosDoFiltro.add(idAlimentoInicio);
        }
        if (idAlimentoFim != null) {
            sql.append(" AND id_alimento <=?");
            parametrosDoFiltro.add(idAlimentoFim);
        }
        if (nome != null && !nome.isBlank()) {
            sql.append(" AND nome ILIKE ?");
            parametrosDoFiltro.add("%" + nome + "%");
        }
        if (categoria != null && !categoria.isBlank()) {
            sql.append(" AND id_categoria=?");
            parametrosDoFiltro.add("%" + categoria + "%");
        }
        if (marca != null && !marca.isBlank()) {
            sql.append(" AND marca ILIKE ?");
            parametrosDoFiltro.add("%" + marca + "%");
        }

        switch (ordenar) {
            case "id":
                sql.append(" ORDER BY id_alimento");
                break;

            case "nome":
                sql.append(" ORDER BY nome");
                break;

            case "categoria":
                sql.append(" ORDER BY categoria");
                break;

            case "marca":
                sql.append(" ORDER BY marca");
                break;

            default:
                sql.append(" ORDER BY id_alimento");
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

                while (rs.next()) {
                    Alimento alimentoTemporario = new Alimento();
                    alimentoTemporario.setId(rs.getInt(1));
                    alimentoTemporario.setCodigoBarras(rs.getString(2));
                    alimentoTemporario.setNome(rs.getString(3));
                    alimentoTemporario.setMarca(rs.getString(4));
                    alimentoTemporario.setIdCategoria(rs.getInt(5));
                    alimentos.add(alimentoTemporario);
                }

                // Retrona a lista de administradores
                return alimentos;
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return alimentos;
        }
    }

    public Alimento buscarporId(int id){
        String sql = "SELECT * FROM tb_alimento WHERE id=?";
        try(
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, id);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    Alimento alimento = new Alimento(
                            rs.getString("codigo_barras"),
                            rs.getString("nome"),
                            rs.getString("marca"),
                            rs.getInt("id_categoria")
                    );
                    return alimento;
                }
            }

        } catch (SQLException sqle){
            System.out.println("Erro de SQL: " + sqle.getMessage());
        }
        return null;
    }
}