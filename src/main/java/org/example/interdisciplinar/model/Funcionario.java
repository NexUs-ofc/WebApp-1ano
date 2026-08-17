package org.example.interdisciplinar.model;

public class Funcionario {

    // Atributos

    private int id;
    private String nome;
    private String cpf;
    private String cargo;
    private double salario;
    private String telefone;
    private String email;
    private int idLoja;

    // Construtor

    public Funcionario(int id, String nome, String cpf, String cargo, double salario, String telefone, String email, int idLoja) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.salario = salario;
        this.telefone = telefone;
        this.email = email;
        this.idLoja = idLoja;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
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

    public int getIdLoja() {
        return idLoja;
    }

    public void setIdLoja(int idLoja) {
        this.idLoja = idLoja;
    }

    // Método toSring()

    @Override
    public String toString() {
        return "==========FUNCIONÁRIO==========" +
                "Id: " + id + "\n" +
                "Nome: " + nome + "\n" +
                "Cpf: '" + cpf + "\n" +
                "Cargo: " + cargo + "\n" +
                "Salário: " + salario + "\n" +
                "Telefone: " + telefone + "\n" +
                "Email: " + email + "\n" +
                "Id da loja: " + idLoja + "\n" +
                "===============================";
    }
}
