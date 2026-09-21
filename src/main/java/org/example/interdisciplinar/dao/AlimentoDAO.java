package org.example.interdisciplinar.dao;


import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.model.Alimento;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

// Abertura de classe
public class AlimentoDAO {

    // Método de inserir um alimento no banco de dados
    public boolean inserir(Alimento alimento){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Comando de inserir em SQL
        String sqlInsert = "INSERT INTO tb_alimento (codigo_barras, nome, marca, id_categoria) VALUES (?, ?, ?, ?)";

        //Iniciando executor que: adicionará valores no comando SQL e executará o comando
        try(PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, alimento.getCodigoBarras());
            pstmt.setString(2, alimento.getNome());
            pstmt.setString(3, alimento.getMarca());
            pstmt.setInt(4, alimento.getIdCategoria());

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
    public boolean deletar(Alimento alimento){

        // Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de deletar em SQL
        String sqlDelete = "DELETE FROM tb_alimento WHERE id_alimento=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai adicionar valores no comando SQL e executará o comando
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)){

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
    public List<Alimento> listar(){

        Connection conn = GerenteConexao.conectar();
        String sqlSelect = "SELECT * FROM tb_alimento";
        List<Alimento> alimentos = new ArrayList<>();

        //Iniciando executor que: adicionará valores no comando SQL e executará o comando
        try(PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
            ResultSet rs  = pstmt.executeQuery();

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while(rs.next()){
                Alimento alimentoTemporario = new Alimento();
                alimentoTemporario.setId(rs.getInt(1));
                alimentoTemporario.setCodigoBarras(rs.getString(2));
                alimentoTemporario.setNome(rs.getString(3));
                alimentoTemporario.setMarca(rs.getString(4));
                alimentoTemporario.setIdCategoria(rs.getInt(5));
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

    // Método de atualizar nome de alimento com base no id
    public boolean atualizarNome(String nomeNovo, int idAlimento){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de atualizar em SQL
        String sqlUpdateName = "UPDATE tb_alimento SET nome=? WHERE id_alimento=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateName)){
            pstmt.setString(1, nomeNovo);
            pstmt.setInt(2, idAlimento);

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

    // Método de atualizar código de barras com base no id
    public boolean atualizarCodigo(String codBarrasNovo, int idAlimento){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de atualizar codigo de barras em SQL
        String sqlUpdateCod = "UPDATE tb_alimento SET codigo_barras=? WHERE id_alimento=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateCod)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, codBarrasNovo);
            pstmt.setInt(2, idAlimento);

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

    // Método de atualizar marca com base no id
    public boolean atualizarMarca(String marcaNova, int idAlimento){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de atualizar a marca em SQL
        String sqlUpdateMarca = "UPDATE tb_alimento SET marca=? WHERE id_alimento=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateMarca)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, marcaNova);
            pstmt.setInt(2, idAlimento);

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
