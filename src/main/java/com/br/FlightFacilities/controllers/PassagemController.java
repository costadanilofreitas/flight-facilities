package com.br.FlightFacilities.controllers;

import com.br.FlightFacilities.models.Passagem;
import com.br.FlightFacilities.services.PassagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/passagem")
public class PassagemController {
    @Autowired
    PassagemService passagemService;

    @PostMapping("/")
    public ResponseEntity<Passagem> salvarPassagem(@RequestBody @Valid Passagem passagem){
        Passagem passagemRetorno = passagemService.salvarPassagem(passagem);
        return ResponseEntity.status(201).body(passagemRetorno);
    }

    @GetMapping("/{id}")
    public Passagem buscarPassagem(@PathVariable Integer id){
        Optional<Passagem> passagemOptional = passagemService.buscarPassagem(id);

        if(passagemOptional.isPresent()){
            return passagemOptional.get();
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public Iterable<Passagem> buscarTodasPassagens(){
        return passagemService.buscarTodasPassagens();
    }

    @PutMapping("/{id}")
    public Passagem atualizarPassagem(@PathVariable Integer id, @RequestBody Passagem passagem){
        passagem.setId(id);

        try{
            Passagem passagemRetorno = passagemService.atualizarPassagem(passagem);
            return passagemRetorno;
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Passagem> deletarPassagem(@PathVariable Integer id){
        Optional<Passagem> passagemOptional = passagemService.buscarPassagem(id);

        if(passagemOptional.isPresent()){
            passagemService.deletarPassagem(passagemOptional.get());
            return ResponseEntity.status(204).body(passagemOptional.get());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

}
