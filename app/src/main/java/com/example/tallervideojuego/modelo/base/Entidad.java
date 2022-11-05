package com.example.tallervideojuego.modelo.base;


import android.content.ContentValues;

import java.util.Calendar;
import java.util.Date;

/** Entidad
// Clase generica de objetos posibles de almacenar en la base de Datos
//  Contiede la estructura nesesaria para que el registro administre los datos entre la app y la DB
//
//  Id = primary key
//  contenido = propiedades de los hijos almacenadas en un hash table de tipo Content Value limitando los tipos de datos a los posibles en la base de datos;
// */
public  class Entidad {


    private int id;
    protected ContentValues contenido;
    public static String Tabla;

    public  Entidad() {
        contenido = new ContentValues();
    }

    protected void update(){
        Date currentTime = Calendar.getInstance().getTime();
        contenido.put("time_stamp", currentTime.toString());
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
