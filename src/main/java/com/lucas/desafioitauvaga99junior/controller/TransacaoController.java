package com.lucas.desafioitauvaga99junior.controller;

import com.lucas.desafioitauvaga99junior.dto.TransacaoDTO;
import com.lucas.desafioitauvaga99junior.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @Operation(
            summary = "Cria uma transação",
            description = "Cria uma nova transação em memória."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Transação criada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Dados da transação inválidos"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "JSON inválido"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criar(@Valid @RequestBody TransacaoDTO dto) {
        transacaoService.criar(dto);
    }

    @Operation(
            summary = "Exclui todas as transações",
            description = "Remove todas as transações armazenadas em memória."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transações excluídas com sucesso"
            )
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deletar() {
        transacaoService.deletar();
    }
}
