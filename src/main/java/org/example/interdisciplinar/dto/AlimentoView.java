package org.example.interdisciplinar.dto;

// Record responsável por transportar os dados de um alimento e sua categoria
public record AlimentoView(
        int id,
        String alimento,
        String marca,
        String nomeCategoria
) {}
