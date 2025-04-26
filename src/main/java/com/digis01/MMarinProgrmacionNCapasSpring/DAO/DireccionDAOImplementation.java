package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.ML.Colonia;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Direccion;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Estado;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Municipio;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Pais;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccion {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result DireccionGetById(int IdDireccion) {
        Result result = new Result();
        try {
            this.jdbcTemplate.execute("{CALL DireccionGetById(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, IdDireccion);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                if (resultSet.next()) {
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(IdDireccion);
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                    result.object = direccion;
                }
                result.correct = true;

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
    public Result DireccionAdd(int IdUsuario, Direccion direccion) {
        Result result = new Result();
        try {
            result.correct = this.jdbcTemplate.execute("CALL DireccionAdd(?,?,?,?,?)", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setString(1, direccion.getCalle());
                callableStatement.setString(2, direccion.getNumeroInterior());
                callableStatement.setString(3, direccion.getNumeroExterior());
                callableStatement.setInt(4, direccion.Colonia.getIdColonia());
                callableStatement.setInt(5, IdUsuario);

                int rowAffected = callableStatement.executeUpdate();

                return rowAffected > 0 ? true : false;
            });

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result DireccionUpdate(int IdUsuario, Direccion direccion) {
        Result result = new Result();
        try {
            result.correct = this.jdbcTemplate.execute("CALL DireccionUpdate(?,?,?,?,?,?)", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, direccion.getIdDireccion());
                callableStatement.setString(2, direccion.getCalle());
                callableStatement.setString(3, direccion.getNumeroInterior());
                callableStatement.setString(4, direccion.getNumeroExterior());
                callableStatement.setInt(5, direccion.Colonia.getIdColonia());
                callableStatement.setInt(6, IdUsuario);
                int rowAffected = callableStatement.executeUpdate();
                return rowAffected > 0 ? true : false;
            });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;

        }
        return result;

    }

    @Override
    public Result DireccionDelete(int IdDireccion) {
        Result result = new Result();
        try {
            result.correct = this.jdbcTemplate.execute("CALL DireccionDelete(?)", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, IdDireccion);
                int rowAffected = callableStatement.executeUpdate();

                return rowAffected > 0 ? true : false;
            });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Transactional
    @Override
    public Result DireccionDeleteJPA(int IdDireccion) {
        Result result = new Result();
        try {
           com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion direccion = this.entityManager.find(com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion.class,IdDireccion );
           this.entityManager.remove(direccion);
           result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result DireccionGetByIdJPA(int IdDireccion) {
        Result result = new Result();
        try {

            TypedQuery<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion> queryDireccion = this.entityManager.createQuery("FROM Direccion WHERE IdDireccion = :iddireccion", com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion.class);
            queryDireccion.setParameter("iddireccion", IdDireccion);
            com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion direccionJPA = queryDireccion.getSingleResult();
            Direccion direccionML = new Direccion();
            direccionML.setIdDireccion(direccionJPA.getIdDireccion());
            direccionML.setCalle(direccionJPA.getCalle());
            direccionML.setNumeroExterior(direccionJPA.getNumeroExterior());
            direccionML.setNumeroInterior(direccionJPA.getNumeroInterior());
            direccionML.Colonia = new Colonia();
            direccionML.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());
            direccionML.Colonia.setNombre(direccionJPA.Colonia.getNombre());
            direccionML.Colonia.setCodigoPostal(direccionJPA.Colonia.getCodigoPostal());
            direccionML.Colonia.Municipio = new Municipio();
            direccionML.Colonia.Municipio.setIdMunicipio(direccionJPA.Colonia.Municipio.getIdMunicipio());
            direccionML.Colonia.Municipio.setNombre(direccionJPA.Colonia.Municipio.getNombre());
            direccionML.Colonia.Municipio.Estado = new Estado();
            direccionML.Colonia.Municipio.Estado.setIdEstado(direccionJPA.Colonia.Municipio.Estado.getIdEstado());
            direccionML.Colonia.Municipio.Estado.setNombre(direccionJPA.Colonia.Municipio.Estado.getNombre());
            direccionML.Colonia.Municipio.Estado.Pais = new Pais();
            direccionML.Colonia.Municipio.Estado.Pais.setIdPais(direccionJPA.Colonia.Municipio.Estado.Pais.getIdPais());
            direccionML.Colonia.Municipio.Estado.Pais.setNombre(direccionJPA.Colonia.Municipio.Estado.Pais.getNombre());
            result.object = direccionML;
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        } finally {

        }
        return result;
    }
    
    @Transactional
    @Override
    public Result DireccionAddJPA(int IdUsuario, Direccion direccion) {
        Result result = new Result();
        try {
             com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion direccionJPA = new com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion();
            direccionJPA.setCalle(direccion.getCalle());
            direccionJPA.setNumeroExterior(direccion.getNumeroExterior());
            direccionJPA.setNumeroInterior(direccion.getNumeroInterior());
            direccionJPA.Colonia = new com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia();
            direccionJPA.Colonia.setIdColonia(direccion.Colonia.getIdColonia());
            direccionJPA.setIdUsuario(IdUsuario);
            this.entityManager.persist(direccionJPA);
            result.correct = true;
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        
        return result;
    }

    @Transactional
    @Override
    public Result DireccionUpdateJPA(int IdUsuario, Direccion direccion) {
        Result result = new Result();
        try {
            com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion direccionJPA = new com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion();
            direccionJPA.setIdDireccion(direccion.getIdDireccion());
            direccionJPA.setCalle(direccion.getCalle());
            direccionJPA.setNumeroExterior(direccion.getNumeroExterior());
            direccionJPA.setNumeroInterior(direccion.getNumeroInterior());
            direccionJPA.Colonia = new com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia ();
            direccionJPA.Colonia.setIdColonia(direccion.Colonia.getIdColonia());
            direccionJPA.setIdUsuario(IdUsuario);
            this.entityManager.merge(direccionJPA);
            result.correct = true;
            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        
        
        return result;
    }

}
