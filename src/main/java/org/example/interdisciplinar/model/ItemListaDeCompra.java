package org.example.interdisciplinar.model;

import java.util.ArrayList;
import java.util.List;

public class ItemListaDeCompra {

    // Atributos

    private int id;
    private List<Alimento> itens = new ArrayList<>();
    private int quantidade;

    // Construtores

    public ItemListaDeCompra(){}

    public ItemListaDeCompra(int id, List<Alimento> itens, int quantidade) {
        this.id = id;
        this.itens = itens;
        this.quantidade = quantidade;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Alimento> getItens() {
        return itens;
    }

    public void setItens(List<Alimento> itens) {
        this.itens = itens;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Id: " + this.id + "\n" +
                "Itens: " + this.itens + "\n" +
                "Quantidade: " + this.quantidade;
    }
}
