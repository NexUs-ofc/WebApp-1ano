package org.example.interdisciplinar.model;

public class Fornecedor {

    // Atributos

    private int id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String endereco;
    private String email;

    // Construtor

    public Fornecedor(int id, String nome, String cnpj, String telefone, String endereco, String email) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
    }

    // Getters e setters

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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Método toString()

    @Override
    public String toString() {
        return "==========FORNECEDOR==========" +
                "Id: " + id + "\n" +
                "Nome: " + nome + "\n" +
                "Cnpj: " + cnpj + "\n" +
                "Telefone: " + telefone + "\n" +
                "Endereço: " + endereco + "\n" +
                "Email: " + email + "\n" +
                "==============================";
    }
}
