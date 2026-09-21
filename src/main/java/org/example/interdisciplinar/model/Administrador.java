package org.example.interdisciplinar.model;

public class Administrador {

    // Atributos

    private int id;
    private String nome;
    private String email;
    private String senha;

    // Construtores

    public Administrador(int id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    public Administrador(){

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // Método toString()

    @Override
    public String toString() {
        return "==========ADMINISTRADOR==========" + "\n" +
                "Id: " + this.id + "\n" +
                "Nome: " + this.nome + "\n" +
                "Email: " + this.email + "\n" +
                "Senha: " + this.senha + "\n" +
                "=================================";
    }
}
