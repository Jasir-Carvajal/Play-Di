package com.example.tallervideojuego.modelo.entidades;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.tallervideojuego.modelo.base.Entidad;

public class Jugador  {

    private String nombre;


    public Jugador( String nombre) {

        this.nombre = nombre;

    }

    protected Jugador(Parcel in) {
        nombre = in.readString();

    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    @Override
    public String toString() {
        return "Jugador{" +
                ", nombre='" + nombre + '\'' +
                '}';
    }


}
