package org.example.interdisciplinar.model;

public class Telefone {

    // Atributos

    private int id;
    private String numero;

    // Construtores
    public Telefone() {}

    public Telefone(int id, String numero) {
        this.id = id;
        this.numero = numero;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    // toString()

    @Override
    public String toString() {
        return "Id: " + this.id + "\n" +
                "Número: " + this.numero;
    }
}
