package com.lucas.desafioitauvaga99junior.controller;

import com.lucas.desafioitauvaga99junior.dto.EstatisticaDTO;
import com.lucas.desafioitauvaga99junior.service.EstatisticaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EstatisticaController.class)
public class EstatisticaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EstatisticaService estatisticaService;

    @Nested
    @DisplayName("GET /estatistica")
    class BuscarEstatisticasTest {

        @Test
        @DisplayName("deve retornar as estatísticas")
        void deveRetornarEstatisticas() throws Exception {
            // Arrange
            when(estatisticaService.calcularEstatisticas(null))
                    .thenReturn(estatisticas());
            // Act
            mockMvc.perform(get("/estatistica"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.count").value(3))
                    .andExpect(jsonPath("$.sum").value(60.0))
                    .andExpect(jsonPath("$.avg").value(20.0))
                    .andExpect(jsonPath("$.min").value(10.0))
                    .andExpect(jsonPath("$.max").value(30.0));
            // Assert
            verify(estatisticaService).calcularEstatisticas(null);
        }

        @Test
        @DisplayName("deve retornar estatísticas zeradas quando não houver transações")
        void deveRetornarEstatisticasZeradasQuandoNaoHouverTransacoes() throws Exception {
            // Arrange
            when(estatisticaService.calcularEstatisticas(null))
                    .thenReturn(new EstatisticaDTO(0L, 0.0, 0.0, 0.0, 0.0));
            // Act
            mockMvc.perform(get("/estatistica"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.count").value(0L))
                    .andExpect(jsonPath("$.sum").value(0.0))
                    .andExpect(jsonPath("$.avg").value(0.0))
                    .andExpect(jsonPath("$.min").value(0.0))
                    .andExpect(jsonPath("$.max").value(0.0));
            // Assert
            verify(estatisticaService).calcularEstatisticas(null);
        }

        @Test
        @DisplayName("deve retornar as estatísticas do intervalo informado")
        void deveRetornarEstatisticasDoIntervaloInformado() throws Exception {
            // Arrange
            when(estatisticaService.calcularEstatisticas(120))
                    .thenReturn(estatisticas());
            // Act
            mockMvc.perform(get("/estatistica?intervalo=120"))
                    .andExpect(status().isOk());
            // Assert
            verify(estatisticaService).calcularEstatisticas(120);
        }

        @Test
        @DisplayName("deve rejeitar intervalo igual a zero")
        void deveRejeitarIntervaloIgualAZero() throws Exception {
            mockMvc.perform(get("/estatistica?intervalo=0"))
                    .andExpect(status().isBadRequest());
            verifyNoInteractions(estatisticaService);
        }
    }

    private EstatisticaDTO estatisticas() {
        return new EstatisticaDTO(
                3L,
                60.0,
                20.0,
                10.0,
                30.0
        );
    }
}
