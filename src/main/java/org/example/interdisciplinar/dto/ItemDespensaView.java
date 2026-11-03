package org.example.interdisciplinar.dto;

import java.time.LocalDate;

// Record responsável por transportar os dados de um item da despensa para exibição
public record ItemDespensaView(
        int id,
        int idUsuario,
        int idAlimento,
        String nomeUsuario,
        String alimento,
        int quantidade,
        LocalDate validade
) {}
