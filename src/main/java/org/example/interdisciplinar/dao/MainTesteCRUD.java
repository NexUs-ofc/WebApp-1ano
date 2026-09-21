package org.example.interdisciplinar.dao;

import org.example.interdisciplinar.conexao.GerenteConexao;
import org.example.interdisciplinar.dao.*;
import org.example.interdisciplinar.model.*;
import java.util.*;

import java.sql.Connection;
import java.time.LocalDate;

public class MainTesteCRUD {
    public static void main(String[] args) {
        Connection conn = GerenteConexao.conectar();

        try {
//            CasaDAO casaDAO = new CasaDAO();
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            AlimentoDAO alimentoDAO = new AlimentoDAO();
            ItemDispensaDAO itemDispensaDAO = new ItemDispensaDAO();

            Categoria categoria = new Categoria("Laticínios", "Derivados de leite");

            Endereco endereco = new Endereco("Rua Garapas", "44", "Centro", "Cajamar", "SP", "00000-000");

            Usuario usuario = new Usuario("Maria Silva", "maria@email.com", "Sem lactose", "SMS");

//            Casa casa = new Casa("Casa do Victor Schiavon", 1, 1);

            Alimento alimento = new Alimento("7777 5678 345678 7890", "Norgets", "Seara", 1);

            ItemDispensa itemDispensa = new ItemDispensa(1, 1, 2, LocalDate.of(2026, 12, 10));

            System.out.println(categoriaDAO.insert(categoria));
            System.out.println(enderecoDAO.insert(endereco));
//            System.out.println(casaDAO.inserir(casa));

            List<Alimento> alimentos = alimentoDAO.select();
            for (Alimento alimento1 : alimentos) {
                System.out.println(alimento1);
            }
            Alimento updalimento = alimentos.get(0);
            updalimento.setNome("Miojo");
            updalimento.setCodigoBarras("1243 0970 0973 0872");
            updalimento.setMarca("Sadia");
            System.out.println("Id: " + updalimento.getId());
            System.out.println("Codigo: " + alimentoDAO.updateCodigo(updalimento));
            System.out.println("Marca: " + alimentoDAO.updateMarca(updalimento));
            System.out.println("Nome: " + alimentoDAO.updateNome(updalimento));
            List<Alimento> alimentosDepoisUpdate = alimentoDAO.select();
            for (Alimento alimento1 : alimentosDepoisUpdate) {
                System.out.println(alimento1);
            }
            Alimento delAlimento = alimentosDepoisUpdate.get(alimentosDepoisUpdate.size() - 1);
            System.out.println("id: " + delAlimento.getId());
            System.out.println("delete: " + alimentoDAO.delete(delAlimento));
            for (Alimento alimento1 : alimentoDAO.select()) {
                System.out.println(alimento1);
            }
            List<Categoria> categorias = categoriaDAO.select();
            for (Categoria categoria1 : categorias) {
                System.out.println(categoria1);
            }
            Categoria updCategoria = categorias.get(0);
            updCategoria.setNome("upd laticinio");
            updCategoria.setDescricao("Descrição atualizada");
            System.out.println("upd categoria id: " + updCategoria.getId());
            System.out.println("updateName: " + categoriaDAO.updateName(updCategoria));
            System.out.println("updateDescription: " + categoriaDAO.updateDescription(updCategoria));
            List<Categoria> categoriasDepoisUpdate = categoriaDAO.select();
            for (Categoria categoria1 : categoriasDepoisUpdate) {
                System.out.println(categoria1);
            }
            Categoria delCategoria = categoriasDepoisUpdate.get(categoriasDepoisUpdate.size() - 1);
            System.out.println("delete no categoria id" + delCategoria.getId());
            System.out.println("delete: " + categoriaDAO.delete(delCategoria));
            for (Categoria categoria1 : categoriaDAO.select()) {
                System.out.println(categoria1);
            }
//            List<Casa> casas = casaDAO.select();
//            for (Casa casa1 : casas) {
//                System.out.println(casa1);
//            }
//            Casa updCasa = casas.get(0);
//            updCasa.setNome("Casa do ruanito batatex");
//            System.out.println("id: " + updCasa.getId());
//            System.out.println("Nome: " + casaDAO.updateNome(updCasa));
//            List<Casa> casasDepoisUpdate = casaDAO.select();
//            for (Casa casa1 : casasDepoisUpdate) {
//                System.out.println(casa1);
//            }
//            Casa delCasa = casasDepoisUpdate.get(casasDepoisUpdate.size() - 1);
//            System.out.println("id: " + delCasa.getId());
//            System.out.println("delete: " + casaDAO.delete(delCasa));
//            for (Casa casa1 : casaDAO.select()) {
//                System.out.println(casa1);
//            }
            List<Endereco> enderecos = enderecoDAO.select();
            for (Endereco endereco1 : enderecos) {
                System.out.println(endereco1);
            }
            Endereco updEndereco = enderecos.get(0);
            updEndereco.setRua("Rua bla bla");
            updEndereco.setNumero("505");
            updEndereco.setBairro("Vila");
            updEndereco.setCidade("Osasco");
            updEndereco.setEstado("SP");
            updEndereco.setCep("06012-123");
            System.out.println("id: " + updEndereco.getId());
            System.out.println("Rua: " + enderecoDAO.updateRua(updEndereco));
            System.out.println("Numero: " + enderecoDAO.updateNumero(updEndereco));
            System.out.println("Bairro: " + enderecoDAO.updateBairro(updEndereco));
            System.out.println("Cidade: " + enderecoDAO.updateCidade(updEndereco));
            System.out.println("Estado: " + enderecoDAO.updateEstado(updEndereco));
            System.out.println("Cep: " + enderecoDAO.updateCep(updEndereco));
            List<Endereco> enderecosDepoisUpdate = enderecoDAO.select();
            for (Endereco endereco1 : enderecosDepoisUpdate) {
                System.out.println(endereco1);
            }
            Endereco delEndereco = enderecosDepoisUpdate.get(enderecosDepoisUpdate.size() - 1);
            System.out.println("delete no endereço id" + delEndereco.getId());
            System.out.println("delete: " + enderecoDAO.delete(delEndereco));
            for (Endereco endereco1 : enderecoDAO.select()) {
                System.out.println(endereco1);
            }
            List<ItemDispensa> itemDispensas = itemDispensaDAO.select();
            for (ItemDispensa itemDispensa1 : itemDispensas) {
                System.out.println(itemDispensa1);
            }
            ItemDispensa updItemDispensa = itemDispensas.get(0);
            updItemDispensa.setQuantidade(updItemDispensa.getQuantidade() + 1);
            updItemDispensa.setValidade(LocalDate.now().plusDays(30));
            System.out.println("id: " + updItemDispensa.getId());
            System.out.println("Quantidade: " + itemDispensaDAO.updateQuantidade(updItemDispensa));
            System.out.println("Validade: " + itemDispensaDAO.updateValidade(updItemDispensa));
            List<ItemDispensa> itemDispensasDepoisUpdate = itemDispensaDAO.select();
            for (ItemDispensa itemDispensa1 : itemDispensasDepoisUpdate) {
                System.out.println(itemDispensa1);
            }
            ItemDispensa delItemDispensa = itemDispensasDepoisUpdate.get(itemDispensasDepoisUpdate.size() - 1);
            System.out.println("id: " + delItemDispensa.getId());
            System.out.println("delete: " + itemDispensaDAO.delete(delItemDispensa));
            for (ItemDispensa itemDispensa1 : itemDispensaDAO.select()) {
                System.out.println(itemDispensa1);
            }
        }
        finally {
            GerenteConexao.desconectar();
        }
    }
}