package org.example.interdisciplinar.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Classe para validar expressões
public class Regex {

    // Método booleano para validar cep
    public static boolean validarCep(String cep){

        // Importando a expressão regular (regex)
        String expressao = Constantes.regexCep;

        // Compilando o regex
        Pattern regex = Pattern.compile(expressao);

        // Compara a entrada de dados com o regex
        Matcher validador = regex.matcher(cep);

        // Retorna se é válido ou não
        return validador.matches();
    }

    // Método booleano para validar cpf
    public static boolean validarCpf(String cpf){

        // Importando a expressão regular (regex)
        String expressao = Constantes.regexCpf;

        // Compilando o regex
        Pattern regex = Pattern.compile(expressao);

        // Compara a entrada de dados com o regex
        Matcher validador = regex.matcher(cpf);

        // Retorna se é valido ou não
        return validador.matches();
    }

    // Método booleano para validar telefone
    public static boolean validarTelefone(String telefone){

        // Importando a expressão regular (regex)
        String expressao = Constantes.regexTelefone;

        // Compilando o regex
        Pattern regex = Pattern.compile(expressao);

        // Compara a entrada de dados com o regex
        Matcher validador = regex.matcher(telefone);

        // Retorna se é válido ou não
        return validador.matches();
    }

    // Método booleano para validar email
    public static boolean validarEmail(String email){

        // Importando a expressão regular (regex)
        String expressao = Constantes.regexEmail;

        // Compilando o regex
        Pattern regex = Pattern.compile(expressao);

        // Compara a entrada de dados com o regex
        Matcher validador = regex.matcher(email);

        // Retorna se é válido ou não
        return validador.matches();
    }

    // Método booleano para validar senha
    public static boolean validarSenha(String senha){

        // Importando a expressão regular (regex)
        String expressao = Constantes.regexSenha;

        // Compilando o regex
        Pattern regex = Pattern.compile(expressao);

        // Compara a entrada de dados com o regex
        Matcher validador = regex.matcher(senha);

        // Retorna se é válido ou não
        return validador.matches();
    }

}
