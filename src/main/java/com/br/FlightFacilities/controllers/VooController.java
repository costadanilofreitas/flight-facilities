package com.br.FlightFacilities.controllers;

import com.br.FlightFacilities.models.Voo;
import com.br.FlightFacilities.services.VooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.br.FlightFacilities.services.VooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voos")
public class VooController {

    @Autowired
    private VooService vooService;

    @GetMapping
    public Iterable<Voo> buscarTodosVoos(){
        return vooService.buscarTodosVoos();
    }

    @GetMapping("/{id}")
    public Voo buscarVoo(@PathVariable int id){
        Optional<Voo> vooOptional = vooService.buscarPorId(id);
        if (vooOptional.isPresent()){
            return vooOptional.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping()
    public ResponseEntity<Voo> adicionarVoo(@RequestBody @Valid Voo voo){
        Voo novoVoo = vooService.salvarVoo(voo);
        return ResponseEntity.status(201).body(novoVoo);
    }

    @PutMapping("/{id}")
    public Voo atualizarVoo(@PathVariable Integer id, @RequestBody Voo voo){
        voo.setId(id);
        Voo vooObjeto = vooService.atualizarVoo(voo);
        return vooObjeto;
    }

    @DeleteMapping("/{id}")
    public Voo deletarVoo(@PathVariable int id){
        Optional<Voo> vooOptional = vooService.buscarPorId(id);
        if (vooOptional.isPresent()){
            vooService.deletarVoo(vooOptional.get());
            return vooOptional.get();
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);

    }
}
