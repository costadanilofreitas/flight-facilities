package com.br.FlightFacilities.services;

import com.br.FlightFacilities.models.Usuario;
import com.br.FlightFacilities.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class UsuarioServiceTests {
    @MockBean
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    Usuario usuario;

    @BeforeEach
    public void inicializar() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setEmail("teste@teste.com");
        usuario.setNome("teste");
        usuario.setSenha("123");
    }

    @Test
    public void testaAdicionarUsuario() {
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(null);
        usuarioService.salvarUsuario(usuario);

        Mockito.verify(usuarioRepository,Mockito.times(1)).findByEmail("teste@teste.com");
    }

    @Test
    public void testaLoadByUserName(){
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuario);
        UserDetails details = usuarioService.loadUserByUsername("teste");

        Assertions.assertEquals(details.getUsername(), usuario.getEmail());
    }
}
