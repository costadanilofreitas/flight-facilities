package com.br.FlightFacilities.controllers;

import com.br.FlightFacilities.models.Empresa;
import com.br.FlightFacilities.services.EmpresaService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    EmpresaService empresaService;

    @GetMapping
    public Iterable<Empresa> buscarTodasEmpresas()
    {
        Iterable<Empresa> empresas = empresaService.buscarTodasEmpresas();
        return empresas;
    }

    @GetMapping("/id")
    public Empresa buscarEmpresaPorId(@PathVariable int id) throws ObjectNotFoundException {
        Empresa empresa = empresaService.buscarPorId(id);
        return empresa;
    }

    @PostMapping
    public Empresa adicionarEmpresa(@RequestBody Empresa empresa) throws ObjectNotFoundException
    {
        Empresa empresaobj = empresaService.salvarEmpresa(empresa);
        return empresa;
    }

    @PutMapping("/id")
    public Empresa alterarEmpresa(@PathVariable int id, @RequestBody Empresa empresa) throws ObjectNotFoundException
    {
        Empresa emprasaAux = empresaService.buscarPorId(id);
        if(empresa.getNome().isEmpty())
        {
            empresa.setNome(emprasaAux.getNome());
        }

        Empresa empresaobj = empresaService.salvarEmpresa(empresa);
        return empresaobj;
    }

    @DeleteMapping("/id")
    public Optional<Empresa> deletarEmpresa(@PathVariable int id) throws ObjectNotFoundException {

        Optional<Empresa>  empresaaux = empresaService.deletarEmpresa(id);
        return empresaaux;
    }
}
