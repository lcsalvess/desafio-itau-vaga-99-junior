package com.lucas.transactionstatistics.service;

import com.lucas.transactionstatistics.dto.EstatisticaDTO;
import com.lucas.transactionstatistics.model.Transacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class EstatisticaService {
    private static final Logger log = LoggerFactory.getLogger(EstatisticaService.class);

    @Value("${estatistica.intervalo-padrao-segundos}")
    private int intervaloPadraoSegundos;

    private final TransacaoService transacaoService;

    public EstatisticaService(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    public EstatisticaDTO calcularEstatisticas(Integer intervalo) {
        int intervaloSegundos = intervalo != null ? intervalo : intervaloPadraoSegundos;
        List<Transacao> transacoes = transacaoService.buscarTransacoes(intervaloSegundos);

        log.info("Estatísticas calculadas: intervalo={}s, transações={}",
                intervaloSegundos,
                transacoes.size());

        if (transacoes.isEmpty()) {
            return EstatisticaDTO.vazio();
        }

        DoubleSummaryStatistics estatistica = transacoes.stream()
                .mapToDouble(Transacao::getValor)
                .summaryStatistics();

        return EstatisticaDTO.aPartirDe(estatistica);
    }
}
