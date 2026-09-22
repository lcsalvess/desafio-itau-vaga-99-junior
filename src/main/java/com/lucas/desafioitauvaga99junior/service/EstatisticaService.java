package com.lucas.desafioitauvaga99junior.service;

import com.lucas.desafioitauvaga99junior.dto.EstatisticaDTO;
import com.lucas.desafioitauvaga99junior.model.Transacao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class EstatisticaService {

    @Value("${estatistica.intervalo-padrao-segundos")
    private int intervaloPadraoSegundos;

    private final TransacaoService transacaoService;

    public EstatisticaService(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    public EstatisticaDTO calcularEstatisticas(Integer intervalo) {
        int intervaloSegundos = intervalo != null ? intervalo : intervaloPadraoSegundos;
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
