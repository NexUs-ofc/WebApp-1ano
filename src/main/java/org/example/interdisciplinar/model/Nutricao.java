package org.example.interdisciplinar.model;

public class Nutricao {

    // Atributos

    private int id;
    private String tabelaNutricional;

    // Construtores

    public Nutricao(){}

    public Nutricao(int id, String tabelaNutricional) {
        this.id = id;
        this.tabelaNutricional = tabelaNutricional;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTabelaNutricional() {
        return tabelaNutricional;
    }

    public void setTabelaNutricional(String tabelaNutricional) {
        this.tabelaNutricional = tabelaNutricional;
    }

    // toString()

    @Override
    public String toString() {
        return "Id: " + this.id + "\n" +
                "Tabela nutricional: " + this.tabelaNutricional;
    }
}
