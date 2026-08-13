package org.example.interdisciplinar.model;

public class ItemDispensa {

    // Atributos

    private int id;
    private int quantidade;
    private String dataValidade;

    // Construtores

    public ItemDispensa(){}

    public ItemDispensa(int id, int quantidade, String dataValidade) {
        this.id = id;
        this.quantidade = quantidade;
        this.dataValidade = dataValidade;
    }

    // Getters e Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }

    // toString()


    @Override
    public String toString() {
        return "Id: " + this.id + "\n" +
                "Quantidade: " + this.quantidade + "\n" +
                "Data de Validade" + this.dataValidade;
    }
}
