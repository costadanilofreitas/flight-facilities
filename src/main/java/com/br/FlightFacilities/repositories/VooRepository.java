package com.br.FlightFacilities.repositories;

import com.br.FlightFacilities.models.Voo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VooRepository extends CrudRepository<Voo, Integer> {

    Optional<Voo> findByOrigemAndDestino(String origem, String destino);

}
