package com.br.FlightFacilities.controllers;

import com.br.FlightFacilities.models.Usuario;
import com.br.FlightFacilities.security.JWTUtil;
import com.br.FlightFacilities.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebMvcTest(UsuarioController.class)
@Import(JWTUtil.class)
public class UsuarioControllerTests {

    @MockBean
    UsuarioService usuarioService;

    @Autowired
    private MockMvc mockMvc;

    Usuario usuario;

    @BeforeEach
    public void inicializar() {
         usuario = new Usuario();
         usuario.setId(1);
         usuario.setEmail("teste@teste.com");
         usuario.setNome("teste");
         usuario.setSenha("123");
    }

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "teste@gmail.com", password = "teste")
    public void testaCadastrarUsuario() throws Exception {
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);

        String json = mapper.writeValueAsString(usuario);

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());
                }
}