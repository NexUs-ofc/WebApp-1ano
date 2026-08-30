package org.example.interdisciplinar.model;

public class Usuario {

    // Atributos

    private int id;
    private String nome;
    private String email;
    private String preferenciasCosnumo;
    private String configuracaoAlerta;

    // Construtores

    public Usuario(){

    }

    public Usuario(String nome, String email, String preferenciasCosnumo, String configuracaoAlerta) {

        this.nome = nome;
        this.email = email;
        this.preferenciasCosnumo = preferenciasCosnumo;
        this.configuracaoAlerta = configuracaoAlerta;
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

    public String getPreferenciasCosnumo() {
        return preferenciasCosnumo;
    }

    public void setPreferenciasCosnumo(String preferenciasCosnumo) {
        this.preferenciasCosnumo = preferenciasCosnumo;
    }

    public String getConfiguracaoAlerta() {
        return configuracaoAlerta;
    }

    public void setConfiguracaoAlerta(String configuracaoAlerta) {
        this.configuracaoAlerta = configuracaoAlerta;
    }

    // Método toString()

    @Override
    public String toString() {
        return "==========USUARIO==========" +
                "Id: " + this.id + "\n" +
                "Nome: " + this.nome + "\n" +
                "Email: " + this.email + "\n" +
                "Preferências de consumo: " + this.preferenciasCosnumo + "\n" +
                "Configuração do alerta: " + this.configuracaoAlerta + "\n" +
                "============================";
    }
}
