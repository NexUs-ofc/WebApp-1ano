package org.example.interdisciplinar.model;

public class Endereco {

    // Atributos

    private int id;
    private String rua;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    // Construtores

    public Endereco() {}

    public Endereco(int id, String rua, String endereco, String bairro, String cidade, String estado, String cep) {
        this.id = id;
        this.rua = rua;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    // toString

    @Override
    public String toString() {
        return "Id do endereço: " + this.id + "\n" +
                "Rua: " + this.rua + "\n" +
                "Endereco: " + this.endereco + "\n" +
                "Bairro: " + this.bairro + "\n" +
                "Cidade: " + this.cidade + "\n" +
                "Estado: " + this.estado + "\n" +
                "Cep: " + this.cep;
    }
}
