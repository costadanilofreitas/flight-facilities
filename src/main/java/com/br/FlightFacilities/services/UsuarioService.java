package com.br.FlightFacilities.services;

import com.br.FlightFacilities.models.Usuario;
import com.br.FlightFacilities.repositories.UsuarioRepository;
import com.br.FlightFacilities.security.DetalhesUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Usuario salvarUsuario(Usuario usuario) {
        Usuario existUsuario = this.repository.findByEmail(usuario.getEmail());
        if(existUsuario != null) {
            throw new RuntimeException("Esse e-mail já existe");
        }
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return this.repository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = this.repository.findByEmail(email);
        if(usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + email);
        }
        return new DetalhesUsuario(usuario.getId(), usuario.getEmail(), usuario.getSenha());
    }
}
