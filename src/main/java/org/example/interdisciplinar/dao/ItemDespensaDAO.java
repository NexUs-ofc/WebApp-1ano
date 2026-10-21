package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.ItemDespensa;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Abertura da classe
public class ItemDespensaDAO {

    // Método de inserir um item na dispensa do usuário
    public boolean inserir(ItemDespensa itemDespensa){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de inserir em SQL
        String sqlInsert = "INSERT INTO tb_item_dispensa (id_casa, id_alimento, quantidade, data_validade) VALUES (?, ?, ?, ?)";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai adicionar valores no comando SQL e executará o comando
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

            //Seta valores nos '?' do comando SQL
            pstmt.setInt(1, itemDespensa.getIdCasa());
            pstmt.setInt(2, itemDespensa.getIdAlimento());
            pstmt.setInt(3, itemDespensa.getQuantidade());
            pstmt.setDate(4, Date.valueOf(itemDespensa.getValidade()));

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

    // Método para deletar um item da dispensa do usuário
    public boolean deletar(int idItem){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de deletar em SQL
        String sqlDelete = "DELETE FROM tb_item_dispensa WHERE id_item=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)){

            //Seta valor no '?' do comando SQL
            pstmt.setInt(1, idItem);

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

    // Método para listar todos os itens da dispensa
    public List<ItemDespensa> listar(){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de listar em SQL
        String sqlSelect = "SELECT * FROM view_item_dispensa";

        // Criando lista que irá armazenar os registros dos itens da dispensa
        List<ItemDespensa> itensDespensa = new ArrayList<>();

        //Cria executor que receberá comando e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect)){
            ResultSet rs = pstmt.executeQuery();

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while (rs.next()) {
                ItemDespensa itemDespensa = new ItemDespensa();
                itemDespensa.setId(rs.getInt(1));
                itemDespensa.setIdCasa(rs.getInt(2));
                itemDespensa.setIdAlimento(rs.getInt(3));
                itemDespensa.setQuantidade(rs.getInt(4));

                if (rs.getDate(5) != null) {
                    itemDespensa.setValidade(rs.getDate(5).toLocalDate());
                }
                itensDespensa.add(itemDespensa);
            }

            // Retrona a lista de itens da dispensa
            return itensDespensa;

        } catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();


            //Retorna a lista
            return itensDespensa;
        }

    }

    public boolean atualizar(ItemDespensa itemDespensa){
        String sql = "UPDATE tb_item_dispensa SET id_casa=?, id_alimento=?, quantidade=?, data_validade=? WHERE id_item=?";
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, itemDespensa.getIdCasa());
            pstmt.setInt(2, itemDespensa.getIdAlimento());
            pstmt.setInt(3, itemDespensa.getQuantidade());

            if(itemDespensa.getValidade() != null){
                pstmt.setDate(4, Date.valueOf(itemDespensa.getValidade()));
            } else {
                pstmt.setNull(4, Types.DATE);
            }

            pstmt.setInt(5, itemDespensa.getId());
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public List<ItemDespensa> listarFiltrado(
            Integer idItemInicio,
            Integer idItemFim,
            Integer qtdMinima,
            Integer qtdMaxima,
            LocalDate dataInicio,
            LocalDate dataFim,
            String ordenar
    ){
        StringBuilder sql = new StringBuilder("SELECT * FROM tb_item_dispensa WHERE 1=1");
        List<Object> parametrosDoFiltro = new ArrayList<>();
        List<ItemDespensa> itensDespensa = new ArrayList<>();

        if(idItemInicio != null){
            sql.append(" AND id_item >=?");
            parametrosDoFiltro.add(idItemInicio);
        }
        if(idItemFim != null){
            sql.append(" AND id_item <=?");
            parametrosDoFiltro.add(idItemFim);
        }
        if(qtdMinima != null){
            sql.append(" AND quantidade >=?");
            parametrosDoFiltro.add(qtdMinima);
        }
        if(qtdMaxima != null){
            sql.append(" AND quantidade <=?");
            parametrosDoFiltro.add(qtdMaxima);
        }
        if(dataInicio != null){
            sql.append(" AND validade >=?");
            parametrosDoFiltro.add(dataInicio);
        }
        if(dataFim != null){
            sql.append(" AND validade <=?");
            parametrosDoFiltro.add(dataFim);
        }

        switch (ordenar){
            case "id":
                sql.append(" ORDER BY id_item");
                break;

            case "quantidade":
                sql.append(" ORDER BY quantidade");
                break;

            case "validade":
                sql.append(" ORDER BY validade");
                break;

            default:
                sql.append(" ORDER BY id_item");
                break;
        }

        //Cria executor que receberá comando e executará ele
        try (   Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql.toString())
        ){

            for(int i = 0; i < parametrosDoFiltro.size(); i++){
                pstmt.setObject(i+1, parametrosDoFiltro.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()){
                //Enquanto tiver mais registros cria novos objetos e adiciona à lista
                while (rs.next()) {
                    ItemDespensa itemDespensa = new ItemDespensa();
                    itemDespensa.setId(rs.getInt(1));
                    itemDespensa.setIdCasa(rs.getInt(2));
                    itemDespensa.setIdAlimento(rs.getInt(3));
                    itemDespensa.setQuantidade(rs.getInt(4));

                    if (rs.getDate(5) != null) {
                        itemDespensa.setValidade(rs.getDate(5).toLocalDate());
                    }
                    itensDespensa.add(itemDespensa);
                }

                // Retrona a lista de itens da dispensa
                return itensDespensa;
            }

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return itensDespensa;
        }
    }
    public ItemDespensa buscarporId(int id){
        String sql = "SELECT * FROM tb_item_despensa WHERE id=?";
        try(
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, id);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    ItemDespensa itemDespensa = new ItemDespensa(
                            rs.getInt("id_usuario"),
                            rs.getInt("id_alimento"),
                            rs.getInt("quantidade"),
                            rs.getDate("validade").toLocalDate()
                    );
                    return itemDespensa;
                }
            }

        } catch (SQLException sqle){
            System.out.println("Erro de SQL: " + sqle.getMessage());
        }
        return null;
    }

}