package org.example.interdisciplinar.exception;

// Exceção personalizada para representar falhas durante operações de persistência no banco de dados.
public class ErroPersistenciaException extends RuntimeException {

    // Construtor que recebe a mensagem do erro e a exceção original que provocou o problema
    public ErroPersistenciaException(String mensagem, Throwable causa) {

        // Encaminha a mensagem e a causa para a classe RuntimeException, preservando os detalhes do erro original.
        super(mensagem, causa);
    }
}