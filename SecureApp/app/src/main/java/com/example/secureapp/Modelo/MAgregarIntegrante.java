package com.example.secureapp.Modelo;

import java.io.Serializable;

public class MAgregarIntegrante implements Serializable{

    private String identificador, nombre, apellido, email, telefono, identificadorGrupo;

    public MAgregarIntegrante(String identificador, String nombre, String apellido, String email, String telefono, String identificadorGrupo) {

        this.identificador = identificador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.identificadorGrupo = identificadorGrupo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIdentificadorGrupo() {
        return identificadorGrupo;
    }

    public void setIdentificadorGrupo(String identificadorGrupo) {
        this.identificadorGrupo = identificadorGrupo;
    }
}


