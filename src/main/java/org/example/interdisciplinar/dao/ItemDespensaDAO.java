package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dto.ItemDespensaView;
import org.example.interdisciplinar.model.ItemDespensa;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Abertura da classe
public class ItemDespensaDAO {

    // Método para inserir um item na despensa do usuário
    public boolean inserir(ItemDespensa itemDespensa) {
        // Comando de inserir em SQL
        String sql = "INSERT INTO tb_item_despensa " +
                "(id_usuario, id_alimento, quantidade, data_validade) VALUES (?, ?, ?, ?)";

        // Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        // Inicia o executor que adiciona os valores e executa o comando SQL
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Seta os valores nos '?' do comando SQL
            pstmt.setInt(1, itemDespensa.getIdUsuario());
            pstmt.setInt(2, itemDespensa.getIdAlimento());
            pstmt.setInt(3, itemDespensa.getQuantidade());
            preencherValidade(pstmt, 4, itemDespensa.getValidade());

            return finalizarAlteracao(pstmt.executeUpdate());

        // Em casos de erros no banco de dados, desfaz a ação
        } catch (SQLException sqle) {
            // Lista todos os erros
            sqle.printStackTrace();

            // Desfaz a ação
            GerenteConexao.rollback();

            // Retorna false
            return false;
        }
    }

    // Método para deletar um item da despensa do usuário
    public boolean deletar(int idItem) {
        // Comando de deletar em SQL
        String sql = "DELETE FROM tb_item_despensa WHERE id_item=?";

        // Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        // Inicia o executor que atribui o valor e executa o comando SQL
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Seta o valor no '?' do comando SQL
            pstmt.setInt(1, idItem);
            return finalizarAlteracao(pstmt.executeUpdate());

        // Em casos de erros no banco de dados, desfaz a ação
        } catch (SQLException sqle) {
            // Lista todos os erros
            sqle.printStackTrace();

            // Desfaz a ação
            GerenteConexao.rollback();

            // Retorna false
            return false;
        }
    }

    // Método para listar todos os itens da despensa
    public List<ItemDespensa> listar() {
        // Comando de listar em SQL
        String sql = "SELECT id_item, id_usuario, id_alimento, quantidade, data_validade " +
                "FROM tb_item_despensa ORDER BY id_item";

        // Cria a lista que irá armazenar os registros dos itens da despensa
        List<ItemDespensa> itensDespensa = new ArrayList<>();

        // Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        // Cria o executor que recebe o comando e executa a consulta
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Enquanto tiver registros, cria os objetos e adiciona à lista
            while (rs.next()) {
                itensDespensa.add(criarItemDespensa(rs));
            }

            GerenteConexao.commit();
        } catch (SQLException sqle) {
            // Lista todos os erros
            sqle.printStackTrace();
            GerenteConexao.rollback();
        }

        // Retorna a lista de itens da despensa
        return itensDespensa;
    }

    public List<ItemDespensaView> listarView() {
        String sql = "SELECT i.id_item, u.nome AS nome_usuario, a.nome AS alimento, " +
                "i.quantidade, i.data_validade FROM tb_item_despensa i " +
                "JOIN tb_usuario u ON i.id_usuario = u.id_usuario " +
                "JOIN tb_alimento a ON i.id_alimento = a.id_alimento ORDER BY i.id_item";
        List<ItemDespensaView> itensDespensa = new ArrayList<>();
        Connection conn = GerenteConexao.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Date validade = rs.getDate("data_validade");
                itensDespensa.add(new ItemDespensaView(
                        rs.getInt("id_item"),
                        rs.getString("nome_usuario"),
                        rs.getString("alimento"),
                        rs.getInt("quantidade"),
                        validade == null ? null : validade.toLocalDate()
                ));
            }
            GerenteConexao.commit();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            GerenteConexao.rollback();
        }

        return itensDespensa;
    }

    // Método para atualizar um item da despensa pelo id
    public boolean atualizar(ItemDespensa itemDespensa) {
        String sql = "UPDATE tb_item_despensa SET id_usuario=?, id_alimento=?, " +
                "quantidade=?, data_validade=? WHERE id_item=?";
        Connection conn = GerenteConexao.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemDespensa.getIdUsuario());
            pstmt.setInt(2, itemDespensa.getIdAlimento());
            pstmt.setInt(3, itemDespensa.getQuantidade());
            preencherValidade(pstmt, 4, itemDespensa.getValidade());
            pstmt.setInt(5, itemDespensa.getId());

            return finalizarAlteracao(pstmt.executeUpdate());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            GerenteConexao.rollback();
            return false;
        }
    }

    // Método para listar os itens usando os filtros recebidos
    public List<ItemDespensa> listarFiltrado(
            Integer idItemInicio,
            Integer idItemFim,
            Integer qtdMinima,
            Integer qtdMaxima,
            LocalDate dataInicio,
            LocalDate dataFim,
            String ordenar
    ) {
        StringBuilder sql = new StringBuilder(
                "SELECT id_item, id_usuario, id_alimento, quantidade, data_validade " +
                        "FROM tb_item_despensa WHERE 1=1"
        );
        List<Object> parametrosDoFiltro = new ArrayList<>();
        List<ItemDespensa> itensDespensa = new ArrayList<>();

        if (idItemInicio != null) {
            sql.append(" AND id_item >=?");
            parametrosDoFiltro.add(idItemInicio);
        }
        if (idItemFim != null) {
            sql.append(" AND id_item <=?");
            parametrosDoFiltro.add(idItemFim);
        }
        if (qtdMinima != null) {
            sql.append(" AND quantidade >=?");
            parametrosDoFiltro.add(qtdMinima);
        }
        if (qtdMaxima != null) {
            sql.append(" AND quantidade <=?");
            parametrosDoFiltro.add(qtdMaxima);
        }
        if (dataInicio != null) {
            sql.append(" AND data_validade >=?");
            parametrosDoFiltro.add(dataInicio);
        }
        if (dataFim != null) {
            sql.append(" AND data_validade <=?");
            parametrosDoFiltro.add(dataFim);
        }

        if ("quantidade".equals(ordenar)) {
            sql.append(" ORDER BY quantidade");
        } else if ("validade".equals(ordenar)) {
            sql.append(" ORDER BY data_validade");
        } else {
            sql.append(" ORDER BY id_item");
        }

        Connection conn = GerenteConexao.conectar();

        // Cria o executor que recebe o comando e executa a consulta
        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametrosDoFiltro.size(); i++) {
                pstmt.setObject(i + 1, parametrosDoFiltro.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                // Enquanto tiver registros, cria os objetos e adiciona à lista
                while (rs.next()) {
                    itensDespensa.add(criarItemDespensa(rs));
                }
            }

            GerenteConexao.commit();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            GerenteConexao.rollback();
        }

        return itensDespensa;
    }

    // Método para buscar um item pelo id
    public ItemDespensa buscarporId(int id) {
        String sql = "SELECT id_item, id_usuario, id_alimento, quantidade, data_validade " +
                "FROM tb_item_despensa WHERE id_item=?";
        Connection conn = GerenteConexao.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                ItemDespensa itemDespensa = rs.next() ? criarItemDespensa(rs) : null;
                GerenteConexao.commit();
                return itemDespensa;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            GerenteConexao.rollback();
            return null;
        }
    }

    // Evita repetir a mesma confirmação em todas as alterações
    private boolean finalizarAlteracao(int linhasAfetadas) {
        // Se a ação alterou dados, confirma e retorna true
        if (linhasAfetadas > 0) {
            GerenteConexao.commit();
            return true;
        }

        // Se não alterou dados, desfaz a ação e retorna false
        GerenteConexao.rollback();
        return false;
    }

    // Preenche uma data que pode ser nula
    private void preencherValidade(PreparedStatement pstmt, int posicao, LocalDate validade)
            throws SQLException {
        if (validade == null) {
            pstmt.setNull(posicao, Types.DATE);
        } else {
            pstmt.setDate(posicao, Date.valueOf(validade));
        }
    }

    // Converte uma linha do banco em um item da despensa
    private ItemDespensa criarItemDespensa(ResultSet rs) throws SQLException {
        ItemDespensa itemDespensa = new ItemDespensa();
        itemDespensa.setId(rs.getInt("id_item"));
        itemDespensa.setIdUsuario(rs.getInt("id_usuario"));
        itemDespensa.setIdAlimento(rs.getInt("id_alimento"));
        itemDespensa.setQuantidade(rs.getInt("quantidade"));

        Date validade = rs.getDate("data_validade");
        if (validade != null) {
            itemDespensa.setValidade(validade.toLocalDate());
        }

        return itemDespensa;
    }
}
