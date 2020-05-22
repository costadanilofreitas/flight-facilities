package com.br.FlightFacilities.controllers;

import com.br.FlightFacilities.services.VooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voos")
public class VooController {

    @Autowired
    private VooService vooService;


}
