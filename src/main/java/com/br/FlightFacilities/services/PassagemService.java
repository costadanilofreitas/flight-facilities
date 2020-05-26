package com.br.FlightFacilities.services;

import com.br.FlightFacilities.enums.TipoDeTarifa;
import com.br.FlightFacilities.models.Passagem;
import com.br.FlightFacilities.models.Voo;
import com.br.FlightFacilities.repositories.PassagemRepository;
import net.bytebuddy.utility.RandomString;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Optional;

@Service
public class PassagemService {
    @Autowired
    PassagemRepository passagemRepository;

    @Autowired
    VooService vooService;

    public Passagem salvarPassagem(Passagem passagem){
        Optional<Voo> vooOptional = vooService.buscarPorId(passagem.getIdVoo());

        if(vooOptional.isPresent()) {
            if (passagem.getTipoDeTarifa() == TipoDeTarifa.PROMO) {
                passagem.setValorPassagem(vooOptional.get().getValor()*0.8);
            }
            if (passagem.getTipoDeTarifa() == TipoDeTarifa.STAND) {
                passagem.setValorPassagem(vooOptional.get().getValor());
            }
            if (passagem.getTipoDeTarifa() == TipoDeTarifa.FLEX) {
                passagem.setValorPassagem(vooOptional.get().getValor()*1.1);
            }
            passagem.setNumeroAssento(RandomString.make(2));
            Passagem passagemObjeto = passagemRepository.save(passagem);

            vooOptional.get().setAssentosDisponiveis(vooOptional.get().getAssentosDisponiveis()-1);
            vooService.atualizarVoo(vooOptional.get());

            return passagemObjeto;
        }else {
            throw new ObjectNotFoundException(Passagem.class, "Voo não encontrado! ");
        }
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
            throw new ObjectNotFoundException(Passagem.class, "Passagem não encontrada! ");
        }
        return passagemRepository.save(passagem);
    }

    public String deletarPassagem(Integer id) {
        Optional<Passagem> passagemOptional = passagemRepository.findById(id);

        if (passagemOptional.isPresent()) {
            Optional<Voo> vooOptional = vooService.buscarPorId(passagemOptional.get().getIdVoo());

            if (passagemOptional.get().getTipoDeTarifa() == TipoDeTarifa.PROMO) {
                return "Cancelamento não permitido, tarifa PROMO!";
            }
            if (passagemOptional.get().getTipoDeTarifa() == TipoDeTarifa.STAND) {
                passagemRepository.delete(passagemOptional.get());
                vooOptional.get().setAssentosDisponiveis(vooOptional.get().getAssentosDisponiveis()+1);
                vooService.atualizarVoo(vooOptional.get());

                return "Cancelamento efetuado com multa de 100 reais, tarifa STANDARD! Valor reembolsado: " + (passagemOptional.get().getValorPassagem()-100.00);
            }
            if (passagemOptional.get().getTipoDeTarifa() == TipoDeTarifa.FLEX) {
                passagemRepository.delete(passagemOptional.get());
                vooOptional.get().setAssentosDisponiveis(vooOptional.get().getAssentosDisponiveis()+1);
                vooService.atualizarVoo(vooOptional.get());

                return "Cancelamento efetuado sem multa, tarifa FLEX! Valor reembolsado: " + (passagemOptional.get().getValorPassagem());
            }
        }
        return "Passagem não encontrada!";
    }
}
