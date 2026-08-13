package org.example.interdisciplinar.model;

public class Alerta {

    // Atributos

    private int id;
    private String tipoNotificacao;
    private String envio;

    // Construtores

    public Alerta(){}

    public Alerta(int id, String tipoNotificacao, String envio) {
        this.id = id;
        this.tipoNotificacao = tipoNotificacao;
        this.envio = envio;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoNotificacao() {
        return tipoNotificacao;
    }

    public void setTipoNotificacao(String tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }

    public String getEnvio() {
        return envio;
    }

    public void setEnvio(String envio) {
        this.envio = envio;
    }

    // toString()

    @Override
    public String toString() {
        return "Id: " + this.id + "\n" +
                "Tipo de notificação: " + this.tipoNotificacao + '\'' +
                "Envio: " + this.envio;
    }
}
