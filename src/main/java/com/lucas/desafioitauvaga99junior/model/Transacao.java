package com.lucas.desafioitauvaga99junior.model;

import java.time.OffsetDateTime;

public class Transacao {

    private Double valor;
    private OffsetDateTime dataHora;

    public Transacao(Double valor, OffsetDateTime dataHora) {
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public Double getValor() {
        return valor;
    }

    public OffsetDateTime getDataHora() {
        return dataHora;
    }

}
