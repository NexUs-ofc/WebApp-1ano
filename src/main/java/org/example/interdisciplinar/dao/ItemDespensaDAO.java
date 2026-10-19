package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.ItemDispensa;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Abertura da classe
public class ItemDispensaDAO {

    // Método de inserir um item na dispensa do usuário
    public boolean inserir(ItemDispensa itemDispensa){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de inserir em SQL
        String sqlInsert = "INSERT INTO tb_item_dispensa (id_casa, id_alimento, quantidade, data_validade) VALUES (?, ?, ?, ?)";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai adicionar valores no comando SQL e executará o comando
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

            //Seta valores nos '?' do comando SQL
            pstmt.setInt(1, itemDispensa.getIdCasa());
            pstmt.setInt(2, itemDispensa.getIdAlimento());
            pstmt.setInt(3, itemDispensa.getQuantidade());
            pstmt.setDate(4, Date.valueOf(itemDispensa.getValidade()));

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
    public List<ItemDispensa> listar(){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de listar em SQL
        String sqlSelect = "SELECT * FROM tb_item_dispensa";

        // Criando lista que irá armazenar os registros dos itens da dispensa
        List<ItemDispensa> itensDispensa = new ArrayList<>();

        //Cria executor que receberá comando e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect)){
            ResultSet rs = pstmt.executeQuery();

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while (rs.next()) {
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

            // Commita a ação no banco de dados
            GerenteConexao.commit();

            // Retrona a lista de itens da dispensa
            return itensDispensa;

            //Em casos de erros no banco de dados desfaz a ação
        } catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna a lista
            return itensDispensa;
        }

    }

    // Método para atualizar a quantidade de itens da dispensa
    public boolean atualizarQuantidade(int qtdNova, int idItem){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de atualizar em SQL
        String sqlUpdateQtd = "UPDATE tb_item_dispensa SET quantidade=? WHERE id_item=?";

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

    // Método para atualizar a validade do item da dispensa
    public boolean atualizarValidade(LocalDate dataNova, int idItem){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de atualizar em SQL
        String sqlUpdateValidade = "UPDATE tb_item_dispensa SET data_validade=? WHERE id_item=?";

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