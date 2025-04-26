
package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.ML.Result;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Types;
import java.util.ArrayList;

@Repository
public class RolDAOImplementation implements IRolDAO {
     
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result RolGetAll(){
        Result result = new Result();
        try {
            result.object = jdbcTemplate.execute("{CALL RolGetAll(?)}", (CallableStatementCallback<List<Rol>>) callableStatement ->{
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet= (ResultSet)callableStatement.getObject(1);
                List<Rol> roles = new ArrayList<>();
                while(resultSet.next()){
                    Rol rol = new Rol();
                    rol.setIdRol(resultSet.getInt("IdRol"));
                    rol.setNombre(resultSet.getString("Nombre"));
                    roles.add(rol);
                }               
                result.correct = true;
                return roles;
            });
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage =e.getLocalizedMessage();
            result.ex = e;
        }
        return result; 
    }

    @Override
    public Result RolGetAllJPA() {
        Result result = new Result();
        try {
            TypedQuery<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Rol> queryRol = this.entityManager.createQuery("FROM Rol", com.digis01.MMarinProgrmacionNCapasSpring.JPA.Rol.class);
            List<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Rol> rolJPA =queryRol.getResultList();
            result.objects = new ArrayList();
            for (com.digis01.MMarinProgrmacionNCapasSpring.JPA.Rol rol : rolJPA) {
                Rol rolML = new Rol();
                rolML.setIdRol(rol.getIdRol());
                rolML.setNombre(rol.getNombre());
                result.objects.add(rolML);
                
            }
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
            result.objects = null;
        }
        return result;
    }
}
