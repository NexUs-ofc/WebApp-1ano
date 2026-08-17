package org.example.interdisciplinar.model;

public class ItemVenda {

    // Atributos

    private int id;
    private int idVenda;
    private int idProduto;

    // Construtor

    public ItemVenda(int id, int idVenda, int idProduto) {
        this.id = id;
        this.idVenda = idVenda;
        this.idProduto = idProduto;
    }

    // Getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    // Método toString()

    @Override
    public String toString() {
        return "==========ITEM VENDA==========" +
                "Id: " + id + "\n" +
                "Id da venda: " + idVenda + "\n" +
                "Id do produto: " + idProduto + "\n" +
                "==============================";
    }
}
