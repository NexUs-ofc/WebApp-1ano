package org.example.interdisciplinar.utils;


// Classe que armazena regex, utilizadas na classe Regex que valida as entradas de dados.
public class Constantes {

    // Expressões regulares de validação
    public static final String regexTelefone = "^(?:\\([0-9]{2}\\)|[0-9]{2})\\s?9[0-9]{4}-?[0-9]{4}$";
    public static final String regexCep = "^[0-9]{5}-?[0-9]{3}$";
    public static final String regexEmail = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}$";
    public static final String regexSenha = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!._*-]).{8,}$";
    public static final String regexCpf = "^(?:[0-9]{11}|[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2})$";

    // Caminho da página de erro
    public static final String paginaErro = "/WEB-INF/erro.jsp";

}
