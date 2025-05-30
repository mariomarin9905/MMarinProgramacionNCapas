package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.ML.Result;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Usuario;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.UsuarioDireccion;

public interface IUsuarioDAO {

    Result UsuarioGetAll();

    Result UsuarioAdd(UsuarioDireccion usuarioDireccion);

    Result DireccionesByIdUsuario(int IdUsuario);

    Result UsuarioById(int IdUsuario);

    Result UsuarioUpdate(Usuario usuario);

    Result UsuarioDireccionDelete(int IdUsuario);

    Result UsuarioUpdateByEstatus(int IdUsuario, int Estatus);

    Result UsuarioGetAllDinamico(String Nombre, String ApellidoPaterno, String ApellidoMaterno, int IdRol);

    //Metods JPA
    Result UsuarioGetAllJPA();

    Result UsuarioAddJPA(UsuarioDireccion usuarioDireccion);

    Result DireccionesByIdUsuarioJPA(int IdUsuario);

    Result UsuarioByIdJPA(int IdUsuario);

    Result UsuarioUpdateJPA(Usuario usuario);

    Result UsuarioDireccionDeleteJPA(int IdUsuario);
    
    Result UsuarioUpdateByEstatusJPA(int IdUsuario, int Estatus);
    
   Result UsuarioGetAllDinamicoJPA(Usuario usuario);
    
}
