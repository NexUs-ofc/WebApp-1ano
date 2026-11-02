package org.example.interdisciplinar.dao;


import java.time.LocalDate;
import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dto.EmpresaView;
import org.example.interdisciplinar.model.Empresa;
import java.sql.*;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Abertura de classe
public class EmpresaDAO {

    // Método de inserir um alimento no banco de dados
    public boolean inserir(Empresa empresa) {

        //Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Comando de inserir em SQL
        String sqlInsert = "INSERT INTO tb_empresa (nome, cnpj, telefone, email, id_endereco) VALUES (?, ?, ?, ?, ?)";

        //Iniciando executor que: adicionará valores no comando SQL e executará o comando
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, empresa.getNome());
            pstmt.setString(2, empresa.getCnpj());
            pstmt.setString(3, empresa.getTelefone());
            pstmt.setString(4, empresa.getEmail());
            pstmt.setInt(5, empresa.getIdEndereco());

            // Executa o comando e recebe a quantidade de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Se ação tiver dados certo commita e retorna true
            if (linhasAfetadas > 0) {
                GerenteConexao.commit();
                return true;
            }

            //Se não desfaz a ação e retorna false
            GerenteConexao.rollback();
            return false;

            //Em casos de erros no banco de dados desfaz a ação
        } catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna false
            return false;
        }

    }

    // Método de deletar um alimento do banco de dados
    public boolean deletar(int id) {

        // Abrindo e armazenando conexão com o banco
        Connection conn = GerenteConexao.conectar();

        //Comando de deletar em SQL
        String sqlDelete = "DELETE FROM tb_empresa WHERE id_empresa=?";

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que vai adicionar valores no comando SQL e executará o comando
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {

            //Seta valor no '?' do comando SQL
            pstmt.setInt(1, id);

            //Se ação tiver dados certo commita e retorna true
            linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                GerenteConexao.commit();
                return true;
            }

            //Se não desfaz a ação e retorna false
            GerenteConexao.rollback();
            return false;

            //Em casos de erros no banco de dados desfaz a ação
        } catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            //Retorna false
            return false;
        }
    }

    // Método de listar todos os alimentos
    public List<EmpresaView> listar() {

        Connection conn = GerenteConexao.conectar();
        String sqlSelect = "SELECT * FROM view_empresa";
        List<EmpresaView> empresas = new ArrayList<>();

        //Iniciando executor que: adicionará valores no comando SQL e executará o comando
        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
            ResultSet rs = pstmt.executeQuery();

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while (rs.next()) {
                EmpresaView empresaTemporaria = new EmpresaView(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                );
                empresas.add(empresaTemporaria);
            }
            // Commita a ação no banco de dados
            GerenteConexao.commit();

            // Retrona a lista de administradores
            return empresas;

            //Em casos de erros no banco de dados desfaz a ação
        } catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            GerenteConexao.rollback();

            // Retorna a lista
            return empresas;
        }
    }

    public boolean atualizar(Empresa empresa) {
        String sql = "UPDATE tb_empresa SET nome=?, cnpj=?, telefone=?, email=?, id_endereco=? WHERE id_empresa=?";
        try (
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, empresa.getNome());
            pstmt.setString(2, empresa.getCnpj());
            pstmt.setString(3, empresa.getTelefone());
            pstmt.setString(4, empresa.getEmail());
            pstmt.setInt(5, empresa.getIdEndereco());
            pstmt.setInt(6, empresa.getId());
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }

    public List<EmpresaView> listarFiltrado(
            Integer idEmpresaInicio,
            Integer idEmpresaFim,
            String nome,
            String ordenar
    ) {
        StringBuilder sql = new StringBuilder("SELECT * FROM view_empresa WHERE 1=1");
        List<Object> parametrosDoFiltro = new ArrayList<>();
        List<EmpresaView> empresas = new ArrayList<>();

        if (idEmpresaInicio != null) {
            sql.append(" AND id_empresa >=?");
            parametrosDoFiltro.add(idEmpresaInicio);
        }
        if (idEmpresaFim != null) {
            sql.append(" AND id_empresa <=?");
            parametrosDoFiltro.add(idEmpresaFim);
        }
        if (nome != null && !nome.isBlank()) {
            sql.append(" AND nome ILIKE ?");
            parametrosDoFiltro.add("%" + nome + "%");
        }

        switch (ordenar == null ? "" : ordenar) {
            case "id_asc":
                sql.append(" ORDER BY id_empresa ASC");
                break;

            case "id_desc":
                sql.append(" ORDER BY id_empresa DESC");
                break;

            case "nome_asc":
                sql.append(" ORDER BY nome ASC");
                break;

            case "nome_desc":
                sql.append(" ORDER BY nome DESC");
                break;

            default:
                sql.append(" ORDER BY id_empresa ASC");
                break;
        }

        //Cria executor que receberá comando e executará ele
        try (Connection conn = GerenteConexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())
        ) {

            for (int i = 0; i < parametrosDoFiltro.size(); i++) {
                pstmt.setObject(i + 1, parametrosDoFiltro.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                //Enquanto tiver mais registros cria novos objetos e adiciona à lista

                while (rs.next()) {
                    EmpresaView empresaTemporaria = new EmpresaView(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
                    );
                    empresas.add(empresaTemporaria);
                }

                // Retrona a lista de administradores
                return empresas;
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return empresas;
        }
    }

    public Empresa buscarporId(int id){
        String sql = "SELECT * FROM tb_empresa WHERE id_empresa=?";
        try(
                Connection conn = GerenteConexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, id);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
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

        } catch (SQLException sqle){
            System.out.println("Erro de SQL: " + sqle.getMessage());
        }
        return null;
    }
}
