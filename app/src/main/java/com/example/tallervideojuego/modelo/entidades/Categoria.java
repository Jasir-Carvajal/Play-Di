package com.example.tallervideojuego.modelo.entidades;

import com.example.tallervideojuego.modelo.base.Entidad;

public class Categoria extends Entidad {


    public static String Tabla = "Categorias";

    public Categoria(int id, String titulo) {
        super(id);
        setTitulo(titulo);
    }
    public Categoria( String titulo) {
        super();
        setTitulo(titulo);
    }

    public Categoria(){
        super();
    }

    public String getTitulo() {
        return contenido.getAsString("titulo");
    }

    public void setTitulo(String titulo) {
        contenido.put("titulo",titulo);
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + getId() +
                ", titulo='" + getTitulo() + '\'' +
                '}';
    }
}
