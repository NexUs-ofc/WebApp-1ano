package org.example.interdisciplinar.dao;

// Importa as bibliotecas e classes necessárias para trabalhar com banco de dados, listas e endereços
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Classe responsável pelas operações dos endereços no banco de dados
public class EnderecoDAO {

    // Método que cadastra um endereço no banco de dados
    public boolean inserir(Endereco endereco){

        // Comando SQL para cadastrar um endereço
        String sqlInsert = "INSERT INTO tb_endereco (rua, numero, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?)";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlInsert)
        ){

            // Preenche os parâmetros do comando SQL
            pstmt.setString(1, endereco.getRua());
            pstmt.setString(2, endereco.getNumero());
            pstmt.setString(3, endereco.getBairro());
            pstmt.setString(4, endereco.getCidade());
            pstmt.setString(5, endereco.getEstado());
            pstmt.setString(6, endereco.getCep());

            // Executa o cadastro e verifica se algum registro foi inserido
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao inserir um endereço no banco de dados.",
                    e
            );
        }
    }

    // Método que deleta um endereço pelo seu ID
    public boolean deletar(int idEndereco){

        // Comando SQL para excluir um endereço
        String sqlDelete = "DELETE FROM tb_endereco WHERE id_endereco=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlDelete)
        ){

            // Informa o ID do endereço que será excluído
            pstmt.setInt(1, idEndereco);

            // Executa a exclusão e verifica se algum registro foi removido
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao deletar um endereço no banco de dados.",
                    e
            );
        }
    }

    // Método que lista todos os endereços cadastrados
    public List<Endereco> listar(){

        // Comando SQL para consultar todos os endereços
        String sqlSelect = "SELECT id_endereco, rua, numero, bairro, cidade, estado, cep FROM tb_endereco";

        // Lista que vai armazenar os endereços encontrados
        List<Endereco> enderecos = new ArrayList<>();

        // Abre a conexão e executa a consulta
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
                ResultSet rs = pstmt.executeQuery()
        ) {

            // Percorre todos os registros encontrados
            while (rs.next()){
                Endereco enderecoTemporario = new Endereco();

                enderecoTemporario.setId(rs.getInt("id_endereco"));
                enderecoTemporario.setRua(rs.getString("rua"));
                enderecoTemporario.setNumero(rs.getString("numero"));
                enderecoTemporario.setBairro(rs.getString("bairro"));
                enderecoTemporario.setCidade(rs.getString("cidade"));
                enderecoTemporario.setEstado(rs.getString("estado"));
                enderecoTemporario.setCep(rs.getString("cep"));

                enderecos.add(enderecoTemporario);
            }

            // Retorna a lista de endereços
            return enderecos;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao listar os endereços no banco de dados.",
                    e
            );
        }
    }

    // Método que atualiza os dados de um endereço
    public boolean atualizar(Endereco endereco){

        // Comando SQL para atualizar um endereço pelo ID
        String sql = "UPDATE tb_endereco SET rua=?, numero=?, bairro=?, cidade=?, estado=?, cep=? WHERE id_endereco=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ){

            // Preenche os parâmetros do comando SQL
            pstmt.setString(1, endereco.getRua());
            pstmt.setString(2, endereco.getNumero());
            pstmt.setString(3, endereco.getBairro());
            pstmt.setString(4, endereco.getCidade());
            pstmt.setString(5, endereco.getEstado());
            pstmt.setString(6, endereco.getCep());
            pstmt.setInt(7, endereco.getId());

            // Executa a atualização e verifica se algum registro foi alterado
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao atualizar um endereço no banco de dados.",
                    e
            );
        }
    }

    // Método que lista endereços de acordo com os filtros informados
    public List<Endereco> listarFiltrado(
            Integer idEnderecoInicio,
            Integer idEnderecoFim,
            String cidade,
            String estado,
            String ordenar
    ) {

        // Monta a consulta SQL conforme os filtros recebidos
        StringBuilder sql = new StringBuilder(
                "SELECT id_endereco, rua, numero, bairro, cidade, estado, cep FROM tb_endereco WHERE 1=1"
        );

        // Lista que armazena os valores utilizados nos filtros
        List<Object> parametrosDoFiltro = new ArrayList<>();

        // Lista que vai armazenar os endereços encontrados
        List<Endereco> enderecos = new ArrayList<>();

        // Filtra pelo ID inicial, caso tenha sido informado
        if (idEnderecoInicio != null) {
            sql.append(" AND id_endereco >= ?");
            parametrosDoFiltro.add(idEnderecoInicio);
        }

        // Filtra pelo ID final, caso tenha sido informado
        if (idEnderecoFim != null) {
            sql.append(" AND id_endereco <= ?");
            parametrosDoFiltro.add(idEnderecoFim);
        }

        // Filtra pela cidade, caso tenha sido informada
        if (cidade != null && !cidade.isBlank()) {
            sql.append(" AND cidade ILIKE ?");
            parametrosDoFiltro.add("%" + cidade + "%");
        }

        // Filtra pelo estado, caso tenha sido informado
        if (estado != null && !estado.isBlank()) {
            sql.append(" AND estado ILIKE ?");
            parametrosDoFiltro.add("%" + estado + "%");
        }

        // Define a ordenação dos resultados
        switch (ordenar == null ? "" : ordenar) {
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

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql.toString())
        ) {

            // Preenche os parâmetros dos filtros na consulta
            for (int i = 0; i < parametrosDoFiltro.size(); i++) {
                pstmt.setObject(i + 1, parametrosDoFiltro.get(i));
            }

            // Executa a consulta e percorre os resultados
            try (ResultSet rs = pstmt.executeQuery()) {

                // Cria um objeto para cada endereço encontrado
                while (rs.next()){
                    Endereco enderecoTemporario = new Endereco();

                    enderecoTemporario.setId(rs.getInt("id_endereco"));
                    enderecoTemporario.setRua(rs.getString("rua"));
                    enderecoTemporario.setNumero(rs.getString("numero"));
                    enderecoTemporario.setBairro(rs.getString("bairro"));
                    enderecoTemporario.setCidade(rs.getString("cidade"));
                    enderecoTemporario.setEstado(rs.getString("estado"));
                    enderecoTemporario.setCep(rs.getString("cep"));

                    enderecos.add(enderecoTemporario);
                }

                // Retorna a lista de endereços filtrados
                return enderecos;
            }

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao listar endereços com filtro no banco de dados.",
                    e
            );
        }
    }

    // Método que busca um endereço pelo seu ID
    public Endereco buscarporId(int id){

        // Comando SQL para buscar um endereço pelo ID
        String sql = "SELECT id_endereco, rua, numero, bairro, cidade, estado, cep FROM tb_endereco WHERE id_endereco=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ){

            // Informa o ID utilizado na consulta
            pstmt.setInt(1, id);

            // Executa a consulta e verifica se encontrou algum endereço
            try (ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){

                    // Cria um objeto com os dados encontrados
                    Endereco endereco = new Endereco(
                            rs.getString("rua"),
                            rs.getString("numero"),
                            rs.getString("bairro"),
                            rs.getString("cidade"),
                            rs.getString("estado"),
                            rs.getString("cep")
                    );

                    endereco.setId(rs.getInt("id_endereco"));

                    return endereco;
                }
            }

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao buscar um endereço pelo ID no banco de dados.",
                    e
            );
        }

        return null;
    }
}