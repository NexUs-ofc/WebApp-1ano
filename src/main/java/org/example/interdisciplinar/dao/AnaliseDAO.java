package org.example.interdisciplinar.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dto.CadastroMensal;
import org.example.interdisciplinar.dto.CategoriaPopular;
import org.example.interdisciplinar.dto.EmpresaRecente;
import org.example.interdisciplinar.exception.ErroPersistenciaException;


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

            // Transforma a SQLException em uma exceção personalizada, preservando a causa original do erro.
            throw new ErroPersistenciaException(
                    "Erro ao listar o cadastro mensal de empresas no banco de dados.",
                    e
            );
        }
    }

    public int contarAdministradores(){
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

            // Transforma a SQLException em uma exceção personalizada, preservando a causa original do erro.
            throw new ErroPersistenciaException(
                    "Erro ao contar o número de administradores no banco de dados.",
                    e
            );
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

            // Transforma a SQLException em uma exceção personalizada, preservando a causa original do erro.
            throw new ErroPersistenciaException(
                    "Erro ao contar o total de itens no banco de dados.",
                    e
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

            // Transforma a SQLException em uma exceção personalizada, preservando a causa original do erro.
            throw new ErroPersistenciaException(
                    "Erro ao contar os itens próximo ao vencimento no banco de dados.",
                    e
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

            // Transforma a SQLException em uma exceção personalizada, preservando a causa original do erro.
            throw new ErroPersistenciaException(
                    "Erro ao contar o número de usuários no banco de dados.",
                    e
            );
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

            // Transforma a SQLException em uma exceção personalizada, preservando a causa original do erro.
            throw new ErroPersistenciaException(
                    "Erro ao contar o número de empresas no banco de dados.",
                    e
            );
        }
    }

    public CategoriaPopular buscarCategoriaMaisPopular() {

        String sql = """
        SELECT c.nome, sum(i.quantidade) AS total
        FROM tb_item_despensa i join tb_alimento a on i.id_alimento = a.id
        join tb_categoria c on a.id_categoria = c.id
        GROUP BY c.id, c.nome ORDER BY total DESC limit 1
    """;

        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            if (rs.next()) {
                return new CategoriaPopular(
                        rs.getString("nome"),
                        rs.getInt("total")
                );
            }

            return null;

        } catch (SQLException e) {

            // Transforma a SQLException em uma exceção personalizada, preservando a causa original do erro.
            throw new ErroPersistenciaException(
                    "Erro ao buscar a categoria mais popular no banco de dados.",
                    e
            );
        }
    }

    public EmpresaRecente buscarEmpresaMaisRecente() {

        String sql = """
        SELECT nome, data_cadastro FROM tb_empresa
        ORDER BY data_cadastro DESC, id DESC limit 1
    """;

        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            if (rs.next()) {
                return new EmpresaRecente(
                        rs.getString("nome"),
                        rs.getDate("data_cadastro").toLocalDate()
                );
            }

            return null;

        } catch (SQLException e) {

            // Transforma a SQLException em uma exceção personalizada, preservando a causa original do erro.
            throw new ErroPersistenciaException(
                    "Erro ao buscar a empresa mais recente no banco de dados.",
                    e
            );
        }
    }
}
