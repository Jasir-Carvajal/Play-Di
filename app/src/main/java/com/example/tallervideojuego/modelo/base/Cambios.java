package com.example.tallervideojuego.modelo.base;

import android.content.ContentValues;

public class Cambios{
    private int id;
    protected ContentValues contenido;
    public static String Tabla = "Cambios";

    public Cambios(){

    }

    public Cambios(String tabla, String id_Relacionado, String accion, String fecha){
        super();
        contenido = new ContentValues();
        setTablaRelacioanda(tabla);
        setIdRelacionado(id_Relacionado);
        setAccion(accion);
        setFecha(fecha);
        setSync(0);
    }

    private void setFecha(String fecha) {
        contenido.put("fecha",fecha);
    }

    private void setAccion(String accion) {
        contenido.put("accion",accion);
    }

    private void setIdRelacionado(String id_relacionado) {
        contenido.put("id_relacionado",id_relacionado);
    }
    public void setContenido(String contenido) {
        this.contenido.put("contenido",contenido);
    }

    private void setTablaRelacioanda(String tabla) {
        contenido.put("tabla",tabla);
    }

    public void getContenido() {
        this.contenido.getAsString("contenido");
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

    public void setContent(ContentValues contenido) {
        this.contenido = contenido;
    }

    public int getId() {
        return id;
    }

    public void setTabla(String tabla) {
        Tabla = tabla;
    }

    public void setSync(Integer sync) {
        this.contenido.put("sync",sync);
    }

    public  Integer getSync() {
        return contenido.getAsInteger("sync");
    }

    public void setId(int id) {
        this.id = id;
    }

}
