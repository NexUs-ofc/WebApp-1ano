package org.example.interdisciplinar.model;

public class ListaDeCompra {

    // Atributos

    private int id;
    private String nome;
    private String dataCriacao;

    // Construtores

    public ListaDeCompra(){}

    public ListaDeCompra(int id, String nome, String dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
    }

    // Getters e Setters


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

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    // toString()


    @Override
    public String toString() {
        return "Id: " + this.id + "\n" +
                "Nome: " + this.nome + "\n" +
                "Data de Criação: " + this.dataCriacao;
    }
}
