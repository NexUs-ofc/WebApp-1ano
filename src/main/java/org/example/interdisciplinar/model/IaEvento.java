package org.example.interdisciplinar.model;

public class IaEvento {

    // Atributos

    private int id;
    private String auxiloEvento;

    // Construtores

    public IaEvento(){}

    public IaEvento(int id, String auxiloEvento) {
        this.id = id;
        this.auxiloEvento = auxiloEvento;
    }

    // Getters e Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuxiloEvento() {
        return auxiloEvento;
    }

    public void setAuxiloEvento(String auxiloEvento) {
        this.auxiloEvento = auxiloEvento;
    }

    // toString()


    @Override
    public String toString() {
        return "Id: " + this.id + "\n" +
                "Auxilio para o Evento: " + this.auxiloEvento;
    }
}
