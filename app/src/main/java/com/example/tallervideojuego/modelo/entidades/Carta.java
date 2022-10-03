package com.example.tallervideojuego.modelo.entidades;

import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.registro.RegistroCat_Car;

import java.util.Arrays;

public class Carta extends Entidad {

    public static String Tabla = "Cartas";

    public Carta(String titulo, String reto, String castigo, int[] idCategorias) {
        super();
        setTitulo(titulo);
        setReto(reto);
        setCastigo(castigo);
        setIdCategorias(idCategorias);
    }
    public Carta(){
        super();
    }

    public Carta(int id, String titulo, String reto, String castigo, int[] idCategorias) {
        super(id);
        setTitulo(titulo);
        setReto(reto);
        setCastigo(castigo);
        setIdCategorias(idCategorias);
    }

    public String getTitulo() {
        return contenido.getAsString("titulo");
    }

    public void setTitulo(String titulo) {
        contenido.put("titulo", titulo);
    }

    public String getReto() {
        return contenido.getAsString("reto");
    }

    public void setReto(String reto) {
        contenido.put("castigo", reto);
    }

    public String getCastigo() {
        return contenido.getAsString("castigo");
    }

    public void setCastigo(String castigo) {
        contenido.put("castigo", castigo);
    }

    public int[] getIdCategorias() {


        RegistroCat_Car registro = new RegistroCat_Car();
        return (int[]) contenido.get("idCategorias");

    }

    public void setIdCategorias(int[] idCategorias) {

        contenido.put("idCategorias", String.valueOf(idCategorias));

    }

    @Override
    public String toString() {
        return "Carta{" +
                "id=" + getId() +
                ", titulo='" + getTitulo() + '\'' +
                ", reto='" + getReto() + '\'' +
                ", castigo='" + getCastigo() + '\'' +
                ", idCategorias=" + Arrays.toString(getIdCategorias()) +
                '}';
    }
}
