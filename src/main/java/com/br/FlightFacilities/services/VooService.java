package com.br.FlightFacilities.services;

import com.br.FlightFacilities.repositories.VooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VooService {

    @Autowired
    private VooRepository vooRepository;


}
