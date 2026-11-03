package org.example.interdisciplinar.dto;

import org.example.interdisciplinar.model.ConfiguracaoAlerta;

// Record responsável por transportar os dados de um usuário para exibição
public record UsuarioView(
        int id,
        String nome,
        String email,
        String preferenciasConsumo,
        String telefone,
        ConfiguracaoAlerta configuracaoAlerta
) {}
