package org.example.interdisciplinar.model;

public class RestricaoAlimentar {

    // Atributos

    private int id;
    private String nome;

    // Construtores

    public RestricaoAlimentar(){}

    public RestricaoAlimentar(int id, String nome) {
        this.id = id;
        this.nome = nome;
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

    // toString()

    @Override
    public String toString() {
        return "Id: " + this.id + "\n" +
                "Nome: " + this.nome;
    }
}
