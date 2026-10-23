package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.ItemDespensa;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Abertura da classe
public class ItemDespensaDAO {

    // Método de inserir um item na despensa do usuário
    public boolean inserir(ItemDespensa itemDespensa){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de inserir em SQL
        String sqlInsert = "INSERT INTO tb_item_despensa (id_casa, id_alimento, quantidade, data_validade) VALUES (?, ?, ?, ?)";

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

    // Método para deletar um item da despensa do usuário
    public boolean deletar(int idItem){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de deletar em SQL
        String sqlDelete = "DELETE FROM tb_item_despensa WHERE id_item=?";

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

    // Método para listar todos os itens da despensa
    public List<ItemDespensa> listar(){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de listar em SQL
        String sqlSelect = "SELECT * FROM tb_item_despensa";

        // Criando lista que irá armazenar os registros dos itens da despensa
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

            // Commita a ação no banco de dados
            GerenteConexao.commit();

            // Retrona a lista de itens da despensa
            return itensDespensa;

            //Em casos de erros no banco de dados desfaz a ação
        } catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna a lista
            return itensDespensa;
        }

    }

    // Método para atualizar a quantidade de itens da despensa
    public boolean atualizarQuantidade(int qtdNova, int idItem){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de atualizar em SQL
        String sqlUpdateQtd = "UPDATE tb_item_despensa SET quantidade=? WHERE id_item=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateQtd)){

            //Seta valores nos '?' do comando SQL
            pstmt.setInt(1, qtdNova);
            pstmt.setInt(2, idItem);

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

    // Método para atualizar a validade do item da despensa
    public boolean atualizarValidade(LocalDate dataNova, int idItem){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de atualizar em SQL
        String sqlUpdateValidade = "UPDATE tb_item_despensa SET data_validade=? WHERE id_item=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateValidade)){

            //Seta valores nos '?' do comando SQL
            pstmt.setDate(1, Date.valueOf(dataNova));
            pstmt.setInt(2, idItem);

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
}
