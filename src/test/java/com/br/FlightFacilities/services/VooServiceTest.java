package com.br.FlightFacilities.services;

import com.br.FlightFacilities.models.Voo;
import com.br.FlightFacilities.repositories.VooRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class VooServiceTest {

    @MockBean
    VooRepository vooRepository;

    @Autowired
    VooService vooService;

    Voo voo;

    @BeforeEach
    public void inicializar(){
        voo = new Voo();
        voo.setId(1);
        voo.setIdEmpresa(1);
        voo.setOrigem("THE");
        voo.setDestino("GRU");
        voo.setAssentosDisponiveis(120);
        voo.setValor(1200.0);

    }

    @Test
    public void testarSalvarVoo(){

        Mockito.when(vooRepository.save(Mockito.any(Voo.class))).thenReturn(voo);

        Voo vooObjeto = vooService.salvarVoo(voo);

        Assertions.assertEquals(voo, vooObjeto);
    }

    @Test
    public void testarAtualizarVoo(){

        voo.setValor(1100.0);

        Mockito.when(vooRepository.save(Mockito.any(Voo.class))).thenReturn(voo);

        Voo vooObjeto = vooService.salvarVoo(voo);

        Assertions.assertEquals(voo.getValor(), vooObjeto.getValor());
    }

    @Test
    public void testarBuscarPorId(){

        Optional<Voo> optionalVoo = Optional.of(voo);

        Mockito.when(vooRepository.findById(Mockito.any(Integer.class))).thenReturn(optionalVoo);

        Optional<Voo> optionalVoo2 = vooService.buscarPorId(1);

        Assertions.assertEquals(optionalVoo.get(), optionalVoo2.get());
    }

    @Test
    public void testarBuscaTodos(){

        Iterable<Voo> vooIterable = Arrays.asList(voo);

        Mockito.when(vooRepository.findAll()).thenReturn(vooIterable);

        Iterable<Voo> vooIterable2 = vooService.buscarTodosVoos();

        Assertions.assertEquals(vooIterable, vooIterable2);
    }


    @Test
    public void testarDeletarVoo(){
        vooService.deletarVoo(voo);
        Mockito.verify(vooRepository).delete(Mockito.any(Voo.class));
    }
}
