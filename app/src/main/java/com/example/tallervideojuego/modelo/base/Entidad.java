package com.example.tallervideojuego.modelo.base;


import android.content.ContentValues;

public class Entidad {
    private int id;
    protected ContentValues contenido;
    public static String Tabla;

    public Entidad() {
        contenido = new ContentValues();
    }
    public Entidad(int id) {
        this.id = id;
        contenido = new ContentValues();
    }
    public ContentValues getContent() {
        return contenido;
    }

    public void setContenido(ContentValues contenido) {
        this.contenido = contenido;
    }

    public int getId() {
        return id;
    }

    public static void setTabla(String tabla) {
        Tabla = tabla;
    }

    public void setId(int id) {
        this.id = id;
    }
}
