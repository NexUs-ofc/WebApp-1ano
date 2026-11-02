package org.example.interdisciplinar.dto;

import java.time.LocalDate;

public record EmpresaRecente(
        String nome,
        LocalDate dataCadastro
) {
}
