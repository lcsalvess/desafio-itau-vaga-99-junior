package com.lucas.desafioitauvaga99junior.service;

import com.lucas.desafioitauvaga99junior.dto.TransacaoDTO;
import com.lucas.desafioitauvaga99junior.model.Transacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TransacaoService {
    private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);

    private final Clock clock;
    private final List<Transacao> transacoes = new CopyOnWriteArrayList<>();

    public TransacaoService(Clock clock) {
        this.clock = clock;
    }

    public List<Transacao> buscarTransacoes(Integer intervaloSegundos) {
        OffsetDateTime limite = OffsetDateTime.now(clock).minusSeconds(intervaloSegundos);
        return transacoes.stream()
                .filter(t -> t.getDataHora().isAfter(limite))
                .toList();
    }

    public void criar (TransacaoDTO dto) {
        Transacao transacao = new Transacao(dto.valor(), dto.dataHora());
        transacoes.add(transacao);
        log.info("Transação criada: valor={}", dto.valor());
    }

    public void deletar() {
        int quantidade = transacoes.size();
        transacoes.clear();
        log.info("Transações deletadas: quantidade={}", quantidade);
    }
}
