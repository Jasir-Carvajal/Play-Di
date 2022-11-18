package com.example.tallervideojuego.modelo.entidades;

import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.registro.RegistroCat_Car;

import java.util.ArrayList;

public class Categoria extends Entidad {

    RegistroCat_Car registroCat_car;
    public static String Tabla = "Categorias";

    public Categoria(int id, String titulo) {
        super(id);
        registroCat_car  = new RegistroCat_Car();
        setTitulo(titulo);
    }
    public Categoria( String titulo) {
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

    public void setRegistroCat_car(RegistroCat_Car registroCat_car) {

        this.registroCat_car = registroCat_car;
    }

    /** Añade una relacion entre la categoria actual y una carta*/
    public void addCarta(Carta carta){

        registroCat_car.addRelacion(getId(),carta.getId());
    }
    /** Busca las cartas relacionadas con la categoria actual*/
    public ArrayList<Entidad> getCartasDeCategoria(){

        return  registroCat_car.search_cartas(getId());
    }

    public void removeCarta(Carta carta){

        registroCat_car.deleteRelacion(this,carta);
    }




    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + getId() +
                ", titulo='" + getTitulo() + '\'' +
                '}';
    }
}
