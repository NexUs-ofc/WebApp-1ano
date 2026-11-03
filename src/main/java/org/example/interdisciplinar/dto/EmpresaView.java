package org.example.interdisciplinar.dto;

// Record responsável por transportar os dados de uma empresa para exibição
public record EmpresaView(
   int id,
   String nome,
   String cnpj,
   String cep,
   String telefone
) {}
