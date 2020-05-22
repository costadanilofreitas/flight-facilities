package com.br.FlightFacilities.controllers;

import com.br.FlightFacilities.models.Simulacao;
import com.br.FlightFacilities.models.Voo;
import com.br.FlightFacilities.services.SimulacaoService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/simulacao")
public class SimulacaoController {
    @Autowired
    private SimulacaoService simulacaoService;

    @PutMapping()
    public ResponseEntity<Optional<Voo>> realizarSimulacao(@RequestBody @Valid Simulacao simulacao){
        Optional<Voo> vooOptional;
        try{vooOptional = simulacaoService.consultarVoo(simulacao);}
        catch (ObjectNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.status(200).body(vooOptional);
    }
}