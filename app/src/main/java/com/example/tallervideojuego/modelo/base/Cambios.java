package com.example.tallervideojuego.modelo.base;

import android.content.ContentValues;

public class Cambios{
    private int id;
    protected ContentValues contenido;
    public static String Tabla = "Cambios";

    public Cambios(){

    }

    public Cambios(String tabla, Integer id_Relacionado, String accion, String fecha){
        super();
        contenido = new ContentValues();
        setTablaRelacioanda(tabla);
        setIdRelacionado(id_Relacionado);
        setAccion(accion);
        setFecha(fecha);
    }

    private void setFecha(String fecha) {
        contenido.put("fecha",fecha);
    }

    private void setAccion(String accion) {
        contenido.put("accion",accion);
    }

    private void setIdRelacionado(Integer id_relacionado) {
        contenido.put("id_relacionado",id_relacionado);
    }

    private void setTablaRelacioanda(String tabla) {
        contenido.put("tabla",tabla);
    }


    private String getFecha() {
        return contenido.getAsString("fecha");
    }

    private String getAccion() {
       return contenido.getAsString("accion");
    }

    private Integer getIdRelacionado() {
       return contenido.getAsInteger("id_relacionado");
    }

    private String getTablaRelacioanda() {
       return contenido.getAsString("tabla");
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
