package com.example.tallervideojuego.modelo.base;


import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/** Entidad
// Clase generica de objetos posibles de almacenar en la base de Datos
//  Contiede la estructura nesesaria para que el registro administre los datos entre la app y la DB
//
//  Id = primary key
//  contenido = propiedades de los hijos almacenadas en un hash table de tipo Content Value limitando los tipos de datos a los posibles en la base de datos;
// */
public  class Entidad {

    protected int id;
    protected ContentValues contenido;
    public static String Tabla;

    public  Entidad() {
        contenido = new ContentValues();
        contenido.put("global_ID", UUID.randomUUID().toString());
    }

    protected void update(String accion,String tabla){
        Registro cambios = new Registro("Cambios");
        Date currentTime = Calendar.getInstance().getTime();

       CharSequence date = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", currentTime);



        Cambios nuevo = new Cambios(tabla,contenido.getAsString("global_ID"),accion,date.toString());
        nuevo.setContenido(this.getJson());
        cambios.add(nuevo);
    }

    public Entidad(int id) {
        contenido = new ContentValues();
        this.id = id;


    }
    public ContentValues getContent() {
        return contenido;
    }



    public String getJson() {
        try {
            JSONObject res = new JSONObject();
            res.put("id",id);
            for (String dato:contenido.keySet()) {
                res.put(dato,contenido.get(dato));
            }
            return res.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

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
