package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.ML.Colonia;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Direccion;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Estado;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Municipio;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Pais;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Result;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Rol;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.Usuario;
import com.digis01.MMarinProgrmacionNCapasSpring.ML.UsuarioDireccion;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Types;
import java.util.ArrayList;

@Repository
public class UsuarioDAOImplementation implements IUsuarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result UsuarioGetAll() {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL UsuarioGetAll(?)}",
                    (CallableStatementCallback<Integer>) callableStatement -> {
                        callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                        result.objects = new ArrayList();

                        while (resultSet.next()) {

                            int idUsuario = resultSet.getInt("IdUsuario");

                            if (!result.objects.isEmpty() && idUsuario == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Usuario.getIdUsuario()) {
                                UsuarioDireccion usuario = (UsuarioDireccion) result.objects.get(result.objects.size() - 1);
                                Direccion direccion = new Direccion();
                                direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.Colonia = new Colonia();
                                direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                                direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                                direccion.Colonia.Municipio = new Municipio();
                                direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMuncipio"));
                                direccion.Colonia.Municipio.Estado = new Estado();
                                direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                                direccion.Colonia.Municipio.Estado.Pais = new Pais();
                                direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                                direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                                usuario.Direcciones.add(direccion);

                            } else {
                                UsuarioDireccion usuario = new UsuarioDireccion();
                                usuario.Usuario = new Usuario();
                                usuario.Usuario.setIdUsuario(idUsuario);
                                usuario.Usuario.setUserName(resultSet.getString("UserName"));
                                usuario.Usuario.setNombre(resultSet.getString("NombreUsuario"));
                                usuario.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                                usuario.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                                usuario.Usuario.setEmail(resultSet.getString("Email"));
                                usuario.Usuario.setPassword(resultSet.getString("Password"));
                                usuario.Usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                                usuario.Usuario.setTelefono(resultSet.getString("Telefono"));
                                usuario.Usuario.setCelular(resultSet.getString("Celular"));
                                usuario.Usuario.setCURP(resultSet.getString("CURP"));
                                usuario.Usuario.setImagen(resultSet.getString("Imagen"));
                                Rol rol = new Rol();
                                rol.setIdRol(resultSet.getInt("IdRol"));
                                rol.setNombre(resultSet.getString("NombreRol"));
                                usuario.Usuario.rol = rol;
                                if (resultSet.getString("IdDireccion") != null) {
                                    Direccion direccion = new Direccion();
                                    direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                    direccion.setCalle(resultSet.getString("Calle"));
                                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                    direccion.Colonia = new Colonia();
                                    direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                    direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                                    direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                                    direccion.Colonia.Municipio = new Municipio();
                                    direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                    direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMuncipio"));
                                    direccion.Colonia.Municipio.Estado = new Estado();
                                    direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                    direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                                    direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                                    direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                                    usuario.Direcciones = new ArrayList();
                                    usuario.Direcciones.add(direccion);
                                }
                                result.objects.add(usuario);
                            }
                        }
                        result.correct = true;
                        return 1;
                    });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
            result.objects = null;
        }
        return result;
    }

    public Result UsuarioAdd(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL UsuarioDireccionAdd(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setString(1, usuarioDireccion.Usuario.getUserName());
                callableStatement.setString(2, usuarioDireccion.Usuario.getNombre());
                callableStatement.setString(3, usuarioDireccion.Usuario.getApellidoPaterno());
                callableStatement.setString(4, usuarioDireccion.Usuario.getApellidoMaterno());
                callableStatement.setString(5, usuarioDireccion.Usuario.getEmail());
                callableStatement.setString(6, usuarioDireccion.Usuario.getPassword());
                callableStatement.setDate(7, new java.sql.Date(usuarioDireccion.Usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(8, String.valueOf(usuarioDireccion.Usuario.getSexo()));
                callableStatement.setString(9, usuarioDireccion.Usuario.getTelefono());
                callableStatement.setString(10, usuarioDireccion.Usuario.getCelular());
                callableStatement.setString(11, usuarioDireccion.Usuario.getCURP());
                callableStatement.setInt(12, usuarioDireccion.Usuario.rol.getIdRol());
                callableStatement.setString(13, usuarioDireccion.Direccion.getCalle());
                callableStatement.setString(14, usuarioDireccion.Direccion.getNumeroExterior());
                callableStatement.setString(15, usuarioDireccion.Direccion.getNumeroInterior());
                callableStatement.setInt(16, usuarioDireccion.Direccion.Colonia.getIdColonia());
                callableStatement.setString(17, usuarioDireccion.Usuario.getImagen());
                callableStatement.setInt(18, usuarioDireccion.Usuario.getEstatus());
                int rowAffected = callableStatement.executeUpdate();
                result.correct = rowAffected > 0 ? true : false;
                return 1;
            });

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    public Result DireccionesByIdUsuario(int IdUsuario) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL DireccionesByIdUsuario(?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.setInt(3, IdUsuario);
                callableStatement.execute();
                ResultSet resultSetUsuario = (ResultSet) callableStatement.getObject(1);
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                if (resultSetUsuario.next()) {
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setIdUsuario(resultSetUsuario.getInt("IdUsuario"));
                    usuarioDireccion.Usuario.setUserName(resultSetUsuario.getString("UserName"));
                    usuarioDireccion.Usuario.setNombre(resultSetUsuario.getString("NombreUsuario"));
                    usuarioDireccion.Usuario.setApellidoPaterno(resultSetUsuario.getString("ApellidoPaterno"));
                    usuarioDireccion.Usuario.setApellidoMaterno(resultSetUsuario.getString("ApellidoMaterno"));
                    usuarioDireccion.Usuario.setEmail(resultSetUsuario.getString("Email"));
                    usuarioDireccion.Usuario.setPassword(resultSetUsuario.getString("Password"));
                    usuarioDireccion.Usuario.setFechaNacimiento(resultSetUsuario.getDate("FechaNacimiento"));
                    usuarioDireccion.Usuario.setSexo(resultSetUsuario.getString("Sexo"));
                    usuarioDireccion.Usuario.setTelefono(resultSetUsuario.getString("Telefono"));
                    usuarioDireccion.Usuario.setCelular(resultSetUsuario.getString("Celular"));
                    usuarioDireccion.Usuario.setCURP(resultSetUsuario.getString("CURP"));
                    usuarioDireccion.Usuario.setImagen(resultSetUsuario.getString("Imagen"));
                    usuarioDireccion.Usuario.setEstatus(resultSetUsuario.getInt("Estatus"));
                    usuarioDireccion.Usuario.rol = new Rol();
                    usuarioDireccion.Usuario.rol.setIdRol(resultSetUsuario.getInt("IdRol"));
                    usuarioDireccion.Usuario.rol.setNombre(resultSetUsuario.getString("NombreRol"));
                    ResultSet resultSetDirecciones = (ResultSet) callableStatement.getObject(2);
                    usuarioDireccion.Direcciones = new ArrayList();
                    while (resultSetDirecciones.next()) {
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSetDirecciones.getInt("IdDireccion"));
                        direccion.setCalle(resultSetDirecciones.getString("Calle"));
                        direccion.setNumeroExterior(resultSetDirecciones.getString("NumeroExterior"));
                        direccion.setNumeroInterior(resultSetDirecciones.getString("NumeroInterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSetDirecciones.getInt("IdColonia"));
                        direccion.Colonia.setCodigoPostal(resultSetDirecciones.getString("CodigoPostal"));
                        direccion.Colonia.setNombre(resultSetDirecciones.getString("NombreColonia"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSetDirecciones.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSetDirecciones.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSetDirecciones.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSetDirecciones.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSetDirecciones.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSetDirecciones.getString("NombrePais"));
                        usuarioDireccion.Direcciones.add(direccion);
                    }
                }
                if (usuarioDireccion.Direcciones.size() == 0) {
                    usuarioDireccion.Direcciones = null;
                }
                result.object = usuarioDireccion;
                return 1;
            });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    public Result UsuarioById(int IdUsuario) {
        Result result = new Result();
        try {
            this.jdbcTemplate.execute("{CALL UsuarioGetById(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, IdUsuario);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                if (resultSet.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                    usuario.setUserName(resultSet.getString("UserName"));
                    usuario.setNombre(resultSet.getString("NombreUsuario"));
                    usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    usuario.setEmail(resultSet.getString("Email"));
                    usuario.setPassword(resultSet.getString("Password"));
                    usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                    usuario.setSexo(resultSet.getString("Sexo"));
                    usuario.setTelefono(resultSet.getString("Telefono"));
                    usuario.setCelular(resultSet.getString("Celular"));
                    usuario.setCURP(resultSet.getString("CURP"));
                    usuario.setImagen(resultSet.getString("Imagen"));
                    usuario.rol = new Rol();
                    usuario.rol.setIdRol(resultSet.getShort("IdRol"));
                    usuario.rol.setNombre(resultSet.getString("NombreRol"));
                    result.object = usuario;
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
    public Result UsuarioUpdate(Usuario usuario) {
        Result result = new Result();
        try {
            result.correct = this.jdbcTemplate.execute("CALL UsuarioUpdate(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2, usuario.getUserName());
                callableStatement.setString(3, usuario.getNombre());
                callableStatement.setString(4, usuario.getApellidoPaterno());
                callableStatement.setString(5, usuario.getApellidoMaterno());
                callableStatement.setString(6, usuario.getEmail());
                callableStatement.setString(7, usuario.getPassword());
                callableStatement.setDate(8, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(9, String.valueOf(usuario.getSexo()));
                callableStatement.setString(10, usuario.getTelefono());
                callableStatement.setString(11, usuario.getCelular());
                callableStatement.setString(12, usuario.getCURP());
                callableStatement.setInt(13, usuario.rol.getIdRol());
                callableStatement.setString(14, usuario.getImagen());
                int rowAffeted = callableStatement.executeUpdate();
                return rowAffeted > 0 ? true : false;
            });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result UsuarioDireccionDelete(int IdUsuario) {
        Result result = new Result();
        try {
            result.correct = this.jdbcTemplate.execute("CALL UsuarioDireccionDelete(?)", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, IdUsuario);
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
    public Result UsuarioUpdateByEstatus(int IdUsuario, int Estatus) {
        Result result = new Result();
        try {
            result.correct = this.jdbcTemplate.execute("CALL UsuarioUpdateByEstatus(?,?)", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, IdUsuario);
                callableStatement.setInt(2, Estatus);

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
    public Result UsuarioGetAllDinamico(String Nombre, String ApellidoPaterno, String ApellidoMaterno, int IdRol) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL UsuarioGetAllDinamico(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setString(1,Nombre);
                callableStatement.setString(2, ApellidoPaterno);
                callableStatement.setString(3, ApellidoMaterno);
                callableStatement.setInt(4, IdRol);
                callableStatement.registerOutParameter(5, Types.REF_CURSOR);                
                callableStatement.execute();                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(5);
                result.objects = new ArrayList();

                while (resultSet.next()) {

                    int idUsuario = resultSet.getInt("IdUsuario");

                    if (!result.objects.isEmpty() && idUsuario == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Usuario.getIdUsuario()) {
                        UsuarioDireccion usuario = (UsuarioDireccion) result.objects.get(result.objects.size() - 1);
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                        usuario.Direcciones.add(direccion);

                    } else {
                        UsuarioDireccion usuario = new UsuarioDireccion();
                        usuario.Usuario = new Usuario();
                        usuario.Usuario.setIdUsuario(idUsuario);
                        usuario.Usuario.setUserName(resultSet.getString("UserName"));
                        usuario.Usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuario.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuario.Usuario.setEmail(resultSet.getString("Email"));
                        usuario.Usuario.setPassword(resultSet.getString("Password"));
                        usuario.Usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuario.Usuario.setTelefono(resultSet.getString("Telefono"));
                        usuario.Usuario.setCelular(resultSet.getString("Celular"));
                        usuario.Usuario.setCURP(resultSet.getString("CURP"));
                        usuario.Usuario.setImagen(resultSet.getString("Imagen"));
                        usuario.Usuario.setEstatus(resultSet.getInt("Estatus"));
                        Rol rol = new Rol();
                        rol.setIdRol(resultSet.getInt("IdRol"));
                        rol.setNombre(resultSet.getString("NombreRol"));
                        usuario.Usuario.rol = rol;
                        if (resultSet.getString("IdDireccion") != null) {
                            Direccion direccion = new Direccion();
                            direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                            direccion.Colonia = new Colonia();
                            direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                            direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                            direccion.Colonia.Municipio = new Municipio();
                            direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                            direccion.Colonia.Municipio.Estado = new Estado();
                            direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                            direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                            direccion.Colonia.Municipio.Estado.Pais = new Pais();
                            direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                            direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                            usuario.Direcciones = new ArrayList();
                            usuario.Direcciones.add(direccion);
                        }
                        result.objects.add(usuario);
                    }
                }
                result.correct = true;
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
}
