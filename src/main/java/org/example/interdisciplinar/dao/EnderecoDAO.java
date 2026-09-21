package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
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

    // Método de atualizar a rua do endereço
    public boolean atualizarRua(String ruaNova, int idEndereco){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de atualizar em SQL
        String sqlUpdateRua = "UPDATE tb_endereco SET rua=? WHERE id_endereco=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateRua)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, ruaNova);
            pstmt.setInt(2, idEndereco);

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

    // Método de atualizar o número da casa do endereço
    public boolean atualizarNumero(String numeroNovo, int idEndereco){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de atualizar em SQL
        String sqlUpdateNumero = "UPDATE tb_endereco SET numero=? WHERE id_endereco=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateNumero)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, numeroNovo);
            pstmt.setInt(2, idEndereco);

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

    // Método de atualizar o bairro do endereço
    public boolean atualizarBairro(String bairroNovo, int idEndereco){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de atualizar em SQL
        String sqlUpdatebairro = "UPDATE tb_endereco SET bairro=? WHERE id_endereco=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdatebairro)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, bairroNovo);
            pstmt.setInt(2, idEndereco);

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

    // Método de atualizar a cidade do endereço
    public boolean atualizarCidade(String cidadeNova, int idEndereco){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de atualizar em SQL
        String sqlUpdateCidade = "UPDATE tb_endereco SET cidade=? WHERE id_endereco=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateCidade)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, cidadeNova);
            pstmt.setInt(2, idEndereco);

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

    // Método de atualizar o estado do endereço
    public boolean atualizarEstado(String estadoNovo, int idEndereco){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de atualizar em SQL
        String sqlUpdateEstado = "UPDATE tb_endereco SET estado=? WHERE id_endereco=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateEstado)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, estadoNovo);
            pstmt.setInt(2, idEndereco);

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

    // Método de atualizar o cep do endereço
    public boolean atualizarCep(String cepNovo, int idEndereco){

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de atualizar em SQL
        String sqlUpdateCep = "UPDATE tb_endereco SET cep=? WHERE id_endereco=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai atribuir valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateCep)){

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, cepNovo);
            pstmt.setInt(2, idEndereco);

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