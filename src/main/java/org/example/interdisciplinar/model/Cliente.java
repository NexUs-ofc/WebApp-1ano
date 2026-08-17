package org.example.interdisciplinar.model;

public class Cliente {

    // Atributos

    private int id;
    private String nome;
    private int cpf;
    private String endereco;
    private String telefone;
    private String email;

    // Construtor

    public Cliente(int id, String nome, int cpf, String endereco, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
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

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Método toString()

    @Override
    public String toString() {
        return "==========CLIENTE==========" +
                "Id: " + id + "\n" +
                "Nome: " + nome + "\n" +
                "Cpf: " + cpf + "\n" +
                "Endereço: " + endereco + "\n" +
                "Telefone: " + telefone + "\n" +
                "Email: " + email + "\n" +
                "===========================";
    }
}
