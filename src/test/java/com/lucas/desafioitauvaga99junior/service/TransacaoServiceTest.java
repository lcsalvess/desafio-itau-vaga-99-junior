package com.lucas.desafioitauvaga99junior.service;

import com.lucas.desafioitauvaga99junior.dto.TransacaoDTO;
import com.lucas.desafioitauvaga99junior.entity.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransacaoServiceTest {
    private TransacaoService transacaoService;
    private OffsetDateTime agora;

    @BeforeEach
    void setUp() {
        transacaoService = new TransacaoService();
        agora = OffsetDateTime.now();
    }

    @Nested
    @DisplayName("Criação de transação")
    class CriarTests {

        @Test
        @DisplayName("deve adicionar a transação à lista")
        void deveAdicionarTransacaoALista() {
            // Arrange
            TransacaoDTO dto = criarTransacao(50.0, agora);
            // Act
            List<Transacao> transacoes = transacaoService.buscarTransacoes(60);
            // Assert
            assertEquals(1, transacoes.size());
            assertEquals(dto.valor(), transacoes.getFirst().getValor());
            assertEquals(dto.dataHora(), transacoes.getFirst().getDataHora());
        }
    }

    @Nested
    @DisplayName("Busca de transações")
    class BuscarTests {

        @Test
        @DisplayName("deve retornar transações dentro do intervalo")
        void deveRetornarTransacoesPorIntervalo() {
            // Arrange
            criarTransacao(
                    50.0,
                    agora.minusSeconds(65)
            );
            TransacaoDTO transacaoDentroDoIntervalo = criarTransacao(
                    100.0,
                    agora.minusSeconds(30)
            );
            // Act
            List<Transacao> transacoes = transacaoService.buscarTransacoes(60);
            // Assert
            assertEquals(1, transacoes.size());
            assertEquals(transacaoDentroDoIntervalo.valor(), transacoes.getFirst().getValor());
            assertEquals(transacaoDentroDoIntervalo.dataHora(), transacoes.getFirst().getDataHora());
        }

        @Test
        @DisplayName("não deve retornar transações fora do intervalo")
        void naoDeveRetornarTransacoesForaDoIntervalo() {
            // Arrange
            criarTransacao(
                    650.5,
                    agora.minusSeconds(90)
            );
            // Act
            List<Transacao> transacoes = transacaoService.buscarTransacoes(60);
            // Assert
            assertEquals(0, transacoes.size());
        }

        @Test
        @DisplayName("deve retornar lista vazia quando não houver transações no intervalo")
        void deveRetornarListaVaziaQuandoNaoHouverTransacoes() {
            // Act
            List<Transacao> transacoes = transacaoService.buscarTransacoes(60);
            // Assert
            assertTrue(transacoes.isEmpty());
        }
    }

    @Nested
    @DisplayName("Exclusão de transações")
    class DeletarTests {

        @Test
        @DisplayName("deve remover todas as transações")
        void deveRemoverTodasAsTransacoes() {
            // Arrange
            criarTransacao(50.0, agora);
            criarTransacao(100.5, agora);
            criarTransacao(65.7, agora);
            // Act
            transacaoService.deletar();
            List<Transacao> transacoes = transacaoService.buscarTransacoes(60);
            // Assert
            assertTrue(transacoes.isEmpty());
        }

        @Test
        @DisplayName("deve funcionar quando não existem transações")
        void deveFuncionarQuandoNaoExistemTransacoes() {
            // Act
            transacaoService.deletar();
            List<Transacao> transacoes = transacaoService.buscarTransacoes(60);
            // Assert
            assertTrue(transacoes.isEmpty());
        }
    }

    private TransacaoDTO criarTransacao(Double valor, OffsetDateTime dataHora) {
        TransacaoDTO dto = new TransacaoDTO(valor, dataHora);
        transacaoService.criar(dto);
        return dto;
    }
}
