package org.example.interdisciplinar.model;

import java.time.LocalDate;

public class Empresa {

    private int id;
    private String nome;
    private String cnpj;
    private int idEndereco;
    private String email;
    private String telefone;
    private LocalDate dataCadastro;

    public Empresa() {
    }

    public Empresa(String nome, String cnpj, int idEndereco, String email, String telefone, LocalDate dataCadastro) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.idEndereco = idEndereco;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public String toString() {
        return "==========EMPRESA==========" + "\n" +
                "Id: " + this.id + "\n" +
                "Nome: " + this.nome + "\n" +
                "CNPJ: " + this.cnpj + "\n" +
                "Id Endereco: " + this.idEndereco + "\n" +
                "Email: " + this.email + "\n" +
                "Telefone: " + this.telefone + "\n" +
                "Data Cadastro: " + this.dataCadastro + "\n" +
                "============================";
    }
}
