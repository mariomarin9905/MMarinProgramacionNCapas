
package com.digis01.MMarinProgrmacionNCapasSpring.ML;

import jakarta.validation.Valid;

public class Municipio {
    private int IdMunicipio;
    private String Nombre;
    @Valid
    public Estado Estado;

    public Estado getEstado() {
        return Estado;
    }

    public void setEstado(Estado Estado) {
        this.Estado = Estado;
    }

    public int getIdMunicipio() {
        return IdMunicipio;
    }

    public void setIdMunicipio(int IdMunicipio) {
        this.IdMunicipio = IdMunicipio;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
}
