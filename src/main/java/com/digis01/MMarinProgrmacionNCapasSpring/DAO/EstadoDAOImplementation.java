
package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.ML.Estado;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EstadoDAOImplementation implements IEstadoDAO {
    
    @Autowired
    private JdbcTemplate jdbceTemplate;
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result EstadoByIdPais(int IdPais) {
       Result result = new Result();
        try {
            result.correct = this.jdbceTemplate.execute("CALL EstadoByIdPais(?,?) ", (CallableStatementCallback<Boolean>) callableStatement ->{
                callableStatement.setInt(1, IdPais);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);                
                result.objects = new ArrayList();
                while(resultSet.next()){
                    Estado estado  = new Estado();
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre( resultSet.getString("Nombre"));                    
                    result.objects.add(estado);
                }                
                return true;
            });
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
       return result;
    }

    @Override
    public Result EstadoByIdPaisJPA(int IdPais) {
        Result result = new Result();
        try {
            TypedQuery<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Estado> queryEstado = this.entityManager.createQuery("FROM Estado WHERE Pais.IdPais = :idpais", com.digis01.MMarinProgrmacionNCapasSpring.JPA.Estado.class);
            queryEstado.setParameter("idpais", IdPais);
            List<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Estado> estadosJPA = queryEstado.getResultList();
            result.objects = new ArrayList();
            for (com.digis01.MMarinProgrmacionNCapasSpring.JPA.Estado estadoJPA : estadosJPA) {
                    Estado estado = new Estado();
                    estado.setIdEstado(estadoJPA.getIdEstado());
                    estado.setNombre(estadoJPA.getNombre());
                    result.objects.add(estado);
            }
            result.correct = true;            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }
    
    
}
