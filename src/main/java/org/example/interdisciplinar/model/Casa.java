package org.example.interdisciplinar.model;

public class Casa {

    // Atributos

    private int id;
    private String nome;
    private int idUsuario;
    private int idEndereco;

    // Construtores

    public Casa(){
    }

    public Casa(int id, String nome, int idUsuario, int idEndereco) {
        this.id = id;
        this.nome = nome;
        this.idUsuario = idUsuario;
        this.idEndereco = idEndereco;
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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    // Método toString()

    @Override
    public String toString() {
        return "==========CASA==========" +
                "Id: " + id + "\n" +
                "Nome: " + nome + "\n" +
                "Id do usuário: " + idUsuario + "\n" +
                ", idEndereco=" + idEndereco + "\n" +
                "========================";
    }
}
