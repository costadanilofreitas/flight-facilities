package com.br.FlightFacilities.services;

import com.br.FlightFacilities.models.Empresa;
import com.br.FlightFacilities.repositories.EmpresaRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public Iterable<Empresa> buscarTodasEmpresas(){
        Iterable<Empresa> produtosIterable = empresaRepository.findAll();
        return produtosIterable;
    }

    public Empresa salvarEmpresa(Empresa emmpresa){
        Empresa empresaObjeto = empresaRepository.save(emmpresa);
        return empresaObjeto;
    }

    public Empresa buscarPorId(int id) throws ObjectNotFoundException{
        Optional<Empresa> empresaOptional = empresaRepository.findById(id);
        if(empresaOptional.isPresent())
        {
            Empresa empresaobj = empresaOptional.get();
          return  empresaobj;
        }

        throw new ObjectNotFoundException(Empresa.class,"empresa não cadastrada");
    }

    public Empresa atualizarEmpresa(Empresa empresa) throws ObjectNotFoundException {
        Optional<Empresa> empresaOptional = empresaRepository.findById(empresa.getIdempresa());
        if (empresaOptional.isPresent()){
            Empresa empresaObjeto = empresaRepository.(empresa);
            return empresaObjeto;
        }
        throw new ObjectNotFoundException(Empresa.class,"empresa não cadastrada");

    }

    public Optional<Empresa> deletarEmpresa(int id) throws ObjectNotFoundException {
        Optional<Empresa> empresaOptional = empresaRepository.findById(id);
        if (empresaOptional.isPresent()){
            empresaRepository.deleteById(id);
            return empresaOptional;
        }
        throw new ObjectNotFoundException(Empresa.class,"empresa não cadastrada");
    }
}
