package com.lucas.transactionstatistics.service;

import com.lucas.transactionstatistics.dto.EstatisticaDTO;
import com.lucas.transactionstatistics.model.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EstatisticaServiceTest {
    private OffsetDateTime agora;
    private static final int INTERVALO_PADRAO_SEGUNDOS = 60;
    @Mock
    private TransacaoService transacaoService;
    @InjectMocks
    private EstatisticaService estatisticaService;

    @BeforeEach
    void setUp() {
        agora = OffsetDateTime.now();
        ReflectionTestUtils.setField(estatisticaService, "intervaloPadraoSegundos", INTERVALO_PADRAO_SEGUNDOS);
    }

    @Nested
    @DisplayName("Cálculo de estatísticas")
    class CalcularEstatisticasTests {

        @Test
        @DisplayName("deve calcular as estatísticas das transações")
        void deveCalcularAsEstatisticasDasTransacoes() {
            // Arrange
            Transacao transacao1 = new Transacao(10.0, agora);
            Transacao transacao2 = new Transacao(30.0, agora);
            Transacao transacao3 = new Transacao(20.0, agora);
            when(transacaoService.buscarTransacoes(INTERVALO_PADRAO_SEGUNDOS))
                    .thenReturn(List.of(transacao1, transacao2, transacao3));
            // Act
            EstatisticaDTO resultado = estatisticaService.calcularEstatisticas(INTERVALO_PADRAO_SEGUNDOS);
            // Assert
            assertEquals(3, resultado.count());
            assertEquals(60.0, resultado.sum());
            assertEquals(60.0 / 3, resultado.avg());
            assertEquals(10.0, resultado.min());
            assertEquals(30.0, resultado.max());
        }
    }

    @Nested
    @DisplayName("Intervalo de busca")
    class IntervaloTests {
        @Test
        @DisplayName("deve usar o intervalo informado")
        void deveUsarIntervaloInformado() {
            // Arrange
            when(transacaoService.buscarTransacoes(120))
                    .thenReturn(List.of());
            // Act
            estatisticaService.calcularEstatisticas(120);
            // Assert
            verify(transacaoService).buscarTransacoes(120);
        }

        @Test
        @DisplayName("deve usar o intervalo padrão quando não informado")
        void deveUsarIntervaloPadraoQuandoNaoInformado() {
            // Arrange
            Transacao transacao1 = new Transacao(10.0, agora);
            Transacao transacao2 = new Transacao(30.0, agora);
            when(transacaoService.buscarTransacoes(INTERVALO_PADRAO_SEGUNDOS))
                    .thenReturn(List.of(transacao1, transacao2));
            // Act
            EstatisticaDTO resultado = estatisticaService.calcularEstatisticas(null);
            // Assert
            verify(transacaoService).buscarTransacoes(INTERVALO_PADRAO_SEGUNDOS);
            assertEquals(2, resultado.count());
            assertEquals(40.0, resultado.sum());
            assertEquals(40.0 / 2, resultado.avg());
            assertEquals(10.0, resultado.min());
            assertEquals(30.0, resultado.max());
        }
    }

    @Nested
    @DisplayName("Ausência de transações")
    class AusenciaTests {

        @Test
        @DisplayName("deve retornar estatísticas zeradas quando não houver transações")
        void deveRetornarEstatisticasZeradasQuandoNaoHouverTransacoes() {
            // Arrange
            when(transacaoService.buscarTransacoes(INTERVALO_PADRAO_SEGUNDOS))
                    .thenReturn(List.of());
            // Act
            EstatisticaDTO dto = estatisticaService.calcularEstatisticas(null);
            // Assert
            assertEquals(0L, dto.count());
            assertEquals(0.0, dto.sum());
            assertEquals(0.0, dto.avg());
            assertEquals(0.0, dto.min());
            assertEquals(0.0, dto.max());
        }
    }
}
