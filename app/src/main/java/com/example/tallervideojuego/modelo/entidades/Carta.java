package com.example.tallervideojuego.modelo.entidades;

import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.registro.RegistroCat_Car;

import java.util.ArrayList;
import java.util.Arrays;

public class Carta extends Entidad {

    public static String Tabla = "Cartas";
    private RegistroCat_Car registroCat_car;
    public Carta(String titulo, String reto, String castigo, int[] idCategorias) {
        super();
        registroCat_car = new RegistroCat_Car();
        setTitulo(titulo);
        setReto(reto);
        setCastigo(castigo);

    }
    public Carta(){
        super();
    }

    public Carta(int id, String titulo, String reto, String castigo) {
        super(id);
        setTitulo(titulo);
        setReto(reto);
        setCastigo(castigo);

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
        contenido.put("reto", reto);
    }

    public String getCastigo() {
        return contenido.getAsString("castigo");
    }

    public void setCastigo(String castigo) {
        contenido.put("castigo", castigo);
    }

    /** añade realcion entre la actual carta y una categoria */
    public void addCategoria(Categoria categoria){
        registroCat_car.addRelacion(categoria.getId(),getId());
    }

    /** Obtine las categorias con las que esta relacionada */
    public ArrayList<Categoria> getCategoriasDeCartas(){

        return  registroCat_car.search_categorias(getId());
    }

    @Override
    public String toString() {
        return "Carta{" +
                "id=" + getId() +
                ", titulo='" + getTitulo() + '\'' +
                ", reto='" + getReto() + '\'' +
                ", castigo='" + getCastigo() + '\'' +
                '}';
    }
}
