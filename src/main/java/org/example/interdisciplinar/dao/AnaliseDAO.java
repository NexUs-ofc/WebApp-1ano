package org.example.interdisciplinar.dao;

// Importa as bibliotecas e classes necessárias para trabalhar com banco de dados, listas e análises
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dto.CadastroMensal;
import org.example.interdisciplinar.dto.CategoriaPopular;
import org.example.interdisciplinar.dto.EmpresaRecente;
import org.example.interdisciplinar.exception.ErroPersistenciaException;

// Classe responsável pelas consultas de análise do sistema
public class AnaliseDAO {

    // Método que busca a quantidade de cadastros de usuários realizados em cada mês do ano atual
    public List<CadastroMensal> buscarCadastrosPorMes() {

        String sql = """
            SELECT
                EXTRACT(MONTH FROM data_criacao) AS mes,
                COUNT(*) AS total
            FROM tb_usuario
            WHERE EXTRACT(YEAR FROM data_criacao) =
                  EXTRACT(YEAR FROM CURRENT_DATE)
            GROUP BY EXTRACT(MONTH FROM data_criacao)
            ORDER BY mes
        """;

        // Lista que vai armazenar os cadastros encontrados
        List<CadastroMensal> cadastros = new ArrayList<>();

        // Abre a conexão e executa a consulta
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            // Percorre os meses encontrados
            while (rs.next()) {

                int mes = rs.getInt("mes");
                int total = rs.getInt("total");

                // Cria um objeto com os dados do mês
                CadastroMensal cadastro = new CadastroMensal(mes, total);

                cadastros.add(cadastro);
            }

            // Retorna a lista de cadastros mensais
            return cadastros;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao listar o cadastro mensal de usuários no banco de dados.",
                    e
            );
        }
    }

    // Método que conta a quantidade de administradores cadastrados
    public int contarAdministradores(){

        String sql = "SELECT COUNT(*) FROM tb_administrador";

        // Abre a conexão e executa a consulta
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            // Retorna a quantidade encontrada
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao contar o número de administradores no banco de dados.",
                    e
            );
        }
    }

    // Método que conta a quantidade total de itens armazenados nas despensas
    public int contarTotalItens() {

        String sql = "SELECT COALESCE(SUM(quantidade), 0) FROM tb_item_despensa";

        // Abre a conexão e executa a consulta
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            // Retorna a soma das quantidades
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao contar o total de itens no banco de dados.",
                    e
            );
        }
    }

    // Método que conta a quantidade de itens com validade nos próximos sete dias
    public int contarItensProximosVencimento() {

        String sql = "SELECT COALESCE(SUM(quantidade), 0) FROM tb_item_despensa WHERE data_validade BETWEEN CURRENT_DATE AND CURRENT_DATE + 7";

        // Abre a conexão e executa a consulta
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            // Retorna a soma das quantidades encontradas
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao contar os itens próximos do vencimento no banco de dados.",
                    e
            );
        }
    }

    // Método que conta a quantidade de usuários cadastrados
    public int contarUsuarios() {

        String sql = "SELECT COUNT(*) FROM tb_usuario";

        // Abre a conexão e executa a consulta
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            // Retorna a quantidade encontrada
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao contar o número de usuários no banco de dados.",
                    e
            );
        }
    }

    // Método que conta a quantidade de empresas cadastradas
    public int contarEmpresas() {

        String sql = "SELECT COUNT(*) FROM tb_empresa";

        // Abre a conexão e executa a consulta
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            // Retorna a quantidade encontrada
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao contar o número de empresas no banco de dados.",
                    e
            );
        }
    }

    // Método que busca a categoria com maior quantidade de itens nas despensas
    public CategoriaPopular buscarCategoriaMaisPopular() {

        String sql = """
            SELECT c.nome, SUM(i.quantidade) AS total
            FROM tb_item_despensa i
            JOIN tb_alimento a ON i.id_alimento = a.id_alimento
            JOIN tb_categoria c ON a.id_categoria = c.id_categoria
            GROUP BY c.id_categoria, c.nome
            ORDER BY total DESC
            LIMIT 1
        """;

        // Abre a conexão e executa a consulta
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            // Verifica se encontrou alguma categoria
            if (rs.next()) {
                return new CategoriaPopular(
                        rs.getString("nome"),
                        rs.getInt("total")
                );
            }

            return null;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao buscar a categoria mais popular no banco de dados.",
                    e
            );
        }
    }

    // Método que busca a empresa cadastrada mais recentemente
    public EmpresaRecente buscarEmpresaMaisRecente() {

        String sql = """
            SELECT nome FROM tb_empresa
            ORDER BY id_empresa DESC
            LIMIT 1
        """;

        // Abre a conexão e executa a consulta
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            // Verifica se encontrou alguma empresa
            if (rs.next()) {
                return new EmpresaRecente(
                        rs.getString("nome"),
                        null
                );
            }

            return null;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao buscar a empresa mais recente no banco de dados.",
                    e
            );
        }
    }
}