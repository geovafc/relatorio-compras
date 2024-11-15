package br.com.compras.relatorio.dtos.responses;

import java.math.BigDecimal;

public record CompraDetalhadaResponseDTO(
        String nomeCliente,
        String cpfCliente,
        String nomeProduto,
        String categoriaProduto,
        BigDecimal preçoProduto,
        Integer quantidadeProdutoComprada,
        BigDecimal valorTotalCompra)
{
}
