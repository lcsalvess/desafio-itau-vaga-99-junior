package com.lucas.desafioitauvaga99junior.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.DoubleSummaryStatistics;

public record EstatisticaDTO(
        @Schema(
                description = "Quantidade de transações realizadas no intervalo",
                example = "5"
        )
        long count,
        @Schema(
                description = "Soma dos valores das transações",
                example = "750.00"
        )
        Double sum,
        @Schema(
                description = "Valor médio das transações",
                example = "150.00"
        )
        Double avg,
        @Schema(
                description = "Menor valor entre as transações",
                example = "50.00"
        )
        Double min,
        @Schema(
                description = "Maior valor entre as transações",
                example = "300.00"
        )
        Double max
) {
    public static EstatisticaDTO vazio() {
        return new EstatisticaDTO(0L, 0.0, 0.0, 0.0, 0.0);
    }

    public static EstatisticaDTO aPartirDe(DoubleSummaryStatistics stats) {
        return new EstatisticaDTO(
                stats.getCount(),
                stats.getSum(),
                stats.getAverage(),
                stats.getMin(),
                stats.getMax()
        );
    }
}
