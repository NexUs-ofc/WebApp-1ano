package org.example.interdisciplinar.utils;
import org.mindrot.jbcrypt.BCrypt;

// Classe criptografia: Responsável por gerar e verificar hashes de senhas
public class Criptografia {

    // Quanto maior esse custo, maior é o trabalho para gerar o hash (2^cost)
    private static final int cost = 10;

    // Método para criptografar uma senha, recebe como parâmetro uma senha normal (limpa)
    public static String criptografar(String senhaLimpa){

        // Gera o hash utilizando um salt aleatório
        String hash = BCrypt.hashpw(senhaLimpa, BCrypt.gensalt(cost));

        // Retorna a senha hasheada
        return hash;
    }

    // Método para verificar se a senha informada corresponde a senha hasheada.
    public static boolean verificar(String senhaLimpa, String hashArmazenado){

        // Retorna true se a senha corresponder ao hash.
        return BCrypt.checkpw(senhaLimpa, hashArmazenado);
    }
}
