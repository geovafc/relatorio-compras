package br.com.compras.relatorio.dtos.responses;

import java.util.List;

public record ClienteResponseDTO(
    String clienteId,
    String nomeCliente,
    String cpf,
    List<CompraResponseDTO> compras)
{}