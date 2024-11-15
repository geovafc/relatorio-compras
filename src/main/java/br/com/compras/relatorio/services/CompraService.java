package br.com.compras.relatorio.services;

import br.com.compras.relatorio.dtos.responses.ClienteResponseDTO;
import br.com.compras.relatorio.dtos.responses.CompraDetalhadaResponseDTO;
import br.com.compras.relatorio.dtos.responses.CompraResponseDTO;
import br.com.compras.relatorio.dtos.responses.ProdutoResponseDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompraService {

    public List<CompraDetalhadaResponseDTO> compras() {
        var produtos = obterProdutos();

        var clientesHistoricoCompras = obterClientesHistoricoCompras();

        var produtoMap = produtos.stream()
                .collect(Collectors.toMap(ProdutoResponseDTO::id, produto -> produto));


        return clientesHistoricoCompras.stream()
//        Transforma cada cliente e suas compras em um único fluxo (stream) de compras.
                .flatMap(cliente -> processarComprasDoCliente(cliente, produtoMap).stream())
                .sorted(Comparator.comparing(CompraDetalhadaResponseDTO::valorTotalCompra))
                .collect(Collectors.toList());

//        return clientesHistoricoCompras.stream()
//                .flatMap(cliente -> cliente.compras().stream()
//                        .map(compra -> {
//
//                            var produto = produtoMap.get(compra.produtoId());
//
//                            if (produto == null)
//                                throw new IllegalArgumentException("Produto não encontrado para a compra: " + compra.id());; // Ignora compras com produto inválido
//
//                            var valorTotal = produto.preco().multiply(BigDecimal.valueOf(compra.quantidade()));
//
//                            return new CompraDetalhadaResponseDTO(
//                                    cliente.nomeCliente(),
//                                    cliente.cpf(),
//                                    produto.nome(),
//                                    produto.categoria(),
//                                    produto.preco(),
//                                    compra.quantidade(),
//                                    valorTotal
//                            );
//
//                        }))
//                .filter(Objects::nonNull) // Remove entradas nulas
//                .sorted(Comparator.comparing(CompraDetalhadaResponseDTO::valorTotalCompra))
//                .collect(Collectors.toList());
    }

    private static List<ProdutoResponseDTO> obterProdutos() {
        var produtos = Arrays.asList(
                new ProdutoResponseDTO("1", "Vinho Tinto Cabernet", "Tinto", 120.50),
                new ProdutoResponseDTO("2", "Vinho Branco Sauvignon", "Branco", 85.00)
        );
        return produtos;
    }


    private static List<ClienteResponseDTO> obterClientesHistoricoCompras() {
        var clientes = Arrays.asList(
                new ClienteResponseDTO("101", "João Silva", "12345678900", Arrays.asList(
                        new CompraResponseDTO("5001", "1", 2, "2023-05-10T10:30:00"),
                        new CompraResponseDTO("5002", "2", 1, "2023-05-15T11:00:00")
                )),
                new ClienteResponseDTO("102", "Maria Oliveira", "98765432100", Arrays.asList(
                        new CompraResponseDTO("5003", "1", 3, "2023-05-12T09:30:00")
                ))
        );
        return clientes;
    }

    private List<CompraDetalhadaResponseDTO> processarComprasDoCliente(ClienteResponseDTO cliente, Map<String, ProdutoResponseDTO> produtoMap) {
        return cliente.compras().stream()
                .map(compra -> criarCompraDetalhadaDTO(cliente, compra, produtoMap))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private CompraDetalhadaResponseDTO criarCompraDetalhadaDTO(ClienteResponseDTO cliente, CompraResponseDTO compra, Map<String, ProdutoResponseDTO> produtoMap) {
        var produto = produtoMap.get(compra.produtoId());
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado para a compra: " + compra.id());
        }

        var valorTotal = produto.preco().multiply(BigDecimal.valueOf(compra.quantidade()));
        return new CompraDetalhadaResponseDTO(
                cliente.nomeCliente(),
                cliente.cpf(),
                produto.nome(),
                produto.categoria(),
                produto.preco(),
                compra.quantidade(),
                valorTotal
        );
    }

}
