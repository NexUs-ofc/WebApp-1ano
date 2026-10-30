package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.Alimento;
import org.example.interdisciplinar.model.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Abertura da classe
public class CategoriaDAO {

    // Método para inserir categoria no banco de dados
    public boolean inserir(Categoria categoria){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de inserir em SQL
        String sqlInsert = "INSERT INTO tb_categoria(nome, descricao) VALUES (?, ?)";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai adicionar valores no comando SQL e executará o comando
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, categoria.getNome());
            pstmt.setString(2, categoria.getDescricao());

            //Se ação tiver dados certo commita e retorna true
            linhasAfetadas = pstmt.executeUpdate();
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

    // Método para deletar uma categoria do BD
    public boolean deletar(int id){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de deletar em SQL
        String sqlDelete = "DELETE FROM tb_categoria WHERE id_categoria=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valor ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)){

            //Seta valor no '?' do comando SQL
            pstmt.setInt(1, id);

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

    // Método para listar todas as categorias do BD
    public List<Categoria> listar(){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de listar em SQL
        String sqlSelect = "SELECT * FROM tb_categoria";

        //Criando uma lista que vai armazenar os registros das categorias
        List<Categoria> categorias = new ArrayList<>();

        //Cria executor que receberá comando e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
            ResultSet rs = pstmt.executeQuery();

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while (rs.next()) {
                Categoria categoriaTemporaria = new Categoria();
                categoriaTemporaria.setId(rs.getInt(1));
                categoriaTemporaria.setNome(rs.getString(2));
                categoriaTemporaria.setDescricao(rs.getString(3));

                categorias.add(categoriaTemporaria);
            }

            // Commita a ação no banco de dados
            GerenteConexao.commit();

            // Retrona a lista de administradores
            return categorias;

        //Em casos de erros no banco de dados desfaz a ação
        } catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna a lista
            return categorias;
        }
    }

    public boolean atualizar(Categoria categoria){
        String sql = "UPDATE tb_categoria SET nome=?, descricao=? WHERE id_categoria=?";
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setString(1, categoria.getNome());
            pstmt.setString(2, categoria.getDescricao());
            pstmt.setInt(3, categoria.getId());
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public List<Categoria> listarFiltrado(
            Integer idCategoriaInicio,
            Integer idCategoriaFim,
            String nome,
            String ordenar
    ) {
        StringBuilder sql = new StringBuilder("SELECT * FROM tb_categoria WHERE 1=1");
        List<Object> parametrosDoFiltro = new ArrayList<>();
        List<Categoria> categorias = new ArrayList<>();

        if (idCategoriaInicio != null) {
            sql.append(" AND id_categoria >=?");
            parametrosDoFiltro.add(idCategoriaInicio);
        }
        if (idCategoriaFim != null) {
            sql.append(" AND id_categoria <=?");
            parametrosDoFiltro.add(idCategoriaFim);
        }
        if (nome != null && !nome.isBlank()) {
            sql.append(" AND nome ILIKE ?");
            parametrosDoFiltro.add("%" + nome + "%");
        }

        switch (ordenar) {
            case "id":
                sql.append(" ORDER BY id_categoria");
                break;

            case "nome":
                sql.append(" ORDER BY nome");
                break;

            default:
                sql.append(" ORDER BY id_categoria");
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
                    Categoria categoriaTemporaria = new Categoria();
                    categoriaTemporaria.setId(rs.getInt(1));
                    categoriaTemporaria.setNome(rs.getString(2));
                    categoriaTemporaria.setDescricao(rs.getString(3));

                    categorias.add(categoriaTemporaria);
                }

                // Retrona a lista de administradores
                return categorias;
            }

        } catch (SQLException sqle) {
            throw new RuntimeException("Erro ao consultar categoria", sqle);
        }
    }

    public Categoria buscarporId(int id){
        String sql = "SELECT * FROM tb_categoria WHERE id=?";
        try(
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, id);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    Categoria categoria = new Categoria(
                            rs.getString("nome"),
                            rs.getString("descricao")
                    );
                    return categoria;
                }
            }

        } catch (SQLException sqle){
            System.out.println("Erro de SQL: " + sqle.getMessage());
        }
        return null;
    }
}