package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.ML.Colonia;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Direccion;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Estado;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Municipio;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Pais;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccion {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
            result.correct = this.jdbcTemplate.execute("CALL DireccionDelete(?)", (CallableStatementCallback<Boolean>) callableStatement->{
                callableStatement.setInt(1, IdDireccion);
                int rowAffected = callableStatement.executeUpdate();                
                
                return rowAffected >0 ? true: false;
            });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        
        return result;
    }

}
