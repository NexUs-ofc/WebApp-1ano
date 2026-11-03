package org.example.interdisciplinar.dao;

// Importa as bibliotecas e classes necessárias para trabalhar com banco de dados, listas e administradores
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.exception.ErroPersistenciaException;
import org.example.interdisciplinar.model.Administrador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Classe responsável pelas operações dos administradores no banco de dados
public class AdministradorDAO {

    // Método que cadastra um administrador no banco de dados
    public boolean inserir(Administrador administrador){

        // Comando SQL para cadastrar um administrador
        String sqlInsert = "INSERT INTO tb_administrador(nome, email, senha, telefone, url_foto) VALUES (?, ?, ?, ?, ?)";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlInsert)
        ){

            // Preenche os parâmetros do comando SQL
            pstmt.setString(1, administrador.getNome());
            pstmt.setString(2, administrador.getEmail());
            pstmt.setString(3, administrador.getSenha());
            pstmt.setString(4, administrador.getTelefone());
            pstmt.setString(5, administrador.getUrlFoto());

            // Executa o cadastro e verifica se algum registro foi inserido
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao inserir os administradores no banco de dados.",
                    e
            );
        }
    }

    // Método que lista todos os administradores cadastrados
    public List<Administrador> listar(){

        // Comando SQL para consultar todos os administradores
        String sqlSelect = "SELECT * FROM tb_administrador";

        // Lista que vai armazenar os administradores encontrados
        List<Administrador> administradores = new ArrayList<>();

        // Abre a conexão e executa a consulta
        try (
                Connection conn = GerenteConexao.conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlSelect)
        ){

            // Percorre todos os registros encontrados
            while(rs.next()){
                Administrador admTemporario = new Administrador();
                admTemporario.setId(rs.getInt("id_administrador"));
                admTemporario.setNome(rs.getString("nome"));
                admTemporario.setEmail(rs.getString("email"));
                admTemporario.setSenha(rs.getString("senha"));
                admTemporario.setTelefone(rs.getString("telefone"));
                admTemporario.setUrlFoto(rs.getString("url_foto"));
                administradores.add(admTemporario);
            }

            // Retorna a lista preenchida
            return administradores;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao listar os administradores no banco de dados.",
                    e
            );
        }
    }

    // Método que atualiza os dados de um administrador
    public boolean atualizar(Administrador administrador){

        // Comando SQL para atualizar um administrador pelo ID
        String sql = "UPDATE tb_administrador SET nome=?, email=?, senha=?, telefone=?, url_foto=? WHERE id_administrador=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ){

            // Preenche os parâmetros do comando SQL
            pstmt.setString(1, administrador.getNome());
            pstmt.setString(2, administrador.getEmail());
            pstmt.setString(3, administrador.getSenha());
            pstmt.setString(4, administrador.getTelefone());
            pstmt.setString(5, administrador.getUrlFoto());
            pstmt.setInt(6, administrador.getId());

            // Executa a atualização e verifica se algum registro foi alterado
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao atualizar o administrador no banco de dados.",
                    e
            );
        }
    }

    // Método que deleta um administrador pelo seu ID
    public boolean deletar(int id){

        // Comando SQL para excluir um administrador
        String sqlDelete = "DELETE FROM tb_administrador WHERE id_administrador=?";

        // Abre a conexão e prepara o comando SQL
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sqlDelete)
        ){

            // Informa o ID do administrador que será excluído
            pstmt.setInt(1, id);

            // Executa a exclusão e verifica se algum registro foi removido
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao deletar o administrador no banco de dados.",
                    e
            );
        }
    }

    // Método que lista administradores de acordo com os filtros informados
    public List<Administrador> listarFiltrado(
            Integer idAdminInicio,
            Integer idAdminFim,
            String nome,
            String email,
            String ordenar
    ){

        // Monta a consulta SQL conforme os filtros recebidos
        StringBuilder sql = new StringBuilder("SELECT * FROM tb_administrador WHERE 1=1");

        // Lista que armazena os valores utilizados nos filtros
        List<Object> parametrosDoFiltro = new ArrayList<>();

        // Lista que vai armazenar os administradores encontrados
        List<Administrador> administradores = new ArrayList<>();

        // Filtra pelo ID inicial, caso tenha sido informado
        if (idAdminInicio != null) {
            sql.append(" AND id_administrador >=?");
            parametrosDoFiltro.add(idAdminInicio);
        }

        // Filtra pelo ID final, caso tenha sido informado
        if (idAdminFim != null) {
            sql.append(" AND id_administrador <=?");
            parametrosDoFiltro.add(idAdminFim);
        }

        // Filtra pelo nome, caso tenha sido informado
        if (nome != null && !nome.isBlank()) {
            sql.append(" AND nome ILIKE ?");
            parametrosDoFiltro.add("%" + nome + "%");
        }

        // Filtra pelo email, caso tenha sido informado
        if (email != null && !email.isBlank()) {
            sql.append(" AND email ILIKE ?");
            parametrosDoFiltro.add("%" + email + "%");
        }

        // Define a ordenação dos resultados
        switch (ordenar == null ? "" : ordenar) {
            case "id":
                sql.append(" ORDER BY id_administrador");
                break;

            case "nome":
                sql.append(" ORDER BY nome");
                break;

            case "email":
                sql.append(" ORDER BY email");
                break;

            default:
                sql.append(" ORDER BY id_administrador");
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

                // Cria um objeto para cada administrador encontrado
                while(rs.next()){
                    Administrador admTemporario = new Administrador();
                    admTemporario.setId(rs.getInt("id_administrador"));
                    admTemporario.setNome(rs.getString("nome"));
                    admTemporario.setEmail(rs.getString("email"));
                    admTemporario.setSenha(rs.getString("senha"));
                    admTemporario.setTelefone(rs.getString("telefone"));
                    admTemporario.setUrlFoto(rs.getString("url_foto"));
                    administradores.add(admTemporario);
                }

                // Retorna a lista de administradores filtrados
                return administradores;
            }

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao listar os administradores com filtro no banco de dados.",
                    e
            );
        }
    }

    // Método que busca um administrador pelo seu ID
    public Administrador buscarporId(int id){

        // Comando SQL para buscar um administrador pelo ID
        String sql = "SELECT * FROM tb_administrador WHERE id_administrador=?";

        // Abre a conexão e prepara o comando SQL
        try(
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ){

            // Informa o ID utilizado na consulta
            pstmt.setInt(1, id);

            // Executa a consulta e verifica se encontrou algum administrador
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){

                    // Cria um objeto com os dados encontrados
                    Administrador administrador = new Administrador();
                    administrador.setId(rs.getInt("id_administrador"));
                    administrador.setNome(rs.getString("nome"));
                    administrador.setEmail(rs.getString("email"));
                    administrador.setSenha(rs.getString("senha"));
                    administrador.setTelefone(rs.getString("telefone"));
                    administrador.setUrlFoto(rs.getString("url_foto"));

                    return administrador;
                }
            }

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao buscar um administrador pelo ID no banco de dados.",
                    e
            );
        }

        return null;
    }

    // Método que busca um administrador pelo seu email
    public Administrador buscarPorEmail(String email){

        // Comando SQL para buscar um administrador pelo email
        String sql = "SELECT * FROM tb_administrador WHERE email=?";

        // Abre a conexão e prepara o comando SQL
        try(
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ){

            // Informa o email utilizado na consulta
            pstmt.setString(1, email);

            // Executa a consulta e verifica se encontrou algum administrador
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){

                    // Cria um objeto com os dados encontrados
                    Administrador administrador = new Administrador();
                    administrador.setId(rs.getInt("id_administrador"));
                    administrador.setNome(rs.getString("nome"));
                    administrador.setEmail(rs.getString("email"));
                    administrador.setSenha(rs.getString("senha"));
                    administrador.setTelefone(rs.getString("telefone"));
                    administrador.setUrlFoto(rs.getString("url_foto"));

                    return administrador;
                }
            }

        } catch (SQLException e) {

            // Lança uma exceção personalizada caso ocorra um erro no banco
            throw new ErroPersistenciaException(
                    "Erro ao buscar um administrador pelo email no banco de dados.",
                    e
            );
        }

        return null;
    }
}