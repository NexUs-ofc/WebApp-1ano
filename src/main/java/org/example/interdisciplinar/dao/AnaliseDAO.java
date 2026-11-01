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
}
