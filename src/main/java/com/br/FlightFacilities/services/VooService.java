package com.br.FlightFacilities.services;

import com.br.FlightFacilities.models.Voo;
import com.br.FlightFacilities.repositories.VooRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VooService {

    @Autowired
    private VooRepository vooRepository;

    public Optional<Voo> buscarPorId(int id) {
        Optional<Voo> optionalVoo = vooRepository.findById(id);
        return optionalVoo;
    }

    public Voo salvarVoo(Voo voo) {
        Voo vooObjeto = vooRepository.save(voo);
        return vooObjeto;
    }

    public Voo atualizarVoo(Voo voo) throws ObjectNotFoundException {
        Optional<Voo> vooOptional = buscarPorId(voo.getId());
        if (vooOptional.isPresent()) {
            Voo vooObjeto = vooRepository.save(voo);
            return vooObjeto;
        }
        throw new ObjectNotFoundException(Voo.class, "O voo não foi encontrato");
    }

    public void deletarVoo(Voo voo) {
        vooRepository.delete(voo);
    }

    public Iterable<Voo> buscarTodosVoos() {
        Iterable<Voo> voos = vooRepository.findAll();
        return voos;
    }
}
