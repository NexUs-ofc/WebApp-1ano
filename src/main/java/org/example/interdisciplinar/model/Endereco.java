package org.example.interdisciplinar.model;

public class Endereco {

    // Atributos

    private int id;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    // Construtores

    public Endereco(){

    }

    public Endereco(int id, String rua, String numero, String bairro, String cidade, String estado, String cep) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    // Getters e setters

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    // Método toString()

    @Override
    public String toString() {
        return "==========ENDEREÇO==========" +
                "Id: " + this.id + "\n" +
                "Rua: " + this.rua + "\n" +
                "Número da casa: " + this.numero + "\n" +
                "Bairro: " + this.bairro + "\n" +
                "Cidade: " + this.cidade + "\n" +
                "Estado: " + this.estado + "\n" +
                "Cep: " + this.cep + "\n" +
                "=============================";
    }
}
