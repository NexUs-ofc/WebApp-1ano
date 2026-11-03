
package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dto.CadastroMensal;
import org.example.interdisciplinar.dto.CategoriaPopular;
import org.example.interdisciplinar.dto.EmpresaRecente;
import org.example.interdisciplinar.exception.ErroPersistenciaException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AnaliseDAO {

    // Total de empresas cadastradas em cada mês do ano atual.
    public List<CadastroMensal> buscarCadastrosPorMes() {

        String sql = """
                SELECT EXTRACT(MONTH FROM data_criacao)::int AS mes,
                       COUNT(*)::int AS total
                FROM tb_empresa
                WHERE data_criacao >= DATE_TRUNC('year', CURRENT_DATE)::date
                  AND data_criacao < (DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year')::date
                GROUP BY EXTRACT(MONTH FROM data_criacao)
                ORDER BY mes
                """;

        List<CadastroMensal> cadastros = new ArrayList<>();

        try (Connection conn = GerenteConexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                cadastros.add(new CadastroMensal(
                        rs.getInt("mes"),
                        rs.getInt("total")
                ));
            }

            return cadastros;

        } catch (SQLException e) {
            throw new ErroPersistenciaException(
                    "Erro ao buscar os cadastros mensais de empresas.", e);
        }
    }

    // Usuários cadastrados no mês atual.
    public int contarNovosCadastrosMes() {

        String sql = """
                SELECT COUNT(*) FROM tb_usuario
                WHERE data_criacao >= DATE_TRUNC('month', CURRENT_DATE)::date
                  AND data_criacao < (DATE_TRUNC('month', CURRENT_DATE) + INTERVAL '1 month')::date
                """;

        return contar(sql, "Erro ao contar os novos usuários do mês.");
    }

    // Usuários cadastrados no mês anterior.
    public int contarCadastrosMesAnterior() {

        String sql = """
                SELECT COUNT(*) FROM tb_usuario
                WHERE data_criacao >= (DATE_TRUNC('month', CURRENT_DATE) - INTERVAL '1 month')::date
                  AND data_criacao < DATE_TRUNC('month', CURRENT_DATE)::date
                """;

        return contar(sql, "Erro ao contar os usuários do mês anterior.");
    }

    public int contarAdministradores() {

        return contar(
                "SELECT COUNT(*) FROM tb_administrador",
                "Erro ao contar administradores."
        );
    }

    // Soma as unidades guardadas nas despensas, não apenas o número de linhas.
    public int contarTotalItens() {

        return contar(
                "SELECT COALESCE(SUM(quantidade), 0) FROM tb_item_despensa",
                "Erro ao contar as unidades das despensas."
        );
    }

    // Unidades com vencimento entre hoje e os próximos sete dias.
    public int contarItensProximosVencimento() {

        return contar("""
                SELECT COALESCE(SUM(quantidade), 0)
                FROM tb_item_despensa
                WHERE data_validade BETWEEN CURRENT_DATE AND CURRENT_DATE + 7
                """,
                "Erro ao contar os itens próximos do vencimento."
        );
    }

    public int contarUsuarios() {

        return contar(
                "SELECT COUNT(*) FROM tb_usuario",
                "Erro ao contar usuários."
        );
    }

    public int contarEmpresas() {

        return contar(
                "SELECT COUNT(*) FROM tb_empresa",
                "Erro ao contar empresas."
        );
    }

    // Empresas antigas que não possuem data de cadastro.
    public int contarEmpresasSemData() {

        return contar(
                "SELECT COUNT(*) FROM tb_empresa WHERE data_criacao IS NULL",
                "Erro ao contar empresas sem data de cadastro."
        );
    }

    public CategoriaPopular buscarCategoriaMaisPopular() {

        String sql = """
                SELECT c.nome, SUM(i.quantidade) AS total
                FROM tb_item_despensa i
                JOIN tb_alimento a ON a.id_alimento = i.id_alimento
                JOIN tb_categoria c ON c.id_categoria = a.id_categoria
                GROUP BY c.id_categoria, c.nome
                ORDER BY total DESC, c.nome
                LIMIT 1
                """;

        try (Connection conn = GerenteConexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {

                return new CategoriaPopular(
                        rs.getString("nome"),
                        rs.getInt("total")
                );
            }

            return null;

        } catch (SQLException e) {
            throw new ErroPersistenciaException(
                    "Erro ao buscar a categoria com mais itens.", e);
        }
    }

    public EmpresaRecente buscarEmpresaMaisRecente() {

        String sql = """
                SELECT nome, data_criacao
                FROM tb_empresa
                ORDER BY data_criacao DESC NULLS LAST, id_empresa DESC
                LIMIT 1
                """;

        try (Connection conn = GerenteConexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {

                Date dataCadastro = rs.getDate("data_criacao");

                return new EmpresaRecente(
                        rs.getString("nome"),
                        dataCadastro == null ? null : dataCadastro.toLocalDate()
                );
            }

            return null;

        } catch (SQLException e) {
            throw new ErroPersistenciaException(
                    "Erro ao buscar a empresa mais recente.", e);
        }
    }

    private int contar(String sql, String mensagem) {

        try (Connection conn = GerenteConexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            return rs.next() ? rs.getInt(1) : 0;

        } catch (SQLException e) {
            throw new ErroPersistenciaException(mensagem, e);
        }
    }
}
