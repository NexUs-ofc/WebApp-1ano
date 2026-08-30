package org.example.interdisciplinar.model;

public class Alimento {

    // Atributos

    private int id;
    private String codigoBarras;
    private String nome;
    private String marca;
    private int idCategoria;

    // Construtores

    public Alimento(){

    }

    public Alimento(String codigoBarras, String nome, String marca, int idCategoria) {
        this.codigoBarras = codigoBarras;
        this.nome = nome;
        this.marca = marca;
        this.idCategoria = idCategoria;
    }

    // Getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
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

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    // Método toString()

    @Override
    public String toString() {
        return "==========ALIMENTO==========" +
                "Id: " + this.id + "\n" +
                "Código de barras: " + this.codigoBarras + "\n" +
                "Nome: " + this.nome + "\n" +
                "Marca: " + this.marca + "\n" +
                "Categoria: " + this.idCategoria + "\n" +
                "============================";
    }
}
