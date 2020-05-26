package com.br.FlightFacilities.models;

import com.br.FlightFacilities.enums.TipoDeTarifa;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties(value = {"valorPassagem", "numeroAssento"}, allowSetters = false, allowGetters = true)
public class Passagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer idVoo;

    private String documentoPassageiro;

    private TipoDeTarifa tipoDeTarifa;

    private Double valorPassagem;

    private String numeroAssento;

    public Passagem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdVoo() {
        return idVoo;
    }

    public void setIdVoo(Integer idVoo) {
        this.idVoo = idVoo;
    }

    public String getDocumentoPassageiro() {
        return documentoPassageiro;
    }

    public void setDocumentoPassageiro(String documentoPassageiro) {
        this.documentoPassageiro = documentoPassageiro;
    }

    public TipoDeTarifa getTipoDeTarifa() {
        return tipoDeTarifa;
    }

    public void setTipoDeTarifa(TipoDeTarifa tipoDeTarifa) {
        this.tipoDeTarifa = tipoDeTarifa;
    }

    public Double getValorPassagem() {
        return valorPassagem;
    }

    public void setValorPassagem(Double valorPassagem) {
        this.valorPassagem = valorPassagem;
    }

    public String getNumeroAssento() {
        return numeroAssento;
    }

    public void setNumeroAssento(String numeroAssento) {
        this.numeroAssento = numeroAssento;
    }
}
