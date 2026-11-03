
package org.example.interdisciplinar.utils;

import org.mindrot.jbcrypt.BCrypt;

// Classe criptografia: Responsável por gerar e verificar hashes de senhas
public class Criptografia {

    // Quanto maior esse custo, maior é o trabalho para gerar o hash (2^cost)
    private static final int cost = 10;

    // Método para criptografar uma senha, recebe como parâmetro uma senha normal (limpa)
    public static String criptografar(String senhaLimpa) {

        // Gera o hash utilizando um salt aleatório
        String hash = BCrypt.hashpw(senhaLimpa, BCrypt.gensalt(cost));

        // Retorna a senha hasheada
        return hash;
    }

    // Método para verificar se a senha informada corresponde a senha hasheada.
    public static boolean verificar(String senhaLimpa, String hashArmazenado) {

        if (senhaLimpa == null || hashArmazenado == null) {
            return false;
        }

        // Verifica se o hash possui um formato BCrypt compatível.
        if (!hashArmazenado.matches("^\\$2[ab]\\$\\d{2}\\$[./A-Za-z0-9]{53}$")) {
            return false;
        }

        try {
            // Retorna true se a senha corresponder ao hash.
            return BCrypt.checkpw(senhaLimpa, hashArmazenado);

        } catch (IllegalArgumentException e) {
            // Evita erro 500 caso o hash armazenado seja inválido.
            return false;
        }
    }
}
