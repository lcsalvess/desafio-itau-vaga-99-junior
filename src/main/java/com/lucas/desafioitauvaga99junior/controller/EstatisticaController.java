package com.lucas.desafioitauvaga99junior.controller;

import com.lucas.desafioitauvaga99junior.dto.EstatisticaDTO;
import com.lucas.desafioitauvaga99junior.service.EstatisticaService;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EstatisticaDTO buscarEstatisticas(
            @RequestParam(required = false)
            @Positive
            Integer intervalo
    ) {
        return estatisticaService.calcularEstatisticas(intervalo);
    }
}
