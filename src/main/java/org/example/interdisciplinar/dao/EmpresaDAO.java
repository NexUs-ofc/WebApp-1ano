package org.example.interdisciplinar.dao;

// Importa as bibliotecas e classes necessárias para trabalhar com banco de dados, listas e empresas
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dto.EmpresaView;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.model.Empresa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Classe responsável pelas operações das empresas no banco de dados
public class EmpresaDAO {

    // Método que cadastra uma empresa no banco de dados
    public boolean inserir(Empresa empresa) {

        // Comando SQL para cadastrar uma empresa
        String sqlInsert = "INSERT INTO tb_empresa (nome, cnpj, telefone, email, id_endereco) VALUES (?, ?, ?, ?, ?)";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlInsert)
        ) {

            // Preenche os parâmetros do comando SQL
            pstmt.setString(1, empresa.getNome());
            pstmt.setString(2, empresa.getCnpj());
            pstmt.setString(3, empresa.getTelefone());
            pstmt.setString(4, empresa.getEmail());
            pstmt.setInt(5, empresa.getIdEndereco());

            // Executa o cadastro e verifica se algum registro foi inserido
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao inserir uma empresa no banco de dados.",
                    e
            );
        }
    }

    // Método que deleta uma empresa pelo seu ID
    public boolean deletar(int id) {

        // Comando SQL para excluir uma empresa
        String sqlDelete = "DELETE FROM tb_empresa WHERE id_empresa=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlDelete)
        ) {

            // Informa o ID da empresa que será excluída
            pstmt.setInt(1, id);

            // Executa a exclusão e verifica se algum registro foi removido
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao deletar uma empresa no banco de dados.",
                    e
            );
        }
    }

    // Método que lista todas as empresas cadastradas
    public List<EmpresaView> listar() {

        // Comando SQL para consultar as empresas
        String sqlSelect = "SELECT id_empresa, nome, cnpj, cep, telefone FROM view_empresa";

        // Lista que vai armazenar as empresas encontradas
        List<EmpresaView> empresas = new ArrayList<>();

        // Abre a conexão e executa a consulta
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
                ResultSet rs = pstmt.executeQuery()
        ) {

            // Percorre todos os registros encontrados
            while (rs.next()) {
                EmpresaView empresaTemporaria = new EmpresaView(
                        rs.getInt("id_empresa"),
                        rs.getString("nome"),
                        rs.getString("cnpj"),
                        rs.getString("cep"),
                        rs.getString("telefone")
                );

                empresas.add(empresaTemporaria);
            }

            // Retorna a lista de empresas
            return empresas;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao listar as empresas no banco de dados.",
                    e
            );
        }
    }

    // Método que atualiza os dados de uma empresa
    public boolean atualizar(Empresa empresa) {

        // Comando SQL para atualizar uma empresa pelo ID
        String sql = "UPDATE tb_empresa SET nome=?, cnpj=?, telefone=?, email=?, id_endereco=? WHERE id_empresa=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            // Preenche os parâmetros do comando SQL
            pstmt.setString(1, empresa.getNome());
            pstmt.setString(2, empresa.getCnpj());
            pstmt.setString(3, empresa.getTelefone());
            pstmt.setString(4, empresa.getEmail());
            pstmt.setInt(5, empresa.getIdEndereco());
            pstmt.setInt(6, empresa.getId());

            // Executa a atualização e verifica se algum registro foi alterado
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao atualizar uma empresa no banco de dados.",
                    e
            );
        }
    }

    // Método que lista empresas de acordo com os filtros informados
    public List<EmpresaView> listarFiltrado(
            Integer idEmpresaInicio,
            Integer idEmpresaFim,
            String nome,
            String cnpj,
            String cep,
            String ordenar
    ) {

        // Monta a consulta SQL conforme os filtros recebidos
        StringBuilder sql = new StringBuilder(
                "SELECT id_empresa, nome, cnpj, cep, telefone FROM view_empresa WHERE 1=1"
        );

        // Lista que armazena os valores utilizados nos filtros
        List<Object> parametrosDoFiltro = new ArrayList<>();

        // Lista que vai armazenar as empresas encontradas
        List<EmpresaView> empresas = new ArrayList<>();

        // Filtra pelo ID inicial, caso tenha sido informado
        if (idEmpresaInicio != null) {
            sql.append(" AND id_empresa >= ?");
            parametrosDoFiltro.add(idEmpresaInicio);
        }

        // Filtra pelo ID final, caso tenha sido informado
        if (idEmpresaFim != null) {
            sql.append(" AND id_empresa <= ?");
            parametrosDoFiltro.add(idEmpresaFim);
        }

        // Filtra pelo nome, caso tenha sido informado
        if (nome != null && !nome.isBlank()) {
            sql.append(" AND nome ILIKE ?");
            parametrosDoFiltro.add("%" + nome + "%");
        }

        // Filtra pelo CNPJ, caso tenha sido informado
        if (cnpj != null && !cnpj.isBlank()) {
            sql.append(" AND cnpj ILIKE ?");
            parametrosDoFiltro.add("%" + cnpj + "%");
        }

        // Filtra pelo CEP, caso tenha sido informado
        if (cep != null && !cep.isBlank()) {
            sql.append(" AND cep ILIKE ?");
            parametrosDoFiltro.add("%" + cep + "%");
        }

        // Define a ordenação dos resultados
        switch (ordenar == null ? "" : ordenar) {
            case "id":
            case "id_asc":
                sql.append(" ORDER BY id_empresa ASC");
                break;

            case "id_desc":
                sql.append(" ORDER BY id_empresa DESC");
                break;

            case "nome":
            case "nome_asc":
                sql.append(" ORDER BY nome ASC");
                break;

            case "nome_desc":
                sql.append(" ORDER BY nome DESC");
                break;

            case "cnpj":
                sql.append(" ORDER BY cnpj ASC");
                break;

            default:
                sql.append(" ORDER BY id_empresa ASC");
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

                // Cria um objeto para cada empresa encontrada
                while (rs.next()) {
                    EmpresaView empresaTemporaria = new EmpresaView(
                            rs.getInt("id_empresa"),
                            rs.getString("nome"),
                            rs.getString("cnpj"),
                            rs.getString("cep"),
                            rs.getString("telefone")
                    );

                    empresas.add(empresaTemporaria);
                }

                // Retorna a lista de empresas filtradas
                return empresas;
            }

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao listar as empresas com filtro no banco de dados.",
                    e
            );
        }
    }

    // Método que busca uma empresa pelo seu ID
    public Empresa buscarporId(int id) {

        // Comando SQL para buscar uma empresa pelo ID
        String sql = "SELECT * FROM tb_empresa WHERE id_empresa=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            // Informa o ID utilizado na consulta
            pstmt.setInt(1, id);

            // Executa a consulta e verifica se encontrou alguma empresa
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    // Cria um objeto com os dados encontrados
                    Empresa empresa = new Empresa(
                            rs.getString("nome"),
                            rs.getString("cnpj"),
                            rs.getInt("id_endereco"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            null
                    );

                    empresa.setId(rs.getInt("id_empresa"));

                    return empresa;
                }
            }

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao buscar uma empresa pelo ID no banco de dados.",
                    e
            );
        }

        return null;
    }
}
