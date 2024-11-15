package br.com.compras.relatorio.controllers;

import br.com.compras.relatorio.dtos.responses.CompraDetalhadaResponseDTO;
import br.com.compras.relatorio.services.CompraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class RelatorioCompraControllerV1 {

    @Autowired
    private CompraService compraService;

    @GetMapping("/compras")
    public ResponseEntity<List<CompraDetalhadaResponseDTO>> compras() {

        log.info("m=compras");

        var compras = compraService.compras();

        return ResponseEntity.ok(compras);

    }
}