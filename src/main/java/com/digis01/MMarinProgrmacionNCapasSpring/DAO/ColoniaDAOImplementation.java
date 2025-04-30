package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.ML.Colonia;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Estado;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Municipio;
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
public class ColoniaDAOImplementation implements IColoniaDAO {

    @Autowired
    private JdbcTemplate jdbcTamplate;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result ColoniaBydIdMunicipio(int IdMunicipio) {
        Result result = new Result();
        try {
            result.correct = this.jdbcTamplate.execute("{CALL ColoniaBydIdMunicipio(?, ?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, IdMunicipio);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                result.objects = new ArrayList();
                while (resultSet.next()) {
                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombre(resultSet.getString("Nombre"));
                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    result.objects.add(colonia);
                }

                return true;
            });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
            result.objects = null;

        }

        return result;

    }

    @Override
    public Result ColoniaByCodigoPostal(String CodigoPostal) {
        Result result = new Result();
        try {
            result.correct = this.jdbcTamplate.execute("CALL ColoniaByCodigoPostal(?,?)", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setString(1, CodigoPostal);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                result.objects = new ArrayList();
                while (resultSet.next()) {
                    Colonia colonia = new Colonia();
                    colonia.Municipio = new Municipio();
                    colonia.Municipio.Estado = new Estado();
                    colonia.Municipio.Estado.Pais = new Pais();
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombre(resultSet.getString("NombreColonia"));
                    colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                    colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                    result.objects.add(colonia);
                }
                return true;
            });

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
            result.objects = null;
        }
        return result;
    }

    @Override
    public Result ColoniaBydIdMunicipioJPA(int IdMunicipio) {
        Result result = new Result();
        try {
            TypedQuery<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia> queryColonia = this.entityManager.createQuery("FROM Colonia WHERE Municipio.IdMunicipio = :idmunicipio", com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia.class);
            queryColonia.setParameter("idmunicipio", IdMunicipio);
            List<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia> colinasJPA = queryColonia.getResultList();
            result.objects = new ArrayList();
            for (com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia coloniaJPA : colinasJPA) {
                Colonia colonia = new Colonia();
                colonia.setIdColonia(coloniaJPA.getIdColonia());
                colonia.setNombre(coloniaJPA.getNombre());
                colonia.setCodigoPostal(coloniaJPA.getCodigoPostal());
                result.objects.add(colonia);
                
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

    @Override
    public Result ColoniaByCodigoPostalJPA(String CodigoPostal) {
        Result result = new Result();
        try {
            TypedQuery<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia> queryColonia = this.entityManager.createQuery("FROM Colonia WHERE CodigoPostal = :vcodigopostal", com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia.class);
            queryColonia.setParameter("vcodigopostal", CodigoPostal);
            List<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia> coloniasJPA = queryColonia.getResultList();
            result.objects = new ArrayList();
            for (com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia coloniaJPA : coloniasJPA) {
                Colonia colonia = new Colonia();
                colonia.Municipio = new Municipio();
                colonia.Municipio.Estado = new Estado();
                colonia.Municipio.Estado.Pais = new Pais();
                colonia.setIdColonia(coloniaJPA.getIdColonia());
                colonia.setNombre(coloniaJPA.getNombre());              
                colonia.Municipio.setIdMunicipio(coloniaJPA.Municipio.getIdMunicipio());
                colonia.Municipio.setNombre(coloniaJPA.Municipio.getNombre());
                colonia.Municipio.Estado.setIdEstado(coloniaJPA.Municipio.Estado.getIdEstado());                
                colonia.Municipio.Estado.Pais.setIdPais(coloniaJPA.Municipio.Estado.Pais.getIdPais());
                result.objects.add(colonia);

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
