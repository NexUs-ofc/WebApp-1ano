package org.example.interdisciplinar.model;

public class Empresa {

    // Atributos

    private int id;
    private String nome;
    private String cnpj;
    private int idEndereco;
    private String telefone;
    private String email;

    // Construtores

    public Empresa(){}

    public Empresa(String nome, String cnpj, int idEndereco, String telefone, String email) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.idEndereco = idEndereco;
        this.telefone = telefone;
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

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
        return "==========ALIMENTO========="+ "\n" +
                "Id: " + this.id + "\n" +
                "Nome: " + this.nome + "\n" +
                "Cnpj: " + this.cnpj + "\n" +
                "Id do endereço: " + this.idEndereco + "\n" +
                "Telefone: " + this.telefone + "\n" +
                "Email: " + this.email + "\n" +
                "============================";
    }
}
