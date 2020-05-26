package com.br.FlightFacilities.controllers;

import com.br.FlightFacilities.models.Simulacao;
import com.br.FlightFacilities.models.Voo;
import com.br.FlightFacilities.services.SimulacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(SimulacaoController.class)
public class SimulacaoControllerTests {

    @MockBean
    SimulacaoService simulacaoService;

    @Autowired
    private MockMvc mockMvc;

    Simulacao simulacao;
    Voo voo;

    @BeforeEach
    public void inicializar() {
         voo = new Voo();
         simulacao = new Simulacao();
         voo.setId(1);
         voo.setIdEmpresa(1);
         voo.setOrigem("VCP");
         voo.setDestino("LIS");
         voo.setAssentosDisponiveis(10);
         voo.setValor(1500.00);
         simulacao.setAeporigem("VCP");
         simulacao.setAepdestino("LIS");
    }

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testarRealizarSimulacao() throws Exception {
        Iterable<Voo> vooIterable = Arrays.asList(voo);
        Mockito.when(simulacaoService.consultarVoo(Mockito.any(Simulacao.class))).thenReturn(vooIterable);

        String json = mapper.writeValueAsString(simulacao);

        mockMvc.perform(MockMvcRequestBuilders.put("/simulacao").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
                }
}