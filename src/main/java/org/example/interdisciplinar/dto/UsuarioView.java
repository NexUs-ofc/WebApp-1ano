package org.example.interdisciplinar.dto;

import org.example.interdisciplinar.model.ConfiguracaoAlerta;

public record UsuarioView(
   int id,
   String nome,
   String telefone,
   String email,
   ConfiguracaoAlerta configuracaoAlerta
) {}
