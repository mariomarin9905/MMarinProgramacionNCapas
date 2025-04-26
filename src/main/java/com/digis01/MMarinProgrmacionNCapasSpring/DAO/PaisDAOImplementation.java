package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.ML.Pais;
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
public class PaisDAOImplementation implements IPaisDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result PaisGetAll() {
        Result result = new Result();
        try {
            result.correct = this.jdbcTemplate.execute("{CALL PaisGetAll(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                result.objects = new ArrayList();
                while (resultSet.next()) {
                    Pais pais = new Pais();
                    pais.setIdPais(resultSet.getInt("IdPais"));
                    pais.setNombre(resultSet.getString("Nombre"));
                    result.objects.add(pais);
                }

                return true;
            });

        } catch (Exception e) {
            result.correct= false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
                    
        }
        return result;

    }

    @Override
    public Result PaisGetAllJPA() {
        Result result = new Result();
        try {
            TypedQuery<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Pais> queryPais = this.entityManager.createQuery("FROM Pais", com.digis01.MMarinProgrmacionNCapasSpring.JPA.Pais.class);
            List<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Pais> paisesJPA = queryPais.getResultList();
            result.objects = new ArrayList();
            for (com.digis01.MMarinProgrmacionNCapasSpring.JPA.Pais paisJPA : paisesJPA) {
                Pais pais = new Pais();
                pais.setIdPais(paisJPA.getIdPais());
                pais.setNombre(paisJPA.getNombre());
                result.objects.add(pais);
            }
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex =e;
        }
        return result;
    }

}
