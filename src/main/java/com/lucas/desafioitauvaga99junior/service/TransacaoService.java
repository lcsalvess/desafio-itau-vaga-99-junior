package com.lucas.desafioitauvaga99junior.service;

import com.lucas.desafioitauvaga99junior.dto.TransacaoDTO;
import com.lucas.desafioitauvaga99junior.entity.Transacao;
import com.lucas.desafioitauvaga99junior.exception.RegraNegocioException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TransacaoService {
    private final List<Transacao> transacoes = new CopyOnWriteArrayList<>();

    public List<Transacao> buscarTransacoes(Integer intervaloSegundos) {
        OffsetDateTime limite = OffsetDateTime.now().minusSeconds(intervaloSegundos);
        return transacoes.stream()
                .filter(t -> t.getDataHora().isAfter(limite))
                .toList();
    }

    public void criar (TransacaoDTO dto) {
        validarTransacao(dto);
        Transacao transacao = new Transacao(dto.valor(), dto.dataHora());
        transacoes.add(transacao);
    }

    public void deletar() {
        transacoes.clear();
    }

    private void validarTransacao(TransacaoDTO dto) {
        OffsetDateTime now = OffsetDateTime.now();
        if (dto.dataHora().isAfter(now)) {
            throw new RegraNegocioException("A data da transação não pode ser no futuro");
        }
    }
}
