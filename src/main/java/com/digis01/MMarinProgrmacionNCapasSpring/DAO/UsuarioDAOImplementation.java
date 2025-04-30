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
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDAOImplementation implements IUsuarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

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
                callableStatement.setString(1, Nombre);
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

    @Override
    public Result UsuarioGetAllJPA() {
        Result result = new Result();
        try {
            TypedQuery<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario> queryUsuarios = this.entityManager.createQuery("FROM Usuario U ORDER BY U.IdUsuario", com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario.class);
            List<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario> usuariosJPA = queryUsuarios.getResultList();
            result.objects = new ArrayList();
            for (com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario usuario : usuariosJPA) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();
                usuarioDireccion.Usuario.setIdUsuario(usuario.getIdUsuario());
                usuarioDireccion.Usuario.setUserName(usuario.getUserName());
                usuarioDireccion.Usuario.setNombre(usuario.getNombre());
                usuarioDireccion.Usuario.setApellidoPaterno(usuario.getApellidoPaterno());
                usuarioDireccion.Usuario.setApellidoMaterno(usuario.getApellidoMaterno());
                usuarioDireccion.Usuario.setEmail(usuario.getEmail());
                usuarioDireccion.Usuario.setPassword(usuario.getPassword());
                usuarioDireccion.Usuario.setFechaNacimiento(usuario.getFechaNacimiento());
                usuarioDireccion.Usuario.setSexo(usuario.getSexo());
                usuarioDireccion.Usuario.setTelefono(usuario.getTelefono());
                usuarioDireccion.Usuario.setCelular(usuario.getCelular());
                usuarioDireccion.Usuario.setCURP(usuario.getCURP());
                usuarioDireccion.Usuario.setImagen(usuario.getImagen());
                usuarioDireccion.Usuario.setEstatus(usuario.getEstatus());
                usuarioDireccion.Usuario.rol = new Rol();
                usuarioDireccion.Usuario.rol.setIdRol(usuario.rol.getIdRol());
                usuarioDireccion.Usuario.rol.setNombre(usuario.rol.getNombre());
                String consutla = "FROM Direccion WHERE IdUsuario = " + usuario.getIdUsuario();
                TypedQuery<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion> queryDireccion = this.entityManager.createQuery(consutla, com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion.class);
                List<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion> direccionesJPA = queryDireccion.getResultList();
                if (direccionesJPA.size() > 0) {
                    usuarioDireccion.Direcciones = new ArrayList();
                    for (com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion direccion : direccionesJPA) {
                        Direccion direccionML = new Direccion();
                        direccionML.setIdDireccion(direccion.getIdDireccion());
                        direccionML.setCalle(direccion.getCalle());
                        direccionML.setNumeroExterior(direccion.getNumeroExterior());
                        direccionML.setNumeroInterior(direccion.getNumeroInterior());
                        direccionML.Colonia = new Colonia();
                        direccionML.Colonia.setIdColonia(direccion.Colonia.getIdColonia());
                        direccionML.Colonia.setNombre(direccion.Colonia.getNombre());
                        direccionML.Colonia.setCodigoPostal(direccion.Colonia.getCodigoPostal());
                        direccionML.Colonia.Municipio = new Municipio();
                        direccionML.Colonia.Municipio.setIdMunicipio(direccion.Colonia.Municipio.getIdMunicipio());
                        direccionML.Colonia.Municipio.setNombre(direccion.Colonia.Municipio.getNombre());
                        direccionML.Colonia.Municipio.Estado = new Estado();
                        direccionML.Colonia.Municipio.Estado.setIdEstado(direccion.Colonia.Municipio.Estado.getIdEstado());
                        direccionML.Colonia.Municipio.Estado.setNombre(direccion.Colonia.Municipio.Estado.getNombre());
                        direccionML.Colonia.Municipio.Estado.Pais = new Pais();
                        direccionML.Colonia.Municipio.Estado.Pais.setIdPais(direccion.Colonia.Municipio.Estado.Pais.getIdPais());
                        direccionML.Colonia.Municipio.Estado.Pais.setNombre(direccion.Colonia.Municipio.Estado.Pais.getNombre());
                        usuarioDireccion.Direcciones.add(direccionML);

                    }
                }

                result.objects.add(usuarioDireccion);

            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
            result.objects = null;
        }
        return result;
    }

    @Transactional
    @Override
    public Result UsuarioAddJPA(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {
            com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario usuarioJPA = new com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario();
            usuarioJPA.setUserName(usuarioDireccion.Usuario.getUserName());
            usuarioJPA.setNombre(usuarioDireccion.Usuario.getNombre());
            usuarioJPA.setApellidoPaterno(usuarioDireccion.Usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuarioDireccion.Usuario.getApellidoMaterno());
            usuarioJPA.setEmail(usuarioDireccion.Usuario.getEmail());
            usuarioJPA.setPassword(usuarioDireccion.Usuario.getPassword());
            usuarioJPA.setFechaNacimiento(usuarioDireccion.Usuario.getFechaNacimiento());
            usuarioJPA.setSexo(usuarioDireccion.Usuario.getSexo());
            usuarioJPA.setTelefono(usuarioDireccion.Usuario.getTelefono());
            usuarioJPA.setCelular(usuarioDireccion.Usuario.getCelular());
            usuarioJPA.setCURP(usuarioDireccion.Usuario.getCURP());
            usuarioJPA.setImagen(usuarioDireccion.Usuario.getImagen());
            usuarioJPA.setEstatus(usuarioDireccion.Usuario.getEstatus());
            usuarioJPA.rol = new com.digis01.MMarinProgrmacionNCapasSpring.JPA.Rol();
            usuarioJPA.rol.setIdRol(usuarioDireccion.Usuario.rol.getIdRol());
            this.entityManager.persist(usuarioJPA);
            com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion direccionJPA = new com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion();
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
            direccionJPA.Colonia = new com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia();
            direccionJPA.Colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());
            direccionJPA.setIdUsuario(usuarioJPA.getIdUsuario());
            this.entityManager.persist(direccionJPA);
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result DireccionesByIdUsuarioJPA(int IdUsuario) {
        Result result = new Result();
        try {
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario usuarioJPA = this.entityManager.find(com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario.class, IdUsuario);
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
            usuarioDireccion.Usuario.setNombre(usuarioJPA.getNombre());
            usuarioDireccion.Usuario.setApellidoPaterno(usuarioJPA.getApellidoPaterno());
            usuarioDireccion.Usuario.setApellidoMaterno(usuarioJPA.getApellidoMaterno());
            usuarioDireccion.Usuario.setEmail(usuarioJPA.getEmail());
            usuarioDireccion.Usuario.setPassword(usuarioJPA.getPassword());
            usuarioDireccion.Usuario.setUserName(usuarioJPA.getUserName());
            usuarioDireccion.Usuario.setFechaNacimiento(usuarioJPA.getFechaNacimiento());
            usuarioDireccion.Usuario.setSexo(usuarioJPA.getSexo());
            usuarioDireccion.Usuario.setTelefono(usuarioJPA.getTelefono());
            usuarioDireccion.Usuario.setCelular(usuarioJPA.getCelular());
            usuarioDireccion.Usuario.setCURP(usuarioJPA.getCURP());
            usuarioDireccion.Usuario.setImagen(usuarioJPA.getImagen());
            usuarioDireccion.Usuario.setEstatus(usuarioJPA.getEstatus());
            usuarioDireccion.Usuario.rol = new Rol();
            usuarioDireccion.Usuario.rol.setIdRol(usuarioJPA.rol.getIdRol());
            usuarioDireccion.Usuario.rol.setNombre(usuarioJPA.rol.getNombre());
            TypedQuery<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion> queryDireccion = this.entityManager.createQuery("FROM Direccion WHERE IdUsuario = :idusuario", com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion.class);
            queryDireccion.setParameter("idusuario", IdUsuario);
            List<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion> direccionesJPA = queryDireccion.getResultList();
            if (direccionesJPA.size() > 0) {
                usuarioDireccion.Direcciones = new ArrayList();
                for (com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion direccionJPA : direccionesJPA) {
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(direccionJPA.getIdDireccion());
                    direccion.setCalle(direccionJPA.getCalle());
                    direccion.setNumeroExterior(direccionJPA.getNumeroExterior());
                    direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());
                    direccion.Colonia.setNombre(direccionJPA.Colonia.getNombre());
                    direccion.Colonia.setCodigoPostal(direccionJPA.Colonia.getCodigoPostal());
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.setIdMunicipio(direccionJPA.Colonia.Municipio.getIdMunicipio());
                    direccion.Colonia.Municipio.setNombre(direccionJPA.Colonia.Municipio.getNombre());
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.setIdEstado(direccionJPA.Colonia.Municipio.Estado.getIdEstado());
                    direccion.Colonia.Municipio.Estado.setNombre(direccionJPA.Colonia.Municipio.Estado.getNombre());
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    direccion.Colonia.Municipio.Estado.Pais.setIdPais(direccionJPA.Colonia.Municipio.Estado.Pais.getIdPais());
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(direccionJPA.Colonia.Municipio.Estado.Pais.getNombre());
                    usuarioDireccion.Direcciones.add(direccion);
                }
            }
            result.object = usuarioDireccion;
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result UsuarioByIdJPA(int IdUsuario) {

        Result result = new Result();
        try {
            com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario usuarioJPA = this.entityManager.find(com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario.class, IdUsuario);
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(usuarioJPA.getIdUsuario());
            usuario.setNombre(usuarioJPA.getNombre());
            usuario.setApellidoPaterno(usuarioJPA.getApellidoPaterno());
            usuario.setApellidoMaterno(usuarioJPA.getApellidoMaterno());
            usuario.setUserName(usuarioJPA.getUserName());
            usuario.setEmail(usuarioJPA.getEmail());
            usuario.setPassword(usuarioJPA.getPassword());
            usuario.setFechaNacimiento(usuarioJPA.getFechaNacimiento());
            usuario.setSexo(usuarioJPA.getSexo());
            usuario.setTelefono(usuarioJPA.getTelefono());
            usuario.setCelular(usuarioJPA.getCelular());
            usuario.setCURP(usuarioJPA.getCURP());
            usuario.setImagen(usuarioJPA.getImagen());
            usuario.setEstatus(usuarioJPA.getEstatus());
            usuario.rol = new Rol();
            usuario.rol.setIdRol(usuarioJPA.rol.getIdRol());
            usuario.rol.setNombre(usuarioJPA.rol.getNombre());
            result.object = usuario;
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
    public Result UsuarioUpdateJPA(Usuario usuario) {
        Result result = new Result();
        try {
            com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario usuarioJPA = new com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario();
            usuarioJPA.setIdUsuario(usuario.getIdUsuario());
            usuarioJPA.setUserName(usuario.getUserName());
            usuarioJPA.setNombre(usuario.getNombre());
            usuarioJPA.setApellidoPaterno(usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuario.getApellidoMaterno());
            usuarioJPA.setEmail(usuario.getEmail());
            usuarioJPA.setPassword(usuario.getPassword());
            usuarioJPA.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioJPA.setSexo(usuario.getSexo());
            usuarioJPA.setTelefono(usuario.getTelefono());
            usuarioJPA.setCelular(usuario.getCelular());
            usuarioJPA.setCURP(usuario.getCURP());
            usuarioJPA.setImagen(usuario.getImagen());
            usuarioJPA.setEstatus(usuario.getEstatus());
            usuarioJPA.rol = new com.digis01.MMarinProgrmacionNCapasSpring.JPA.Rol();
            usuarioJPA.rol.setIdRol(usuario.rol.getIdRol());

            this.entityManager.merge(usuarioJPA);
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
    public Result UsuarioDireccionDeleteJPA(int IdUsuario) {
        Result result = new Result();
        try {
            TypedQuery<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion> queryDireccion = this.entityManager.createQuery("FROM Direccion WHERE IdUsuario = :idusuario", com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion.class);
            queryDireccion.setParameter("idusuario", IdUsuario);
            List<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion> direcciones = queryDireccion.getResultList();
            for (com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion direccion : direcciones) {
                this.entityManager.remove(direccion);
            }
            com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario usuario = this.entityManager.find(com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario.class, IdUsuario);
            this.entityManager.remove(usuario);
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Transactional
    @Override
    public Result UsuarioUpdateByEstatusJPA(int IdUsuario, int Estatus) {
        Result result = new Result();
        try {
            com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario usuarioJPA = this.entityManager.find(com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario.class, IdUsuario);
            usuarioJPA.setEstatus(Estatus);
            this.entityManager.merge(usuarioJPA);
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result UsuarioGetAllDinamicoJPA(Usuario usuario) {
        Result result = new Result();
        try {
            String queryBase = "FROM Usuario U";
            String queryNombre = " WHERE LOWER(U.Nombre) LIKE '%'||:nombre||'%'";
            String queryApellidoPaterno = " AND LOWER(U.ApellidoPaterno) LIKE '%'|| :apellidopaterno||'%' ";
            String queryApellidoMaterno = " AND LOWER(U.ApellidoMaterno) LIKE  '%'||:apellidomaterno||'%'";
            TypedQuery<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario> queryUsuarios;
            if (usuario.rol.getIdRol() == 0 && usuario.getEstatus() == -1) {
                queryUsuarios = this.entityManager.createQuery(queryBase + queryNombre + queryApellidoPaterno + queryApellidoMaterno + " ORDER BY U.IdUsuario ",
                        com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario.class);
                queryUsuarios.setParameter("nombre", usuario.getNombre().toLowerCase());
                queryUsuarios.setParameter("apellidopaterno", usuario.getApellidoPaterno().toLowerCase());
                queryUsuarios.setParameter("apellidomaterno", usuario.getApellidoMaterno().toLowerCase());

            } else {
                if (usuario.rol.getIdRol() > 0 && usuario.getEstatus() == -1) {
                    String queryRol = " AND U.rol.IdRol = :idrol ";
                    String queryCompleto = queryBase + queryNombre + queryApellidoPaterno + queryApellidoMaterno + queryRol + "ORDER BY U.IdUsuario";

                    queryUsuarios = this.entityManager.createQuery(queryCompleto, com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario.class);
                    queryUsuarios.setParameter("nombre", usuario.getNombre().toLowerCase());
                    queryUsuarios.setParameter("apellidopaterno", usuario.getApellidoPaterno().toLowerCase());
                    queryUsuarios.setParameter("apellidomaterno", usuario.getApellidoMaterno().toLowerCase());
                    queryUsuarios.setParameter("idrol", usuario.rol.getIdRol());
                } else if (usuario.rol.getIdRol() == 0) {
                    String queryEstatus = " AND U.Estatus = :estatus ";
                    String queryCompleto = queryBase + queryNombre + queryApellidoPaterno + queryApellidoMaterno + queryEstatus + "ORDER BY U.IdUsuario";
                    queryUsuarios = this.entityManager.createQuery(queryCompleto, com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario.class);
                    queryUsuarios.setParameter("nombre", usuario.getNombre().toLowerCase());
                    queryUsuarios.setParameter("apellidopaterno", usuario.getApellidoPaterno().toLowerCase());
                    queryUsuarios.setParameter("apellidomaterno", usuario.getApellidoMaterno().toLowerCase());
                    queryUsuarios.setParameter("estatus", usuario.getEstatus());

                } else {
                    String queryRol = " AND U.rol.IdRol = :idrol";
                    String queryEstatus = " AND U.Estatus =  :estatus ";
                    String queryCompleto = queryBase + queryNombre + queryApellidoPaterno + queryApellidoMaterno + queryRol + queryEstatus + "ORDER BY U.IdUsuario";
                    queryUsuarios = this.entityManager.createQuery(queryCompleto, com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario.class);
                    queryUsuarios.setParameter("nombre", usuario.getNombre().toLowerCase());
                    queryUsuarios.setParameter("apellidopaterno", usuario.getApellidoPaterno().toLowerCase());
                    queryUsuarios.setParameter("apellidomaterno", usuario.getApellidoMaterno().toLowerCase());
                    queryUsuarios.setParameter("estatus", usuario.getEstatus());
                    queryUsuarios.setParameter("idrol", usuario.rol.getIdRol());

                }

            }
            List<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario> usuariosJPA = queryUsuarios.getResultList();
            result.objects = new ArrayList();
            for (com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario usuarioJPA : usuariosJPA) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();
                usuarioDireccion.Usuario.setIdUsuario(usuarioJPA.getIdUsuario());
                usuarioDireccion.Usuario.setUserName(usuarioJPA.getUserName());
                usuarioDireccion.Usuario.setNombre(usuarioJPA.getNombre());
                usuarioDireccion.Usuario.setApellidoPaterno(usuarioJPA.getApellidoPaterno());
                usuarioDireccion.Usuario.setApellidoMaterno(usuarioJPA.getApellidoMaterno());
                usuarioDireccion.Usuario.setEmail(usuarioJPA.getEmail());
                usuarioDireccion.Usuario.setPassword(usuarioJPA.getPassword());
                usuarioDireccion.Usuario.setFechaNacimiento(usuarioJPA.getFechaNacimiento());
                usuarioDireccion.Usuario.setSexo(usuarioJPA.getSexo());
                usuarioDireccion.Usuario.setTelefono(usuarioJPA.getTelefono());
                usuarioDireccion.Usuario.setCelular(usuarioJPA.getCelular());
                usuarioDireccion.Usuario.setCURP(usuarioJPA.getCURP());
                usuarioDireccion.Usuario.setImagen(usuarioJPA.getImagen());
                usuarioDireccion.Usuario.setEstatus(usuarioJPA.getEstatus());
                usuarioDireccion.Usuario.rol = new Rol();
                usuarioDireccion.Usuario.rol.setIdRol(usuarioJPA.rol.getIdRol());
                usuarioDireccion.Usuario.rol.setNombre(usuarioJPA.rol.getNombre());
                
                TypedQuery<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion> queryDireccion = this.entityManager.createQuery("FROM Direccion WHERE IdUsuario = :idusuario", com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion.class);
                queryDireccion.setParameter("idusuario", usuarioJPA.getIdUsuario());
                List<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion> direccionesJPA = queryDireccion.getResultList();
                if (direccionesJPA.size() > 0) {
                    usuarioDireccion.Direcciones = new ArrayList();
                    for (com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion direccion : direccionesJPA) {
                        Direccion direccionML = new Direccion();
                        direccionML.setIdDireccion(direccion.getIdDireccion());
                        direccionML.setCalle(direccion.getCalle());
                        direccionML.setNumeroExterior(direccion.getNumeroExterior());
                        direccionML.setNumeroInterior(direccion.getNumeroInterior());
                        direccionML.Colonia = new Colonia();
                        direccionML.Colonia.setIdColonia(direccion.Colonia.getIdColonia());
                        direccionML.Colonia.setNombre(direccion.Colonia.getNombre());
                        direccionML.Colonia.setCodigoPostal(direccion.Colonia.getCodigoPostal());
                        direccionML.Colonia.Municipio = new Municipio();
                        direccionML.Colonia.Municipio.setIdMunicipio(direccion.Colonia.Municipio.getIdMunicipio());
                        direccionML.Colonia.Municipio.setNombre(direccion.Colonia.Municipio.getNombre());
                        direccionML.Colonia.Municipio.Estado = new Estado();
                        direccionML.Colonia.Municipio.Estado.setIdEstado(direccion.Colonia.Municipio.Estado.getIdEstado());
                        direccionML.Colonia.Municipio.Estado.setNombre(direccion.Colonia.Municipio.Estado.getNombre());
                        direccionML.Colonia.Municipio.Estado.Pais = new Pais();
                        direccionML.Colonia.Municipio.Estado.Pais.setIdPais(direccion.Colonia.Municipio.Estado.Pais.getIdPais());
                        direccionML.Colonia.Municipio.Estado.Pais.setNombre(direccion.Colonia.Municipio.Estado.Pais.getNombre());
                        usuarioDireccion.Direcciones.add(direccionML);

                    }
                }

                result.objects.add(usuarioDireccion);
               
            }
            if(result.objects.size()==0){
                result.correct = false;
                result.objects = null;
            }else{
                result.correct = true;
            }
             
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
            result.objects = null;
        }
        return result;
    }

}
