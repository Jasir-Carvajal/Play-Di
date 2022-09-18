package com.example.tallervideojuego.modelo.entidades;

import com.example.tallervideojuego.modelo.base.Entidad;

import java.util.Arrays;

public class Carta extends Entidad {
    private String titulo;
    private String reto;
    private String castigo;
    private int idCategorias[]; //Los id se las categorías a las que esta asociada esta carta

    public Carta(int id) {
        super(id);
    }

    public Carta(int id, String titulo, String reto, String castigo, int[] idCategorias) {
        super(id);
        this.titulo = titulo;
        this.reto = reto;
        this.castigo = castigo;
        this.idCategorias = idCategorias;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getReto() {
        return reto;
    }

    public void setReto(String reto) {
        this.reto = reto;
    }

    public String getCastigo() {
        return castigo;
    }

    public void setCastigo(String castigo) {
        this.castigo = castigo;
    }

    public int[] getIdCategorias() {
        return idCategorias;
    }

    public void setIdCategorias(int[] idCategorias) {
        this.idCategorias = idCategorias;
    }

    @Override
    public String toString() {
        return "Carta{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", reto='" + reto + '\'' +
                ", castigo='" + castigo + '\'' +
                ", idCategorias=" + Arrays.toString(idCategorias) +
                '}';
    }
}
