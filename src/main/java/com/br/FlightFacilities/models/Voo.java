package com.br.FlightFacilities.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Voo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private int idEmpresa;
    private String origem;
    private String destino;
    private Double valor;
    private int assentosDisponiveis;

    public Voo() {
    }

    public Voo(int id, int idEmpresa, String origem, String destino, Double valor, int assentosDisponiveis) {
        this.id = id;
        this.idEmpresa = idEmpresa;
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
        this.assentosDisponiveis = assentosDisponiveis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public int getAssentosDisponiveis() {
        return assentosDisponiveis;
    }

    public void setAssentosDisponiveis(int assentosDisponiveis) {
        this.assentosDisponiveis = assentosDisponiveis;
    }
}
