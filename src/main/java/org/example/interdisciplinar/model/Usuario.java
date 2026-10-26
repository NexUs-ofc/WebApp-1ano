package org.example.interdisciplinar.model;

public class Usuario {

    // Atributos
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String preferenciasConsumo;
    private ConfiguracaoAlerta configuracaoAlerta;

    // Construtores

    public Usuario(){

    }

    public Usuario(String nome, String email, String senha, String telefone, String preferenciasConsumo, ConfiguracaoAlerta configuracaoAlerta) {

        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.preferenciasConsumo = preferenciasConsumo;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreferenciasConsumo() {
        return preferenciasConsumo;
    }

    public void setPreferenciasConsumo(String preferenciasCosnumo) {
        this.preferenciasConsumo = preferenciasCosnumo;
    }

    public ConfiguracaoAlerta getConfiguracaoAlerta() {
        return configuracaoAlerta;
    }

    public void setConfiguracaoAlerta(ConfiguracaoAlerta configuracaoAlerta) {
        this.configuracaoAlerta = configuracaoAlerta;
    }

    // Método toString()

    @Override
    public String toString() {
        return "==========USUARIO==========" + "\n" +
                "Id: " + this.id + "\n" +
                "Nome: " + this.nome + "\n" +
                "Email: " + this.email + "\n" +
                "Senha: " + this.senha + "\n" +
                "Telefone: " + this.telefone + "\n" +
                "Preferências de consumo: " + this.preferenciasConsumo + "\n" +
                "Configuração do alerta: " + this.configuracaoAlerta + "\n" +
                "============================";
    }
}
