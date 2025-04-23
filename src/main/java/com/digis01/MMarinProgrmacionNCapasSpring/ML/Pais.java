
package com.digis01.MMarinProgrmacionNCapasSpring.ML;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;



public class Pais {
    
    @Min(value = 4, message = "Pais invalido")
    @Max(value = 6, message = "Pais invalido")
    private int IdPais;
    private String Nombre;

    public int getIdPais() {
        return IdPais;
    }

    public void setIdPais(int IdPais) {
        this.IdPais = IdPais;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
}
