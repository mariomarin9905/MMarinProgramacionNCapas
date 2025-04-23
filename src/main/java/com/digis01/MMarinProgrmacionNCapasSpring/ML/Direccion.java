
package com.digis01.MMarinProgrmacionNCapasSpring.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;


public class Direccion {
    
    private int IdDireccion;
    @NotEmpty(message = "Error Calle vacia")    
    private String Calle; 
    @NotEmpty(message = "Error Numero Exterior vacio")
    private String NumeroExterior;
    @NotEmpty(message = "Error Numero Interiror vacio")
    private String NumeroInterior;
    @Valid
    public Colonia Colonia;

    public Colonia getColonia() {
        return Colonia;
    }

    public void setColonia(Colonia Colonia) {
        this.Colonia = Colonia;
    }

    public int getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(int IdDireccion) {
        this.IdDireccion = IdDireccion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String NumeroInterior) {
        this.NumeroInterior = NumeroInterior;
    }
    
}
