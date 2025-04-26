
package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.ML.Municipio;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementation implements IMunicipio {
    
    @Autowired
    private JdbcTemplate jdbcTamplate;
    
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result MunicipioByIdEstado(int IdEstado) {
        Result result = new Result ();
        try {
           result.correct = this.jdbcTamplate.execute("CALL MunicipioByIdEstado(?,?)", (CallableStatementCallback<Boolean>) callableStatement ->{
               callableStatement.setInt(1, IdEstado);
               callableStatement.registerOutParameter(2, Types.REF_CURSOR);
               callableStatement.execute();
               
               ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
              result.objects = new ArrayList();
             while(resultSet.next()){
                 Municipio municipio = new Municipio();
                 municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                 municipio.setNombre(resultSet.getString("Nombre"));
                 result.objects.add(municipio);
             }               
               return true;
           });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex =e;
        }
        return result;
    }

    @Override
    public Result MunicipioByIdEstadoJPA(int IdEstado) {
        Result result = new Result();
        try {
            TypedQuery<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Municipio> queryMunicipio = this.entityManager.createQuery("FROM Municipio WHERE Estado.IdEstado = :idestado", com.digis01.MMarinProgrmacionNCapasSpring.JPA.Municipio.class);
            queryMunicipio.setParameter("idestado", IdEstado);
            List<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Municipio> municipiosJPA = queryMunicipio.getResultList();
            result.objects = new ArrayList();
            for (com.digis01.MMarinProgrmacionNCapasSpring.JPA.Municipio municipioJPA : municipiosJPA) {
                Municipio municipio = new Municipio();
                municipio.setIdMunicipio(municipioJPA.getIdMunicipio());
                municipio.setNombre(municipioJPA.getNombre());
                result.objects.add(municipio);
            }
            result.correct = true;
        } catch (Exception e) {
            result.correct =false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        
        return result;
    }
            
    
    
}
