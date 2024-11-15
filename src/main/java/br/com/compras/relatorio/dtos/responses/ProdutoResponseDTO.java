package br.com.compras.relatorio.dtos.responses;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
    String id,
    String nome,
    String categoria,
    BigDecimal preco)
{}
