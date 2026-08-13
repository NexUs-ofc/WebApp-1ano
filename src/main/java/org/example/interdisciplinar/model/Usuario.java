package org.example.interdisciplinar.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    // Atributos

    private int id;
    private String nome;
    private String email;
    private String preferenciasConsumo;
    private String configuracaoAlerta;
    private List<Telefone> telefones = new ArrayList<>();
    private List<RestricaoAlimentar> restricoes = new ArrayList<>();

    // Construtores

    public Usuario(){}

    public Usuario(int id, String nome, String email, String preferenciasConsumo, String configuracaoAlerta, List<Telefone> telefones, List<RestricaoAlimentar> restricoes) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.preferenciasConsumo = preferenciasConsumo;
        this.configuracaoAlerta = configuracaoAlerta;
        this.telefones = telefones;
        this.restricoes = restricoes;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreferenciasConsumo() {
        return preferenciasConsumo;
    }

    public void setPreferenciasConsumo(String preferenciasConsumo) {
        this.preferenciasConsumo = preferenciasConsumo;
    }

    public String getConfiguracaoAlerta() {
        return configuracaoAlerta;
    }

    public void setConfiguracaoAlerta(String configuracaoAlerta) {
        this.configuracaoAlerta = configuracaoAlerta;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public List<RestricaoAlimentar> getRestricoes() {
        return restricoes;
    }

    public void setRestricoes(List<RestricaoAlimentar> restricoes) {
        this.restricoes = restricoes;
    }


    // toString()

    @Override
    public String toString() {
        return "idUsuario: " + this.id + "\n" +
                "Nome: " + this.nome + "\n" +
                "Email: " + this.email + "\n" +
                "Preferências de consumo: " + this.preferenciasConsumo + "\n" +
                "Configuração do alerta" + this.configuracaoAlerta + "\n" +
                "Telefone(s): " + this.telefones + "\n" +
                "Restrições alimentares: " + this.restricoes;
    }
}
