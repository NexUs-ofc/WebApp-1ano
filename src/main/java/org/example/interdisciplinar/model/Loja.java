package org.example.interdisciplinar.model;

public class Loja {

    // Atributos

    private int id;
    private String nome;
    private String endereco;
    private String telefone;

    // Construtor

    public Loja(int id, String nome, String endereco, String telefone) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Método toString()

    @Override
    public String toString() {
        return "==========LOJA==========" + "\n" +
                "Id: " + id + "\n" +
                "Nome: " + nome + "\n" +
                "Endereço: " + endereco + "\n" +
                "Telefone" + telefone + "\n" +
                "========================";
    }
}
