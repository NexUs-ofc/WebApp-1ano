package org.example.interdisciplinar.dao;

// Importa as bibliotecas e classes necessárias para trabalhar com banco de dados, listas e itens da despensa
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dto.ItemDespensaView;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.model.ItemDespensa;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Classe responsável pelas operações dos itens da despensa no banco de dados
public class ItemDespensaDAO {

    // Método que cadastra um item na despensa
    public boolean inserir(ItemDespensa itemDespensa){

        // Comando SQL para cadastrar um item
        String sqlInsert = "INSERT INTO tb_item_despensa (id_usuario, id_alimento, quantidade, data_validade) VALUES (?, ?, ?, ?)";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlInsert)
        ) {

            // Preenche os parâmetros do comando SQL
            pstmt.setInt(1, itemDespensa.getIdUsuario());
            pstmt.setInt(2, itemDespensa.getIdAlimento());
            pstmt.setInt(3, itemDespensa.getQuantidade());

            if (itemDespensa.getValidade() != null) {
                pstmt.setDate(4, Date.valueOf(itemDespensa.getValidade()));
            } else {
                pstmt.setNull(4, Types.DATE);
            }

            // Executa o cadastro e verifica se algum registro foi inserido
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao inserir um item na despensa no banco de dados.",
                    e
            );
        }
    }

    // Método que deleta um item da despensa pelo seu ID
    public boolean deletar(int idItem){

        String sqlDeleteAlertas = "DELETE FROM tb_alerta WHERE id_item=?";
        String sqlDeleteItem = "DELETE FROM tb_item_despensa WHERE id_item=?";

        try (Connection conn = GerenteConexao.conectar()) {
            conn.setAutoCommit(false);

            try (
                    PreparedStatement pstmtAlertas = conn.prepareStatement(sqlDeleteAlertas);
                    PreparedStatement pstmtItem = conn.prepareStatement(sqlDeleteItem)
            ) {
                pstmtAlertas.setInt(1, idItem);
                pstmtAlertas.executeUpdate();

                pstmtItem.setInt(1, idItem);
                int linhasAfetadas = pstmtItem.executeUpdate();

                conn.commit();
                return linhasAfetadas > 0;

            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackException) {
                    e.addSuppressed(rollbackException);
                }
                throw e;
            }

        } catch (SQLException e) {
            throw new ErroPersistenciaException(
                    "Erro ao deletar um item da despensa no banco de dados.",
                    e
            );
        }
    }
    // Método que lista todos os itens das despensas
    public List<ItemDespensaView> listar(){

        // Comando SQL para consultar todos os itens
        String sqlSelect = "SELECT item.id_item, item.id_usuario, item.id_alimento, " +
                "usuario.nome AS usuario, alimento.nome AS alimento, item.quantidade, item.data_validade " +
                "FROM tb_item_despensa AS item " +
                "JOIN tb_usuario AS usuario ON usuario.id_usuario = item.id_usuario " +
                "JOIN tb_alimento AS alimento ON alimento.id_alimento = item.id_alimento";

        // Lista que vai armazenar os itens encontrados
        List<ItemDespensaView> itensDespensa = new ArrayList<>();

        // Abre a conexão e executa a consulta
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
                ResultSet rs = pstmt.executeQuery()
        ) {

            // Percorre todos os registros encontrados
            while (rs.next()) {

                LocalDate validade = null;

                if (rs.getDate("data_validade") != null) {
                    validade = rs.getDate("data_validade").toLocalDate();
                }

                // Cria um objeto com os dados encontrados
                ItemDespensaView itemDespensa = new ItemDespensaView(
                        rs.getInt("id_item"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_alimento"),
                        rs.getString("usuario"),
                        rs.getString("alimento"),
                        rs.getInt("quantidade"),
                        validade
                );

                itensDespensa.add(itemDespensa);
            }

            // Retorna a lista de itens da despensa
            return itensDespensa;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao listar os itens da despensa no banco de dados.",
                    e
            );
        }
    }

    // Método que atualiza os dados de um item da despensa
    public boolean atualizar(ItemDespensa itemDespensa){

        // Comando SQL para atualizar um item pelo ID
        String sql = "UPDATE tb_item_despensa SET id_usuario=?, id_alimento=?, quantidade=?, data_validade=? WHERE id_item=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ){

            // Preenche os parâmetros do comando SQL
            pstmt.setInt(1, itemDespensa.getIdUsuario());
            pstmt.setInt(2, itemDespensa.getIdAlimento());
            pstmt.setInt(3, itemDespensa.getQuantidade());

            // Verifica se a validade foi informada
            if(itemDespensa.getValidade() != null){
                pstmt.setDate(4, Date.valueOf(itemDespensa.getValidade()));
            } else {
                pstmt.setNull(4, Types.DATE);
            }

            pstmt.setInt(5, itemDespensa.getId());

            // Executa a atualização e verifica se algum registro foi alterado
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e){

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao atualizar um item da despensa no banco de dados.",
                    e
            );
        }
    }

    // Método que lista itens da despensa de acordo com os filtros informados
    public List<ItemDespensaView> listarFiltrado(
            Integer idItemInicio,
            Integer idItemFim,
            Integer qtdMinima,
            Integer qtdMaxima,
            LocalDate dataInicio,
            LocalDate dataFim,
            String ordenar
    ){

        // Monta a consulta SQL conforme os filtros recebidos
        StringBuilder sql = new StringBuilder(
                "SELECT item.id_item, item.id_usuario, item.id_alimento, " +
                        "usuario.nome AS usuario, alimento.nome AS alimento, item.quantidade, item.data_validade " +
                        "FROM tb_item_despensa AS item " +
                        "JOIN tb_usuario AS usuario ON usuario.id_usuario = item.id_usuario " +
                        "JOIN tb_alimento AS alimento ON alimento.id_alimento = item.id_alimento " +
                        "WHERE 1=1"
        );

        // Lista que armazena os valores utilizados nos filtros
        List<Object> parametrosDoFiltro = new ArrayList<>();

        // Lista que vai armazenar os itens encontrados
        List<ItemDespensaView> itensDespensa = new ArrayList<>();

        // Filtra pelo ID inicial, caso tenha sido informado
        if(idItemInicio != null){
            sql.append(" AND id_item >= ?");
            parametrosDoFiltro.add(idItemInicio);
        }

        // Filtra pelo ID final, caso tenha sido informado
        if(idItemFim != null){
            sql.append(" AND id_item <= ?");
            parametrosDoFiltro.add(idItemFim);
        }

        // Filtra pela quantidade mínima, caso tenha sido informada
        if(qtdMinima != null){
            sql.append(" AND quantidade >= ?");
            parametrosDoFiltro.add(qtdMinima);
        }

        // Filtra pela quantidade máxima, caso tenha sido informada
        if(qtdMaxima != null){
            sql.append(" AND quantidade <= ?");
            parametrosDoFiltro.add(qtdMaxima);
        }

        // Filtra pela data inicial, caso tenha sido informada
        if(dataInicio != null){
            sql.append(" AND data_validade >= ?");
            parametrosDoFiltro.add(Date.valueOf(dataInicio));
        }

        // Filtra pela data final, caso tenha sido informada
        if(dataFim != null){
            sql.append(" AND data_validade <= ?");
            parametrosDoFiltro.add(Date.valueOf(dataFim));
        }

        // Define a ordenação dos resultados
        switch (ordenar == null ? "" : ordenar){
            case "id":
                sql.append(" ORDER BY id_item");
                break;

            case "quantidade":
                sql.append(" ORDER BY quantidade");
                break;

            case "validade":
                sql.append(" ORDER BY data_validade");
                break;

            default:
                sql.append(" ORDER BY id_item");
                break;
        }

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql.toString())
        ){

            // Preenche os parâmetros dos filtros na consulta
            for(int i = 0; i < parametrosDoFiltro.size(); i++){
                pstmt.setObject(i + 1, parametrosDoFiltro.get(i));
            }

            // Executa a consulta e percorre os resultados
            try (ResultSet rs = pstmt.executeQuery()){

                // Cria um objeto para cada item encontrado
                while (rs.next()) {

                    LocalDate validade = null;

                    if (rs.getDate("data_validade") != null) {
                        validade = rs.getDate("data_validade").toLocalDate();
                    }

                    ItemDespensaView itemDespensa = new ItemDespensaView(
                            rs.getInt("id_item"),
                            rs.getInt("id_usuario"),
                            rs.getInt("id_alimento"),
                            rs.getString("usuario"),
                            rs.getString("alimento"),
                            rs.getInt("quantidade"),
                            validade
                    );

                    itensDespensa.add(itemDespensa);
                }

                // Retorna a lista de itens filtrados
                return itensDespensa;
            }

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao listar os itens da despensa com filtro no banco de dados.",
                    e
            );
        }
    }

    // Método que busca um item da despensa pelo seu ID
    public ItemDespensa buscarporId(int id){

        // Comando SQL para buscar um item pelo ID
        String sql = "SELECT id_item, id_usuario, id_alimento, quantidade, data_validade FROM tb_item_despensa WHERE id_item=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ){

            // Informa o ID utilizado na consulta
            pstmt.setInt(1, id);

            // Executa a consulta e verifica se encontrou algum item
            try (ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){

                    // Converte a validade para LocalDate
                    Date dataValidade = rs.getDate("data_validade");

                    // Cria um objeto com os dados encontrados
                    ItemDespensa itemDespensa = new ItemDespensa(
                            rs.getInt("id_usuario"),
                            rs.getInt("id_alimento"),
                            rs.getInt("quantidade"),
                            dataValidade == null ? null : dataValidade.toLocalDate()
                    );

                    itemDespensa.setId(rs.getInt("id_item"));

                    return itemDespensa;
                }
            }

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao buscar um item da despensa pelo ID no banco de dados.",
                    e
            );
        }

        return null;
    }
}