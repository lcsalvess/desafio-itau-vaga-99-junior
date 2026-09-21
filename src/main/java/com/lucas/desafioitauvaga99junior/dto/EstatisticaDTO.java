package com.lucas.desafioitauvaga99junior.dto;

import java.util.DoubleSummaryStatistics;

public record EstatisticaDTO(
        long count,
        Double sum,
        Double avg,
        Double min,
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
