
package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.ML.Direccion;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Result;


public interface IDireccion {
    
    Result DireccionGetById(int IdDireccion);
    
    Result DireccionAdd(int IdUsuario, Direccion direccion);
    
    Result DireccionUpdate(int IdUsuario, Direccion direccion);
    Result DireccionDelete(int IdDireccion);
    //Metodos JPA
    Result DireccionDeleteJPA(int IdDireccion);      
    Result DireccionGetByIdJPA(int IdDireccion);    
    Result DireccionAddJPA(int IdUsuario, Direccion direccion);
    Result DireccionUpdateJPA(int IdUsuario,Direccion direccion);
}
