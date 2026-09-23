package com.lucas.desafioitauvaga99junior.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.OffsetDateTime;

public record TransacaoDTO(
        @Schema(
                description = "Valor da transação",
                example = "150.50"
        )
        @NotNull(message = "O valor não pode ser nulo")
        @PositiveOrZero(message = "O valor deve ser igual ou maior que zero")
        Double valor,
        @Schema(
                description = "Data e hora em que a transação foi realizada",
                example = "2026-09-22T19:30:00-03:00"
        )
        @NotNull
        @PastOrPresent
        OffsetDateTime dataHora
) {
}
