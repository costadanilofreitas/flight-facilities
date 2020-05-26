package com.br.FlightFacilities.services;

import com.br.FlightFacilities.enums.TipoDeTarifa;
import com.br.FlightFacilities.models.Passagem;
import com.br.FlightFacilities.models.Voo;
import com.br.FlightFacilities.repositories.PassagemRepository;
import org.assertj.core.api.PathAssert;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.omg.CORBA.portable.ValueOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class PassagemServiceTests {
    @MockBean
    PassagemRepository passagemRepository;

    @MockBean
    VooService vooService;

    @Autowired
    PassagemService passagemService;

    Passagem passagem;
    Optional<Passagem> passagemOptionalMock;
    Iterable<Passagem> passagemIterableMock;

    Voo voo;
    Optional<Voo> vooOptionalMock;

    @BeforeEach
    public void inicializar(){
        passagem = new Passagem();
        passagem.setId(1);
        passagem.setIdVoo(1);
        passagem.setTipoDeTarifa(TipoDeTarifa.FLEX);
        passagem.setDocumentoPassageiro("123");

        voo = new Voo();
        voo.setId(1);
        voo.setValor(1000.0);
        voo.setAssentosDisponiveis(10);
        voo.setDestino("MIA");
        voo.setOrigem("GRU");
        voo.setIdEmpresa(1);

        passagemOptionalMock = Optional.of((Passagem) passagem);
        passagemIterableMock = Arrays.asList(passagem);

        vooOptionalMock = Optional.of((Voo) voo);
    }

    @Test
    public void testarSalvarPassagem(){
        Mockito.when(vooService.buscarPorId(Mockito.anyInt())).thenReturn(vooOptionalMock);
        Mockito.when(passagemRepository.save(Mockito.any(Passagem.class))).thenReturn(passagem);

        Passagem PassagemObjeto = passagemService.salvarPassagem(passagem);

        Assertions.assertEquals(passagem, PassagemObjeto);
        Assertions.assertEquals(passagem.getValorPassagem(), 1100.00);
        Assertions.assertEquals(voo.getAssentosDisponiveis(), 9);
    }

    @Test
    public void testarSalvarPassagemNaoEncontrada(){
        passagemOptionalMock = Optional.empty();

        Mockito.when(passagemService.buscarPassagem(Mockito.anyInt())).thenReturn(passagemOptionalMock);

        try {
            passagemService.salvarPassagem(passagem);
            Assertions.fail("Falha. Uma exceção deve ser lançada!");
        } catch (ObjectNotFoundException ex) {
            Assertions.assertEquals("No row with the given identifier exists: [Voo não encontrado! #class com.br.FlightFacilities.models.Passagem]",  ex.getMessage());
        }
    }

    @Test
    public void testarBuscarTodasPassagens(){
        Mockito.when(passagemRepository.findAll()).thenReturn(passagemIterableMock);

        Iterable<Passagem> passagemIterableRetorno = passagemService.buscarTodasPassagens();

        Assertions.assertEquals(passagemIterableRetorno, passagemIterableMock);
        Assertions.assertEquals(passagemIterableRetorno.iterator().next().getDocumentoPassageiro(), "123");
    }

    @Test
    public void testarBuscarPassagem(){
        Mockito.when(passagemRepository.findById(Mockito.anyInt())).thenReturn(passagemOptionalMock);

        Optional<Passagem> passagemOptionalRetorno = passagemService.buscarPassagem(1);

        Assertions.assertEquals(passagemOptionalRetorno, passagemOptionalMock);
        Assertions.assertEquals(passagemOptionalRetorno.get().getDocumentoPassageiro(), "123");
    }

    @Test
    public void testarAtualizarPassagem(){
        Mockito.when(passagemService.buscarPassagem(Mockito.anyInt())).thenReturn(passagemOptionalMock);
        Mockito.when(passagemRepository.save(Mockito.any(Passagem.class))).thenReturn(passagem);

        Passagem passagemRetorno = passagemService.atualizarPassagem(passagem);

        Assertions.assertEquals(passagemRetorno, passagem);
        Assertions.assertEquals(passagemRetorno.getDocumentoPassageiro(), passagem.getDocumentoPassageiro());
    }

    @Test
    public void testarAtualizarNaoEncontrada(){
        passagemOptionalMock = Optional.empty();

        Mockito.when(passagemService.buscarPassagem(Mockito.anyInt())).thenReturn(passagemOptionalMock);

        try {
            passagemService.atualizarPassagem(passagem);
            Assertions.fail("Falha. Uma exceção deve ser lançada!");
        } catch (ObjectNotFoundException ex) {
            Assertions.assertEquals("No row with the given identifier exists: [Passagem não encontrada! #class com.br.FlightFacilities.models.Passagem]",  ex.getMessage());
        }
    }

    @Test
    public void testarDeletarPassagemTarifaPROMO(){
        passagemOptionalMock.get().setTipoDeTarifa(TipoDeTarifa.PROMO);

        Mockito.when(passagemRepository.findById(Mockito.anyInt())).thenReturn(passagemOptionalMock);
        Mockito.when(vooService.buscarPorId(Mockito.anyInt())).thenReturn(vooOptionalMock);

        String retornoDelete = passagemService.deletarPassagem(1);

        Assertions.assertEquals("Cancelamento não permitido, tarifa PROMO!", retornoDelete);
    }

    @Test
    public void testarDeletarPassagemTarifaSTAND(){
        passagemOptionalMock.get().setValorPassagem(1000.00);
        passagemOptionalMock.get().setTipoDeTarifa(TipoDeTarifa.STAND);

        Mockito.when(passagemRepository.findById(Mockito.anyInt())).thenReturn(passagemOptionalMock);
        Mockito.when(vooService.buscarPorId(Mockito.anyInt())).thenReturn(vooOptionalMock);

        String retornoDelete = passagemService.deletarPassagem(1);

        Assertions.assertEquals("Cancelamento efetuado com multa de 100 reais, tarifa STANDARD! Valor reembolsado: 900.0", retornoDelete);
    }

    @Test
    public void testarDeletarPassagemTarifaFLEX(){
        passagemOptionalMock.get().setValorPassagem(1000.00);
        passagemOptionalMock.get().setTipoDeTarifa(TipoDeTarifa.FLEX);

        Mockito.when(passagemRepository.findById(Mockito.anyInt())).thenReturn(passagemOptionalMock);
        Mockito.when(vooService.buscarPorId(Mockito.anyInt())).thenReturn(vooOptionalMock);

        String retornoDelete = passagemService.deletarPassagem(1);

        Assertions.assertEquals("Cancelamento efetuado sem multa, tarifa FLEX! Valor reembolsado: 1000.0", retornoDelete);
    }

    @Test
    public void testarDeletarPassagemTarifaNaoEncontrada(){
        passagemOptionalMock = Optional.empty();

        Mockito.when(passagemRepository.findById(Mockito.anyInt())).thenReturn(passagemOptionalMock);
        Mockito.when(vooService.buscarPorId(Mockito.anyInt())).thenReturn(vooOptionalMock);

        String retornoDelete = passagemService.deletarPassagem(1);

        Assertions.assertEquals("Passagem não encontrada!", retornoDelete);
    }
}
