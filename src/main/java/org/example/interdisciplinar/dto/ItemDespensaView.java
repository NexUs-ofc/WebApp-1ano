package org.example.interdisciplinar.dto;

import java.time.LocalDate;

public record ItemDespensaView(
        int id,
        int idUsuario,
        int idAlimento,
        String nomeUsuario,
        String alimento,
        int quantidade,
        LocalDate validade
) {}
