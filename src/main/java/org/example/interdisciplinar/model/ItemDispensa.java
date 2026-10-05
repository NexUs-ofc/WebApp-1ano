package org.example.interdisciplinar.model;

import java.time.LocalDate;

public class ItemDispensa {

    // Atributos

    private int id;
    private int idCasa;
    private int idAlimento;
    private int quantidade;
    private LocalDate validade;

    // Construtores

    public ItemDispensa(){

    }

    public ItemDispensa(int idCasa, int idAlimento, int quantidade, LocalDate validade) {

        this.idCasa = idCasa;
        this.idAlimento = idAlimento;
        this.quantidade = quantidade;
        this.validade = validade;
    }

    // Getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCasa() {
        return idCasa;
    }

    public void setIdCasa(int idCasa) {
        this.idCasa = idCasa;
    }

    public int getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    // Método toString()

    @Override
    public String toString() {
        return "==========ITEM DISPENSA==========" + "\n" +
                "Id: " + this.id + "\n" +
                "Id da casa: " + this.idCasa+ "\n" +
                "Id do alimento: " + this.idAlimento + "\n" +
                "Quantidade: " + this.quantidade + "\n" +
                "Validade: " + this.validade +
                "================================";
    }
}
