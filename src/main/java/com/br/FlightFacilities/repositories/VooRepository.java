package com.br.FlightFacilities.repositories;

import com.br.FlightFacilities.models.Empresa;
import com.br.FlightFacilities.models.Voo;
import org.springframework.data.repository.CrudRepository;

public interface VooRepository extends CrudRepository<Voo, Integer> {
}
