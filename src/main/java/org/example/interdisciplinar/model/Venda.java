package org.example.interdisciplinar.model;

import java.time.LocalDate;

public class Venda {

    // Atributos

    private int id;
    private LocalDate dataVenda;
    private double valorTotal;
    private String formaPagamento;
    private String status;
    private int idCliente;
    private int idFuncionario;

    // Construtor


    public Venda(int id, LocalDate dataVenda, double valorTotal, String formaPagamento, String status, int idCliente, int idFuncionario) {
        this.id = id;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.status = status;
        this.idCliente = idCliente;
        this.idFuncionario = idFuncionario;
    }

    // Getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    // Método toString()

    @Override
    public String toString() {
        return "==========VENDA==========" +
                "Id: " + id + "\n" +
                "Data da venda: " + dataVenda + "\n" +
                "Valor total: " + valorTotal + "\n" +
                "Forma de pagamento: " + formaPagamento + "\n" +
                "Status: " + status + "\n" +
                "Id do cliente: " + idCliente + "\n" +
                "Id do funcionário: " + idFuncionario + "\n" +
                "=========================";
    }
}
