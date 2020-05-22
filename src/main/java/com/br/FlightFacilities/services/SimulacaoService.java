package com.br.FlightFacilities.services;

import com.br.FlightFacilities.models.Simulacao;
import com.br.FlightFacilities.models.Voo;
import com.br.FlightFacilities.repositories.VooRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimulacaoService {

    @Autowired
    VooRepository vooRepository;

    public Iterable<Voo> consultarVoo(Simulacao simulacao) throws ObjectNotFoundException {

        Iterable<Voo> vooIterable = vooRepository.findByOrigemAndDestino(simulacao.getAeporigem(),simulacao.getAepdestino());

        if (vooIterable.iterator().hasNext()){
            return vooIterable;
        }
        throw new ObjectNotFoundException(Voo.class, "Voo não encontrado");
    }
 }
