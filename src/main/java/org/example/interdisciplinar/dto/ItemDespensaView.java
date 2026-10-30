package org.example.interdisciplinar.dto;

import java.time.LocalDate;

public record ItemDespensaView(
        int id,
        String nomeUsuario,
        String alimento,
        int quantidade,
        LocalDate validade
) {}
