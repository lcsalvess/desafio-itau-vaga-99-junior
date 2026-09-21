package com.lucas.desafioitauvaga99junior.controller;

import com.lucas.desafioitauvaga99junior.dto.TransacaoDTO;
import com.lucas.desafioitauvaga99junior.service.TransacaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransacaoController.class)
public class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransacaoService transacaoService;

    @Nested
    @DisplayName("POST /transacao")
    class CriarTests {
        @Test
        @DisplayName("deve criar uma transação")
        void deveCriarTransacao() throws Exception {
            // Act
            mockMvc.perform(post("/transacao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                    "valor": 50.0,
                                    "dataHora": "2026-09-21T19:00:00-03:00"
                                    }
                                    """))
                    .andExpect(status().isCreated());
            // Assert
            ArgumentCaptor<TransacaoDTO> captor = ArgumentCaptor.forClass(TransacaoDTO.class);
            verify(transacaoService).criar(captor.capture());
            TransacaoDTO dto = captor.getValue();
            assertEquals(50.0, dto.valor());
            assertTrue(OffsetDateTime.parse("2026-09-21T19:00:00-03:00").isEqual(dto.dataHora()));
        }

        @Test
        @DisplayName("deve rejeitar dados inválidos")
        void deveRejeitarDadosInvalidos() throws Exception {
            // Act
            mockMvc.perform(post("/transacao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                    "valor": -50.0,
                                    "dataHora": "2026-09-21T19:00:00-03:00"
                                    }
                                    """))
                    // Assert
                    .andExpect(status().isUnprocessableContent());
            verifyNoInteractions(transacaoService);
        }

        @Test
        @DisplayName("deve rejeitar data futura")
        void deveRejeitarDataFutura() throws Exception {
            // Act
            mockMvc.perform(post("/transacao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                    "valor": 80.0,
                                    "dataHora": "2026-09-22T19:00:00-03:00"
                                    }
                                    """))
                    // Assert
                    .andExpect(status().isUnprocessableContent());
            verifyNoInteractions(transacaoService);
        }

        @Test
        @DisplayName("deve rejeitar JSON inválido")
        void deveRejeitarJSONInvalido() throws Exception {
            // Act
            mockMvc.perform(post("/transacao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                    valor: 80.0,
                                    dataHora: 2026-09-22T19:00:00-03:00
                                    }
                                    """))
                    // Assert
                    .andExpect(status().isBadRequest());
            verifyNoInteractions(transacaoService);
        }
    }

    @Nested
    @DisplayName("DELETE /transacao")
    class DeletarTests {

        @Test
        @DisplayName("deve deletar todas as transações")
        void deveDeletarTodasAsTransacoes() throws Exception {
            // Act
            mockMvc.perform(delete("/transacao"))
                    .andExpect(status().isOk());
            // Assert
            verify(transacaoService).deletar();
        }
    }
}
