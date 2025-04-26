
package com.digis01.MMarinProgrmacionNCapasSpring.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Pais {
    @Id
    @Column(name = "idpais")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdPais;
    @Column(name = "nombre")
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
