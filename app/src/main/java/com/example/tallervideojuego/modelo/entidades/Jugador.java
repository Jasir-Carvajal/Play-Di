package com.example.tallervideojuego.modelo.entidades;

import com.example.tallervideojuego.modelo.base.Entidad;

public class Jugador  {

    private String nombre;
    private String correo;
    private String clave; //Contraseña

    public Jugador( String nombre, String correo, String clave) {

        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", clave='" + clave + '\'' +
                '}';
    }
}
