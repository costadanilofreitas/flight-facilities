package com.br.FlightFacilities.services;

import com.br.FlightFacilities.models.Empresa;
import com.br.FlightFacilities.models.Simulacao;
import com.br.FlightFacilities.models.Voo;
import com.br.FlightFacilities.repositories.EmpresaRepository;
import com.br.FlightFacilities.repositories.VooRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class EmpresaServiceTest {
    @MockBean
    EmpresaRepository empresaRepository;

    @Autowired
    EmpresaService empresaService;

    Empresa empresa;

    @BeforeEach
    public void inicializar() {
        empresa = new Empresa();
        empresa.setNome("Tam");
        empresa.setIdempresa(2);
    }

    @Test
    public void InserirEmpresa()
    {
        Mockito.when(empresaRepository.save(Mockito.any(Empresa.class))).thenReturn(empresa);
        Empresa empresabjeto = empresaService.salvarEmpresa(empresa);
        Assertions.assertEquals(empresa, empresabjeto);
    }


    @Test
    public void testarAtualizarEmpresa(){
        empresa.setNome("AzuL");

        Optional<Empresa> optionalEmpresa = Optional.of(empresa);

        Mockito.when(empresaRepository.findById(Mockito.any(Integer.class))).thenReturn(optionalEmpresa);
        Mockito.when(empresaRepository.save(Mockito.any(Empresa.class))).thenReturn(empresa);

        Empresa emprasaObjeto = empresaService.atualizarEmpresa(empresa);

        Assertions.assertEquals(empresa.getNome(), emprasaObjeto.getNome());
    }

    @Test
    public void testarBuscarPorId(){

        Optional<Empresa> optionalEmpresa = Optional.of(empresa);

        Mockito.when(empresaRepository.findById(Mockito.any(Integer.class))).thenReturn(optionalEmpresa);

        Optional<Empresa> optionalEmpresaobj =  empresaRepository.findById(1);

        Assertions.assertEquals(optionalEmpresa.get(), optionalEmpresaobj.get());
    }

    @Test
    public void testarBuscaTodos(){

        Iterable<Empresa> empresaIterable = Arrays.asList(empresa);

        Mockito.when(empresaRepository.findAll()).thenReturn(empresaIterable);

        Iterable<Empresa> empresaIterable2 = empresaService.buscarTodasEmpresas();

        Assertions.assertEquals(empresaIterable, empresaIterable2);
    }


    @Test
    public void testarDeletarEmpresa(){

        Optional<Empresa> optionalEmpresa = Optional.of(empresa);
        Mockito.when(empresaRepository.findById(Mockito.any(Integer.class))).thenReturn(optionalEmpresa);

        empresaService.deletarEmpresa(4);
        Mockito.verify(empresaRepository,Mockito.times(1)).deleteById(Mockito.anyInt());

    }

}
