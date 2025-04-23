
package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.ML.Estado;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Result;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Types;
import java.util.ArrayList;

@Repository
public class EstadoDAOImplementation implements IEstadoDAO {
    
    @Autowired
    private JdbcTemplate jdbceTemplate;
    
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
    
    
}
