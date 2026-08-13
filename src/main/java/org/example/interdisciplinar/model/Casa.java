package org.example.interdisciplinar.model;

import java.util.ArrayList;
import java.util.List;

public class Casa {

    // Atributos

    private int id;
    private String nome;
    private List<Telefone> telefones = new ArrayList<>();


    // Construtores
    public Casa(){}

    public Casa(int id, String nome, List<Telefone> telefones) {
        this.id = id;
        this.nome = nome;
        this.telefones = telefones;
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

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }


    // toString()

    @Override
    public String toString() {
        return "Id: " + this.id + "\n" +
                "Nome: " + this.nome + "\n" +
                "Telefones da casa" + this.telefones;
    }
}
