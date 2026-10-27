package org.example.interdisciplinar.utils;


// Classe que armazena regex, utilizadas na classe Regex que valida as entradas de dados.
public class Constantes {

    // Expressões regulares de validação
    public static final String REGEX_TELEFONE = "^(?:\\([0-9]{2}\\)|[0-9]{2})\\s?9[0-9]{4}-?[0-9]{4}$";
    public static final String REGEX_CEP = "^[0-9]{5}-?[0-9]{3}$";
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}$";
    public static final String REGEX_SENHA = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!._*-]).{8,}$";
    public static final String REGEX_CPF = "^(?:[0-9]{11}|[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2})$";

    // Caminho da página de erro
    public static final String ERROR_PAGE = "/WEB-INF/erro.jsp";

}
