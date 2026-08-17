package org.example.interdisciplinar.model;

public class Produto {

    // Atributos

    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;
    private int idCategoria;

    // Construtor

    public Produto(int id, String nome, String descricao, double preco, int estoque, int idCategoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.idCategoria = idCategoria;
    }

    // Getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    // Método toString()

    @Override
    public String toString() {
        return "==========PRODUTO==========" +
                "Id: " + id + "\n" +
                "Nome: " + nome + "\n" +
                "Descrição: " + descricao + "\n" +
                "Preço: " + preco + "\n" +
                "Estoque: " + estoque + "\n" +
                "Id da categoria: " + idCategoria + "\n" +
                "===========================";
    }
}
