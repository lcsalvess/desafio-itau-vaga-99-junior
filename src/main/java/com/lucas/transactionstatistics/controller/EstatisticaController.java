package com.lucas.transactionstatistics.controller;

import com.lucas.transactionstatistics.dto.EstatisticaDTO;
import com.lucas.transactionstatistics.service.EstatisticaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {

    private final EstatisticaService estatisticaService;

    public EstatisticaController(EstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    @Operation(
            summary = "Consulta as estatísticas das transações",
            description = "Calcula as estatísticas das transações realizadas dentro do intervalo informado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Estatísticas calculadas com sucesso"
            )
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EstatisticaDTO buscarEstatisticas(
            @Parameter(
                    description = "Intervalo de tempo em segundos utilizado para calcular as estatísticas. Quando não informado, utiliza o intervalo padrão configurado na aplicação.",
                    example = "60"
            )
            @RequestParam(required = false)
            @Positive
            Integer intervalo
    ) {
        return estatisticaService.calcularEstatisticas(intervalo);
    }
}
