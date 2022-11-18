package com.example.tallervideojuego.modelo.entidades;

import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.registro.RegistroCat_Car;

import java.util.ArrayList;

public class Carta extends Entidad {

    public static String Tabla = "Cartas";
    private RegistroCat_Car registroCat_car;

    public Carta(String titulo, String reto, String castigo) {
        super();

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
        registroCat_car = new RegistroCat_Car();
        registroCat_car.addRelacion(categoria.getId(),getId());
    }

    public void removeCategoria(Categoria categoria){
        registroCat_car = new RegistroCat_Car();
        registroCat_car.deleteRelacion(categoria,this);
    }

    /** Obtine las categorias con las que esta relacionada */
    public ArrayList<Categoria> getCategoriasDeCartas(){
        registroCat_car = new RegistroCat_Car();
        return  registroCat_car.search_categorias(getId());
    }

    @Override
    public String getJson() {
        registroCat_car = new RegistroCat_Car();
            String res ="{ \"id\": \" " + getId() + "\", " +
                    "\"global_ID\":\"" + contenido.getAsString("global_ID") + "\" , " +
                    "\"titulo\":\"" + getTitulo() + "\" , " +
                    "\"reto\" : \"" + getReto() + "\" , " +
                    "\"castigo\":\"" + getCastigo() + "\", ";

        res+="\"Categorias\":[";
        ArrayList<Categoria> listaCategorias = registroCat_car.search_categorias(getId());

        if (listaCategorias.size
                ()>0){

            for (Categoria categoria:listaCategorias) {
                res+=categoria.getJson()+",";
            }
            res = res.substring(0, res.length() - 1);
        }

        res+="]}";

        return res;
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
