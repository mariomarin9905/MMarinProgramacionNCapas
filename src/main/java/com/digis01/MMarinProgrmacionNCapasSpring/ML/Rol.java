package com.digis01.MMarinProgrmacionNCapasSpring.ML;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class Rol {
    
    @Min(value = 1, message = "Rol invalido")
    @Max(value = 3, message = "Rol invalido")
    private int IdRol;
    private String Nombre;

    public void setIdRol(int IdRol) {
        this.IdRol = IdRol;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;

    }

    public int getIdRol() {
        return this.IdRol;
    }

    public String getNombre() {
        return this.Nombre;
    }
}
