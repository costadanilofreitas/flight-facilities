package com.br.FlightFacilities.services;

import com.br.FlightFacilities.models.Simulacao;
import com.br.FlightFacilities.models.Voo;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimulacaoService {

    @Autowired
    VooRepository vooRepository;

    public Optional<Voo> consultarVoo(Simulacao simulacao) throws ObjectNotFoundException {

        Optional<Voo> vooOptional = vooRepository.findByOrigemAndDestino(simulacao.getAeporigem(),simulacao.getAepdestino());

        if (vooOptional.isPresent()){
            return vooOptional;
        }
        throw new ObjectNotFoundException(Voo.class, "Voo não encontrado");
    }
}
