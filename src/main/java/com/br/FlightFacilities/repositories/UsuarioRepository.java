package com.br.FlightFacilities.repositories;

import com.br.FlightFacilities.models.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    Usuario findByEmail(String email);
}
