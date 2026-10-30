package org.example.interdisciplinar.model;

public class Categoria {

    // Atributos

    private int id;
    private String nome;
    private String descricao;

    // Construtores

    public Categoria(){

    }

    public Categoria(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
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

    // Método toString()

    @Override
    public String toString() {
        return "==========CATEGORIA==========" +
                "Id: " + this.id + "\n" +
                "Nome: " + this.nome + "\n" +
                "Descrição: " + this.descricao + "\n" +
                "============================";
    }
}
