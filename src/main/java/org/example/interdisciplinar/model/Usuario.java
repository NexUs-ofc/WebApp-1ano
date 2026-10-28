package org.example.interdisciplinar.model;

public class Usuario {

    // Atributos
    private int id;
    private String nome;
    private String email;
    private String preferenciasConsumo;
    private ConfiguracaoAlerta configuracaoAlerta;
    private String telefone;
    private String senha;

    // Construtores

    public Usuario(){

    }

    public Usuario(String nome, String email, String preferenciasConsumo, ConfiguracaoAlerta configuracaoAlerta,  String senha, String telefone) {

        this.nome = nome;
        this.email = email;
        this.preferenciasConsumo = preferenciasConsumo;
        this.configuracaoAlerta = configuracaoAlerta;
        this.telefone = telefone;
        this.senha = senha;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
        return "==========USUARIO==========" + "\n" +
                "Id: " + this.id + "\n" +
                "Nome: " + this.nome + "\n" +
                "Email: " + this.email + "\n" +
                "Preferências de consumo: " + this.preferenciasConsumo + "\n" +
                "Configuração do alerta: " + this.configuracaoAlerta + "\n" +
                "Telefone: " + this.telefone + "\n" +
                "Senha: " + this.senha + "\n" +
                "============================";
    }
}
