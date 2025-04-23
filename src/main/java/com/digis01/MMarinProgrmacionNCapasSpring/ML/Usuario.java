package com.digis01.MMarinProgrmacionNCapasSpring.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Usuario {

    private int IdUsuario;
    @NotEmpty(message = "Incorrecto UserName vacio")
    @Size(min = 4, max = 50, message = "caracteres entre 4 y 50")
    private String UserName;
    @NotEmpty(message = "Incorrecto Nombre vacio")
    @Pattern(regexp = "^([a-zA-ZÀ-ÿ\\u00f1\\u00d1]+[,.]?[ ]?|[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+['-]?)+$", message = "Incorrecto solo letras ")
    private String Nombre;
    @NotEmpty(message = "Incorrecto Apellido Paterno vacio")
    @Pattern(regexp = "^([a-zA-ZÀ-ÿ\\u00f1\\u00d1]+[,.]?[ ]?|[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+['-]?)+$", message = "Incorrecto solo letras ")
    private String ApellidoPaterno;
    @NotEmpty(message = "Incorrecto Apellido Materno vacio")
    @Pattern(regexp = "^([a-zA-ZÀ-ÿ\\u00f1\\u00d1]+[,.]?[ ]?|[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+['-]?)+$", message = "Incorrecto solo letras ")
    private String ApellidoMaterno;
    @NotEmpty(message = "Incorrecto Email vacio")
    @Pattern(regexp = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "Correo incorrecto")
    private String Email;
    @NotEmpty(message = "Incorrecto Password Vacio")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Contrseña incorrecta")
    private String Password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")  //Esto te ayuda a darle formato a la fecha que se recibe de la vista y poder
    @NotNull(message = "Error Fecha vacia")
    private Date FechaNacimiento;
    @NotEmpty(message = "Error Sexo vacio")
    @Pattern(regexp = "((M|H)|(m|h))", message = "Contrseña incorrecta")
    private String Sexo;
    @NotEmpty(message = "Error Telefono vacio")
    @Pattern(regexp = "^(\\+\\d{1,2}\\s?)?1?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", message = "Numero telefonico incorrecto")
    private String Telefono;
    //@Pattern(regexp = "", message = "Numero de Celular incorrecto")
    private String Celular;
    private String CURP;
    @Valid
    public Rol rol;
    private String Imagen;
    private int Estatus;
    
    public void setEstatus(int Estatus){
        this.Estatus = Estatus;
    }
    public int getEstatus(){
        return this.Estatus;
    }
    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;

    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;

    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserName() {
        return this.UserName;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public Date getFechaNacimiento() {
        return this.FechaNacimiento;
    }

    public int getIdUsuario() {
        return this.IdUsuario;
    }

    public String getNombre() {
        return this.Nombre;

    }

    public String getTelefono() {
        return this.Telefono;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getCelular() {
        return this.Celular;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getCURP() {
        return this.CURP;
    }
}
