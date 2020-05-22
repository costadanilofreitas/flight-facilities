package com.br.FlightFacilities.services;

import com.br.FlightFacilities.models.Simulacao;
import com.br.FlightFacilities.models.Voo;
import com.br.FlightFacilities.repositories.VooRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

@SpringBootTest
public class SimulacaoServiceTests {
    @MockBean
    VooRepository vooRepository;

    @Autowired
    SimulacaoService simulacaoService;

    Voo voo;
    Simulacao simulacao;

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

    @Test
    public void testarConsultarVoo(){
        Iterable<Voo> vooIterable = Arrays.asList(voo);
        Mockito.when(vooRepository.findByOrigemAndDestino(Mockito.anyString(), Mockito.anyString())).thenReturn(vooIterable);
        simulacaoService.consultarVoo(simulacao);

        Mockito.verify(vooRepository,Mockito.times(1)).findByOrigemAndDestino("VCP", "LIS" );
    }
}
