package com.br.FlightFacilities.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ControllerSeguranca {

    @GetMapping("/aberto")
    public String endpointAberto() {
        return "endpoint aberto";
    }

    @GetMapping("/fechado")
    public String endpointFechado() {
        return "endpoint fechado";
    }
}
