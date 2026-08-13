package org.example.interdisciplinar.model;

public class Alimento {

    // Atributos

    private int id;
    private String codigoDeBarras;
    private String nome;
    private String marca;

    // Construtores

    public Alimento(){}

    public Alimento(int id, String codigoDeBarras, String nome, String marca) {
        this.id = id;
        this.codigoDeBarras = codigoDeBarras;
        this.nome = nome;
        this.marca = marca;
    }

    // Getters e Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    // toString()


    @Override
    public String toString() {
        return "Id " + this.id + "\n" +
                "Código de Barras: " + this.codigoDeBarras + "\n" +
                "Nome: " + this.nome + "\n" +
                "Marca: " + this.marca;
    }
}
