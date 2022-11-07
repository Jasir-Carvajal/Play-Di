package com.example.tallervideojuego.modelo.entidades;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.tallervideojuego.modelo.base.Entidad;

public class Jugador implements Parcelable {

    private String nombre;


    public Jugador( String nombre) {

        this.nombre = nombre;

    }

    protected Jugador(Parcel in) {
        nombre = in.readString();

    }


    public static final Creator<Jugador> CREATOR = new Creator<Jugador>() {
        @Override
        public Jugador createFromParcel(Parcel in) {
            return new Jugador(in);
        }

        @Override
        public Jugador[] newArray(int size) {
            return new Jugador[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
    }
}
