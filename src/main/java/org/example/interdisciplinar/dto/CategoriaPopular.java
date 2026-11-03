package org.example.interdisciplinar.dto;

// Record responsável por transportar os dados da categoria mais popular
public record CategoriaPopular(
        String nome,
        int total
) {}
