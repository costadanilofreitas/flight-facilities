package com.br.FlightFacilities.services;

import com.br.FlightFacilities.models.Passagem;
import com.br.FlightFacilities.models.Voo;
import com.br.FlightFacilities.repositories.PassagemRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PassagemService {
    @Autowired
    PassagemRepository passagemRepository;

    public Passagem salvarPassagem(Passagem passagem){
        return passagemRepository.save(passagem);
    }

    public Optional<Passagem> buscarPassagem(Integer idPassagem){
        return passagemRepository.findById(idPassagem);
    }

    public Iterable<Passagem> buscarTodasPassagens(){
        return passagemRepository.findAll();
    }

    public Passagem atualizarPassagem(Passagem passagem){
        Optional<Passagem> optionalPassagem = passagemRepository.findById(passagem.getId());

        if (optionalPassagem.isPresent()){
            Passagem passagemData = optionalPassagem.get();

            if (passagem.getIdVoo() == null){
                passagem.setIdVoo(passagemData.getIdVoo());
            }

            if (passagem.getDocumentoPassageiro() == null){
                passagem.setDocumentoPassageiro(passagemData.getDocumentoPassageiro());
            }

            if (passagem.getTipoDeTarifa() == null){
                passagem.setTipoDeTarifa(passagemData.getTipoDeTarifa());
            }

            if (passagem.getValorPassagem() == null){
                passagem.setValorPassagem(passagemData.getValorPassagem());
            }

            if (passagem.getNumeroAssento() == null){
                passagem.setNumeroAssento(passagemData.getNumeroAssento());
            }
        }else{
            throw new ObjectNotFoundException(Passagem.class, "Passagem não encontrada");
        }
        return passagemRepository.save(passagem);
    }

    public void deletarPassagem(Passagem passagem){
        passagemRepository.delete(passagem);
    }

}
