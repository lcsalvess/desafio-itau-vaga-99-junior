package com.lucas.desafioitauvaga99junior.service;

import com.lucas.desafioitauvaga99junior.dto.TransacaoDTO;
import com.lucas.desafioitauvaga99junior.model.Transacao;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TransacaoService {
    private final Clock clock;

    public TransacaoService(Clock clock) {
        this.clock = clock;
    }

    private final List<Transacao> transacoes = new CopyOnWriteArrayList<>();

    public List<Transacao> buscarTransacoes(Integer intervaloSegundos) {
        OffsetDateTime limite = OffsetDateTime.now(clock).minusSeconds(intervaloSegundos);
        return transacoes.stream()
                .filter(t -> t.getDataHora().isAfter(limite))
                .toList();
    }

    public void criar (TransacaoDTO dto) {
        Transacao transacao = new Transacao(dto.valor(), dto.dataHora());
        transacoes.add(transacao);
    }

    public void deletar() {
        transacoes.clear();
    }
}
