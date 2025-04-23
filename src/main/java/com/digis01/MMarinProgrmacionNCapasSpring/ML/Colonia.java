
package com.digis01.MMarinProgrmacionNCapasSpring.ML;

import jakarta.validation.Valid;

public class Colonia {
    private int IdColonia;
    private String Nombre;
    private String CodigoPostal;
    @Valid
    public Municipio Municipio;

    public Municipio getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(Municipio Municipio) {
        this.Municipio = Municipio;
    }

    public int getIdColonia() {
        return IdColonia;
    }

    public void setIdColonia(int IdColonia) {
        this.IdColonia = IdColonia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String CodigoPostal) {
        this.CodigoPostal = CodigoPostal;
    }
}
