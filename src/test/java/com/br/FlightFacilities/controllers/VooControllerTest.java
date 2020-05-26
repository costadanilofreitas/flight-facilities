package com.br.FlightFacilities.controllers;

import com.br.FlightFacilities.models.Voo;
import com.br.FlightFacilities.security.JWTUtil;
import com.br.FlightFacilities.services.UsuarioService;
import com.br.FlightFacilities.services.VooService;
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

@WebMvcTest(VooController.class)
@Import(JWTUtil.class)
public class VooControllerTest {

    @MockBean
    VooService vooService;

    @MockBean
    UsuarioService usuarioService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    Voo voo;

    @BeforeEach
    public void inicializar() {
        voo = new Voo();
        voo.setId(1);
        voo.setIdEmpresa(1);
        voo.setOrigem("THE");
        voo.setDestino("GRU");
        voo.setAssentosDisponiveis(120);
        voo.setValor(1200.0);
    }

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarCadastroDeVoo() throws Exception {

        Mockito.when(vooService.salvarVoo(Mockito.any(Voo.class))).thenReturn(voo);

        String json = objectMapper.writeValueAsString(voo);

        mockMvc.perform(MockMvcRequestBuilders.post("/voos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated()) //verifica se o status de resposta é 201 CREATED
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.equalTo(1)));
    }

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarBuscarTodosVoos() throws Exception {

        Iterable<Voo> vooIterable = Arrays.asList(voo);
        Mockito.when(vooService.buscarTodosVoos()).thenReturn(vooIterable);

        mockMvc.perform(MockMvcRequestBuilders.get("/voos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].origem", CoreMatchers.equalTo("THE")));

    }

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarBuscarVoo() throws Exception {

        Optional<Voo> vooOptional = Optional.of(voo);

        Mockito.when(vooService.buscarPorId(Mockito.any(Integer.class))).thenReturn(vooOptional);

        mockMvc.perform(MockMvcRequestBuilders.get("/voos/" + voo.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) //verifica se o status de resposta é 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.equalTo(voo.getId())));
    }

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarAtualizarVoo() throws Exception {

        voo.setValor(1100.0);

        Mockito.when(vooService.atualizarVoo(Mockito.any(Voo.class))).thenReturn(voo);

        String json = objectMapper.writeValueAsString(voo);

        mockMvc.perform(MockMvcRequestBuilders.put("/voos/"+voo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk()) //verifica se o status de resposta é 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor", CoreMatchers.equalTo(voo.getValor())));
    }

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarDeletarVoo() throws Exception {
        vooService.deletarVoo(voo);

        String json = objectMapper.writeValueAsString(voo);

        Mockito.verify(vooService, Mockito.times(1)).deletarVoo(Mockito.any(Voo.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/voos/"+voo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isNoContent()); //verifica se o status de resposta é 200 OK
    }
}
