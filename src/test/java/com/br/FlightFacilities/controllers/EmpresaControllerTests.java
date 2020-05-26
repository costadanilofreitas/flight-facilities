package com.br.FlightFacilities.controllers;

import com.br.FlightFacilities.models.Empresa;
import com.br.FlightFacilities.security.JWTUtil;
import com.br.FlightFacilities.services.EmpresaService;
import com.br.FlightFacilities.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(EmpresaController.class)
@Import(JWTUtil.class)
public class EmpresaControllerTests {
    @MockBean
    EmpresaService empresaService;

    @MockBean
    UsuarioService usuarioService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    Empresa empresa;

    @BeforeEach
    public void inicializar() {
        empresa = new Empresa();

        empresa.setIdempresa(1);
        empresa.setNome("Latam");
    }

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarCadastroEmpresa() throws Exception {

        Mockito.when(empresaService.salvarEmpresa(Mockito.any(Empresa.class))).thenReturn(empresa);

        String json = objectMapper.writeValueAsString(empresa);

        mockMvc.perform(MockMvcRequestBuilders.post("/empresas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated()) //verifica se o status de resposta é 201 CREATED
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.equalTo(1)));
    }

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarBuscarTodosVoos() throws Exception {

        Iterable<Empresa> empresaIterable = Arrays.asList(empresa);
        Mockito.when(empresaService.buscarTodasEmpresas()).thenReturn(empresaIterable);

        mockMvc.perform(MockMvcRequestBuilders.get("/empresas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].origem", CoreMatchers.equalTo("THE")));

    }

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarBuscarEmpresa() throws Exception {
        Iterable<Empresa> empresaIterable = Arrays.asList(empresa);

        Mockito.when(empresaService.buscarTodasEmpresas()).thenReturn(empresaIterable);

        mockMvc.perform(MockMvcRequestBuilders.get("/empresas/" + empresa.getIdempresa())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) //verifica se o status de resposta é 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.equalTo(empresa.getIdempresa())));
    }

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarAtualizarEmpresa() throws Exception {

        empresa.setNome("Latam Internacional");

        Mockito.when(empresaService.atualizarEmpresa(Mockito.any(Empresa.class))).thenReturn(empresa);

        String json = objectMapper.writeValueAsString(empresa);

        mockMvc.perform(MockMvcRequestBuilders.put("/empresas/"+empresa.getIdempresa())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk()) ;
    }

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarDeletarVoo() throws Exception {
        empresaService.deletarEmpresa(empresa.getIdempresa());

        String json = objectMapper.writeValueAsString(empresa);

        Mockito.verify(empresaService, Mockito.times(1)).deletarEmpresa(empresa.getIdempresa());

        mockMvc.perform(MockMvcRequestBuilders.delete("/empresas/"+empresa.getIdempresa())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isNoContent()); //verifica se o status de resposta é 200 OK
    }
}
