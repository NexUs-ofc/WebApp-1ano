package org.example.interdisciplinar.dto;

// Record responsável por transportar a quantidade de cadastros realizados em cada mês
public record CadastroMensal(
        int mes,
        int total
) {}