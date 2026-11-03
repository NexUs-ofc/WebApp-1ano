package org.example.interdisciplinar.dao;

// Importa as bibliotecas e classes necessárias para trabalhar com banco de dados, listas e alimentos
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dto.AlimentoView;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.model.Alimento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Classe responsável pelas operações dos alimentos no banco de dados
public class AlimentoDAO {

    // Método que cadastra um alimento no banco de dados
    public boolean inserir(Alimento alimento) {

        // Comando SQL para cadastrar um alimento
        String sqlInsert = "INSERT INTO tb_alimento (codigo_barras, nome, marca, id_categoria) VALUES (?, ?, ?, ?)";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlInsert)
        ) {

            // Preenche os parâmetros do comando SQL
            pstmt.setString(1, alimento.getCodigoBarras());
            pstmt.setString(2, alimento.getNome());
            pstmt.setString(3, alimento.getMarca());
            pstmt.setInt(4, alimento.getIdCategoria());

            // Executa o cadastro e verifica se algum registro foi inserido
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao inserir o alimento no banco de dados.",
                    e
            );
        }
    }

    // Método que deleta um alimento pelo seu ID
    public boolean deletar(int id) {

        // Comando SQL para excluir um alimento
        String sqlDelete = "DELETE FROM tb_alimento WHERE id_alimento=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlDelete)
        ) {

            // Informa o ID do alimento que será excluído
            pstmt.setInt(1, id);

            // Executa a exclusão e verifica se algum registro foi removido
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao deletar o alimento pelo ID no banco de dados.",
                    e
            );
        }
    }

    // Método que lista todos os alimentos cadastrados
    public List<AlimentoView> listar() {

        // Comando SQL para consultar os alimentos
        String sqlSelect = "SELECT id_alimento, nome, marca, categoria FROM view_alimento";

        // Lista que vai armazenar os alimentos encontrados
        List<AlimentoView> alimentos = new ArrayList<>();

        // Abre a conexão e executa a consulta
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
                ResultSet rs = pstmt.executeQuery()
        ) {

            // Percorre todos os registros encontrados
            while (rs.next()) {
                AlimentoView alimentoTemporario = new AlimentoView(
                        rs.getInt("id_alimento"),
                        rs.getString("nome"),
                        rs.getString("marca"),
                        rs.getString("categoria")
                );

                alimentos.add(alimentoTemporario);
            }

            // Retorna a lista de alimentos
            return alimentos;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao listar os alimentos no banco de dados.",
                    e
            );
        }
    }

    // Método que atualiza os dados de um alimento
    public boolean atualizar(Alimento alimento) {

        // Comando SQL para atualizar um alimento pelo ID
        String sql = "UPDATE tb_alimento SET codigo_barras=?, nome=?, marca=?, id_categoria=? WHERE id_alimento=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            // Preenche os parâmetros do comando SQL
            pstmt.setString(1, alimento.getCodigoBarras());
            pstmt.setString(2, alimento.getNome());
            pstmt.setString(3, alimento.getMarca());
            pstmt.setInt(4, alimento.getIdCategoria());
            pstmt.setInt(5, alimento.getId());

            // Executa a atualização e verifica se algum registro foi alterado
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao atualizar o alimento no banco de dados.",
                    e
            );
        }
    }

    // Método que lista alimentos de acordo com os filtros informados
    public List<AlimentoView> listarFiltrado(
            Integer idAlimentoInicio,
            Integer idAlimentoFim,
            String nome,
            String categoria,
            String marca,
            String ordenar
    ) {

        // Monta a consulta SQL conforme os filtros recebidos
        StringBuilder sql = new StringBuilder("SELECT id_alimento, nome, marca, categoria FROM view_alimento WHERE 1=1");

        // Lista que armazena os valores utilizados nos filtros
        List<Object> parametrosDoFiltro = new ArrayList<>();

        // Lista que vai armazenar os alimentos encontrados
        List<AlimentoView> alimentos = new ArrayList<>();

        // Filtra pelo ID inicial, caso tenha sido informado
        if (idAlimentoInicio != null) {
            sql.append(" AND id_alimento >=?");
            parametrosDoFiltro.add(idAlimentoInicio);
        }

        // Filtra pelo ID final, caso tenha sido informado
        if (idAlimentoFim != null) {
            sql.append(" AND id_alimento <=?");
            parametrosDoFiltro.add(idAlimentoFim);
        }

        // Filtra pelo nome, caso tenha sido informado
        if (nome != null && !nome.isBlank()) {
            sql.append(" AND nome ILIKE ?");
            parametrosDoFiltro.add("%" + nome + "%");
        }

        // Filtra pela categoria, caso tenha sido informada
        if (categoria != null && !categoria.isBlank()) {
            sql.append(" AND categoria ILIKE ?");
            parametrosDoFiltro.add("%" + categoria + "%");
        }

        // Filtra pela marca, caso tenha sido informada
        if (marca != null && !marca.isBlank()) {
            sql.append(" AND marca ILIKE ?");
            parametrosDoFiltro.add("%" + marca + "%");
        }

        // Define a ordenação dos resultados
        switch (ordenar == null ? "" : ordenar) {
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

                // Cria um objeto para cada alimento encontrado
                while (rs.next()) {
                    AlimentoView alimentoTemporario = new AlimentoView(
                            rs.getInt("id_alimento"),
                            rs.getString("nome"),
                            rs.getString("marca"),
                            rs.getString("categoria")
                    );

                    alimentos.add(alimentoTemporario);
                }

                // Retorna a lista de alimentos filtrados
                return alimentos;
            }

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao listar os alimentos com filtro no banco de dados.",
                    e
            );
        }
    }

    // Método que busca um alimento pelo seu ID
    public Alimento buscarporId(int id) {

        // Comando SQL para buscar um alimento pelo ID
        String sql = "SELECT * FROM tb_alimento WHERE id_alimento=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            // Informa o ID utilizado na consulta
            pstmt.setInt(1, id);

            // Executa a consulta e verifica se encontrou algum alimento
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    // Cria um objeto com os dados encontrados
                    Alimento alimento = new Alimento(
                            rs.getString("codigo_barras"),
                            rs.getString("nome"),
                            rs.getString("marca"),
                            rs.getInt("id_categoria")
                    );

                    alimento.setId(rs.getInt("id_alimento"));

                    return alimento;
                }
            }

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao buscar o alimento pelo ID no banco de dados.",
                    e
            );
        }

        return null;
    }
}