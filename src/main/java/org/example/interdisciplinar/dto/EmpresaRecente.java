package org.example.interdisciplinar.dto;

import java.time.LocalDate;

// Record responsável por transportar os dados de uma empresa cadastrada recentemente
public record EmpresaRecente(
        String nome,
        LocalDate dataCadastro
) {}
