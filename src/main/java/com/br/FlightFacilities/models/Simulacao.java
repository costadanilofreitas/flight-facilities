package com.br.FlightFacilities.models;

import javax.validation.constraints.Size;

public class Simulacao {

    @Size(min=1, max=3, message = "Código aeroporto deve ter 3 caracteres")
    private String aeporigem;

    @Size(min=1, max=3, message = "Código aeroporto deve ter 3 caracteres")
    private String aepdestino;

    public Simulacao() {
    }

    public Simulacao(@Size(min = 1, max = 3, message = "Código aeroporto deve ter 3 caracteres") String aeporigem, @Size(min = 1, max = 3, message = "Código aeroporto deve ter 3 caracteres") String aepdestino) {
        this.aeporigem = aeporigem;
        this.aepdestino = aepdestino;
    }

    public String getAeporigem() {
        return aeporigem;
    }

    public void setAeporigem(String aeporigem) {
        this.aeporigem = aeporigem;
    }

    public String getAepdestino() {
        return aepdestino;
    }

    public void setAepdestino(String aepdestino) {
        this.aepdestino = aepdestino;
    }
}
