package com.br.FlightFacilities.controllers;

import com.br.FlightFacilities.models.Simulacao;
import com.br.FlightFacilities.models.Voo;
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

@RestController
@RequestMapping("/simulacao")
public class SimulacaoController {
    @Autowired
    private SimulacaoServices simulacaoServices;

    @PutMapping()
    public ResponseEntity<Voo> realizarSimulacao(@RequestBody @Valid Simulacao simulacao){
        Voo voo;
        try{voo = simulacaoServices.consultarVoo(simulacao);}
        catch (ObjectNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.status(200).body(voo);
    }
}
