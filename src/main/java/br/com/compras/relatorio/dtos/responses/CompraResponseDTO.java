package br.com.compras.relatorio.dtos.responses;

public record CompraResponseDTO(
        String id,
        String produtoId,
        int quantidade,
        String data)

{}