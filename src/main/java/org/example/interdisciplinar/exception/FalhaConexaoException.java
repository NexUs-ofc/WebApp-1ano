package org.example.interdisciplinar.exception;

// Exceção personalizada para representar falhas ao tentar estabelecer uma conexão com o banco de dados.
public class FalhaConexaoException extends RuntimeException {

    // Construtor que recebe a mensagem do erro e a exceção original que provocou o problema.
    public FalhaConexaoException(String mensagem, Throwable causa) {

        // Encaminha a mensagem e a causa para a classe RuntimeException, preservando os detalhes do erro original.
        super(mensagem, causa);
    }
}