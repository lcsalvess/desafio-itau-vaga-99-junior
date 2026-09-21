package com.lucas.desafioitauvaga99junior.service;

import com.lucas.desafioitauvaga99junior.dto.EstatisticaDTO;
import com.lucas.desafioitauvaga99junior.entity.Transacao;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class EstatisticaService {

    private static final int INTERVALO_PADRAO_SEGUNDOS = 60;

    private final TransacaoService transacaoService;

    public EstatisticaService(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    public EstatisticaDTO calcularEstatisticas(Integer intervalo) {
        int intervaloSegundos = intervalo != null ? intervalo : INTERVALO_PADRAO_SEGUNDOS;
        List<Transacao> transacoes = transacaoService.buscarTransacoes(intervaloSegundos);
        if (transacoes.isEmpty()) {
            return EstatisticaDTO.vazio();
        }

        DoubleSummaryStatistics estatistica = transacoes.stream()
                .mapToDouble(Transacao::getValor)
                .summaryStatistics();

        return EstatisticaDTO.aPartirDe(estatistica);
    }
}
