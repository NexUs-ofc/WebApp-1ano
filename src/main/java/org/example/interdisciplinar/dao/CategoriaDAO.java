package org.example.interdisciplinar.dao;

// Importa as bibliotecas e classes necessárias para trabalhar com banco de dados, listas e categorias
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Classe responsável pelas operações das categorias no banco de dados
public class CategoriaDAO {

    // Método que cadastra uma categoria no banco de dados
    public boolean inserir(Categoria categoria){

        // Comando SQL para cadastrar uma categoria
        String sqlInsert = "INSERT INTO tb_categoria(nome, descricao) VALUES (?, ?)";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlInsert)
        ){

            // Preenche os parâmetros do comando SQL
            pstmt.setString(1, categoria.getNome());
            pstmt.setString(2, categoria.getDescricao());

            // Executa o cadastro e verifica se algum registro foi inserido
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao inserir uma categoria no banco de dados.",
                    e
            );
        }
    }

    // Método que deleta uma categoria pelo seu ID
    public boolean deletar(int id){

        // Comando SQL para excluir uma categoria
        String sqlDelete = "DELETE FROM tb_categoria WHERE id_categoria=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlDelete)
        ){

            // Informa o ID da categoria que será excluída
            pstmt.setInt(1, id);

            // Executa a exclusão e verifica se algum registro foi removido
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao deletar uma categoria do banco de dados.",
                    e
            );
        }
    }

    // Método que lista todas as categorias cadastradas
    public List<Categoria> listar(){

        // Comando SQL para consultar todas as categorias
        String sqlSelect = "SELECT id_categoria, nome, descricao FROM tb_categoria";

        // Lista que vai armazenar as categorias encontradas
        List<Categoria> categorias = new ArrayList<>();

        // Abre a conexão e executa a consulta
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
                ResultSet rs = pstmt.executeQuery()
        ){

            // Percorre todos os registros encontrados
            while (rs.next()) {
                Categoria categoriaTemporaria = new Categoria();

                categoriaTemporaria.setId(rs.getInt("id_categoria"));
                categoriaTemporaria.setNome(rs.getString("nome"));
                categoriaTemporaria.setDescricao(rs.getString("descricao"));

                categorias.add(categoriaTemporaria);
            }

            // Retorna a lista de categorias
            return categorias;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao listar as categorias no banco de dados.",
                    e
            );
        }
    }

    // Método que atualiza os dados de uma categoria
    public boolean atualizar(Categoria categoria){

        // Comando SQL para atualizar uma categoria pelo ID
        String sql = "UPDATE tb_categoria SET nome=?, descricao=? WHERE id_categoria=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ){

            // Preenche os parâmetros do comando SQL
            pstmt.setString(1, categoria.getNome());
            pstmt.setString(2, categoria.getDescricao());
            pstmt.setInt(3, categoria.getId());

            // Executa a atualização e verifica se algum registro foi alterado
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao atualizar a categoria no banco de dados.",
                    e
            );
        }
    }

    // Método que lista categorias de acordo com os filtros informados
    public List<Categoria> listarFiltrado(
            Integer idCategoriaInicio,
            Integer idCategoriaFim,
            String nome,
            String ordenar
    ) {

        // Monta a consulta SQL conforme os filtros recebidos
        StringBuilder sql = new StringBuilder(
                "SELECT id_categoria, nome, descricao FROM tb_categoria WHERE 1=1"
        );

        // Lista que armazena os valores utilizados nos filtros
        List<Object> parametrosDoFiltro = new ArrayList<>();

        // Lista que vai armazenar as categorias encontradas
        List<Categoria> categorias = new ArrayList<>();

        // Filtra pelo ID inicial, caso tenha sido informado
        if (idCategoriaInicio != null) {
            sql.append(" AND id_categoria >=?");
            parametrosDoFiltro.add(idCategoriaInicio);
        }

        // Filtra pelo ID final, caso tenha sido informado
        if (idCategoriaFim != null) {
            sql.append(" AND id_categoria <=?");
            parametrosDoFiltro.add(idCategoriaFim);
        }

        // Filtra pelo nome, caso tenha sido informado
        if (nome != null && !nome.isBlank()) {
            sql.append(" AND nome ILIKE ?");
            parametrosDoFiltro.add("%" + nome + "%");
        }

        // Define a ordenação dos resultados
        switch (ordenar == null ? "" : ordenar) {
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

                // Cria um objeto para cada categoria encontrada
                while (rs.next()) {
                    Categoria categoriaTemporaria = new Categoria();

                    categoriaTemporaria.setId(rs.getInt("id_categoria"));
                    categoriaTemporaria.setNome(rs.getString("nome"));
                    categoriaTemporaria.setDescricao(rs.getString("descricao"));

                    categorias.add(categoriaTemporaria);
                }

                // Retorna a lista de categorias filtradas
                return categorias;
            }

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao listar as categorias com filtro no banco de dados.",
                    e
            );
        }
    }

    // Método que busca uma categoria pelo seu ID
    public Categoria buscarporId(int id){

        // Comando SQL para buscar uma categoria pelo ID
        String sql = "SELECT id_categoria, nome, descricao FROM tb_categoria WHERE id_categoria=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ){

            // Informa o ID utilizado na consulta
            pstmt.setInt(1, id);

            // Executa a consulta e verifica se encontrou alguma categoria
            try (ResultSet rs = pstmt.executeQuery()){
                if (rs.next()){

                    // Cria um objeto com os dados encontrados
                    Categoria categoria = new Categoria(
                            rs.getString("nome"),
                            rs.getString("descricao")
                    );

                    categoria.setId(rs.getInt("id_categoria"));

                    return categoria;
                }
            }

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao buscar uma categoria pelo ID no banco de dados.",
                    e
            );
        }

        return null;
    }
}