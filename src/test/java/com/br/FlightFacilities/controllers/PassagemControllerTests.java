package com.br.FlightFacilities.controllers;

import com.br.FlightFacilities.enums.TipoDeTarifa;
import com.br.FlightFacilities.models.Passagem;
import com.br.FlightFacilities.models.Voo;
import com.br.FlightFacilities.security.JWTUtil;
import com.br.FlightFacilities.services.PassagemService;
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

@WebMvcTest(PassagemController.class)
@Import(JWTUtil.class)
public class PassagemControllerTests {
    @MockBean
    PassagemService passagemService;

    @MockBean
    UsuarioService usuarioService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

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
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarSalvarPassagem() throws Exception {
        Mockito.when(passagemService.salvarPassagem(Mockito.any(Passagem.class))).thenReturn(passagem);

        String json = mapper.writeValueAsString(passagem);

        mockMvc.perform(MockMvcRequestBuilders.post("/passagens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.equalTo(1)));
    }

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarBuscarTodasPassagens() throws Exception{
        Mockito.when(passagemService.buscarTodasPassagens()).thenReturn(passagemIterableMock);

        String json = mapper.writeValueAsString(passagem);

        mockMvc.perform(MockMvcRequestBuilders.get("/passagens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.equalTo(1)));

    }

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarBuscarPassagem() throws Exception{
        Mockito.when(passagemService.buscarPassagem(Mockito.anyInt())).thenReturn(passagemOptionalMock);

        String json = mapper.writeValueAsString(passagem);

        mockMvc.perform(MockMvcRequestBuilders.get("/passagens/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoDeTarifa", CoreMatchers.equalTo("FLEX")));

    }

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarBuscarPassagemNoContent() throws Exception{
        passagemOptionalMock = Optional.empty();
        Mockito.when(passagemService.buscarPassagem(Mockito.anyInt())).thenReturn(passagemOptionalMock);

        String json = mapper.writeValueAsString(passagem);

        mockMvc.perform(MockMvcRequestBuilders.get("/passagens/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarAtualizarPassagem() throws Exception{
        Mockito.when(passagemService.atualizarPassagem(Mockito.any(Passagem.class))).thenReturn(passagem);

        String json = mapper.writeValueAsString(passagem);

        mockMvc.perform(MockMvcRequestBuilders.put("/passagens/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoDeTarifa", CoreMatchers.equalTo("FLEX")));
    }

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testarDeletarPassagem() throws Exception{
        Mockito.when(passagemService.buscarPassagem(Mockito.anyInt())).thenReturn(passagemOptionalMock);
        Mockito.when(passagemService.deletarPassagem(Mockito.anyInt())).thenReturn("Cancelamento efetuado sem multa, tarifa FLEX! Valor reembolsado: " + passagemOptionalMock.get().getValorPassagem());

        String retorno = "Cancelamento efetuado sem multa, tarifa FLEX! Valor reembolsado: " + passagemOptionalMock.get().getValorPassagem();

        mockMvc.perform(MockMvcRequestBuilders.delete("/passagens/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(retorno))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}