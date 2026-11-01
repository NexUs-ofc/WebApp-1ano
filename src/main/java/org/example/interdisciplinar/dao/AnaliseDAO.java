package org.example.interdisciplinar.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dto.CadastroMensal;


public class AnaliseDAO {

    public List<CadastroMensal> buscarCadastrosPorMes() {

        String sql = """
        SELECT
            EXTRACT(MONTH FROM data_cadastro) AS mes,
            COUNT(*) AS total
        FROM tb_usuario
        WHERE EXTRACT(YEAR FROM data_cadastro) =
              EXTRACT(YEAR FROM CURRENT_DATE)
        GROUP BY EXTRACT(MONTH FROM data_cadastro)
        ORDER BY mes
    """;

        List<CadastroMensal> cadastros = new ArrayList<>();

        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            while (rs.next()) {

                int mes = rs.getInt("mes");
                int total = rs.getInt("total");

                CadastroMensal cadastro = new CadastroMensal(mes, total);

                cadastros.add(cadastro);
            }

            return cadastros;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cadastros por mês.", e);
        }
    }

    public int contarAdministradores(){;
        String sql = "SELECT COUNT(*) FROM tb_administrador";

        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar administradores.", e);
        }
    }

    public int contarTotalItens() {

        String sql = "SELECT COALESCE(SUM(quantidade), 0) FROM tb_item_despensa";

        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao contar o total de itens.", e
            );
        }
    }

    public int contarItensProximosVencimento() {

        String sql = "SELECT coalesce(sum(quantidade), 0) FROM tb_item_despensa WHERE validade BETWEEN current_date AND current_date + 7";

        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao contar itens próximos do vencimento.", e
            );
        }
    }

    public int contarUsuarios() {
        String sql = "SELECT count(*) FROM tb_usuario";

        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar usuários.", e);
        }
    }

    public int contarEmpresas() {
        String sql = "SELECT count(*) FROM tb_empresa";

        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar empresas.", e);
        }
    }
}
