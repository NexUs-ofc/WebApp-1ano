package org.example.interdisciplinar.model;

public class IaReceita {

    // Atributos

    private int id;
    private String SugestaoReceita;
    private String tipo;

    // Construtores

    public IaReceita(){}

    public IaReceita(int id, String sugestaoReceita, String tipo) {
        this.id = id;
        SugestaoReceita = sugestaoReceita;
        this.tipo = tipo;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSugestaoReceita() {
        return SugestaoReceita;
    }

    public void setSugestaoReceita(String sugestaoReceita) {
        SugestaoReceita = sugestaoReceita;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // toString()


    @Override
    public String toString() {
        return "Id: " + id + "\n" +
                "Sugestão de receita: " + SugestaoReceita + "\n" +
                "Tipo: " + tipo;
    }

}
