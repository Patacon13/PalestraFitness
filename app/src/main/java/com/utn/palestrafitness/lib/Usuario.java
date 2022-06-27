package com.utn.palestrafitness.lib;

public class Usuario {
    private String usuario;
    private String apellido;
    private String documento;
    private String telefono;
    private String email;
    private String sexo;
    private Boolean esAlumnoActivo;

    public Usuario() {

    }

    public Usuario(String usuario, String apellido, String documento, String telefono, String email, String sexo) {
        this.usuario = usuario;
        this.apellido = apellido;
        this.documento = documento;
        this.telefono = telefono;
        this.email = email;
        this.sexo = sexo;
        this.esAlumnoActivo = true;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Boolean getEsAlumnoActivo() {
        return esAlumnoActivo;
    }

    public void setEsAlumnoActivo(Boolean esAlumnoActivo) {
        this.esAlumnoActivo = esAlumnoActivo;
    }
}
