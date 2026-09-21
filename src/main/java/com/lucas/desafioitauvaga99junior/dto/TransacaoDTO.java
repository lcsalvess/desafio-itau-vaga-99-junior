package com.lucas.desafioitauvaga99junior.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.OffsetDateTime;

public record TransacaoDTO(
        @NotNull(message = "O valor não pode ser nulo")
        @PositiveOrZero(message = "O valor deve ser igual ou maior que zero")
        Double valor,
        @NotNull
        @PastOrPresent
        OffsetDateTime dataHora
) {
}
