package com.example.tallervideojuego.modelo.registro;

import android.content.ContentValues;

import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Carta;
import com.example.tallervideojuego.modelo.entidades.Categoria;

import java.util.ArrayList;
/** Clase de administracion de una tabla intermedai (relacion m:m) entre Categoria y Carta */
public class RegistroCat_Car extends Registro {

    private Registro registroCategorias;
    private Registro registroCartas;

    public RegistroCat_Car() {
        super("Carta_Categoria");
        registroCartas = new Registro(Carta.class);
        registroCategorias = new Registro(Categoria.class);
    }
    /** Valida si una relacion es nueva en la tabla o ya se habia creado */
    private boolean validarRealcion(Entidad relacion){
        ArrayList<Entidad> cartas = search_cartas(relacion.getContent().getAsInteger("categoria_id"));
        for (Entidad carta:cartas) {
            if (carta.getId() == relacion.getContent().getAsInteger("cartas_id")){
                return false;
            }
        }
        return true;
    }
    /** Añade una relacion a la tabla intermedia */
    public void addRelacion(int id_categoria, int id_carta){
        ContentValues data = new ContentValues();
        data.put("cartas_id",id_carta);
        data.put("categoria_id",id_categoria);
        Entidad relacion = new Entidad();
        relacion.setContenido(data);
        if (validarRealcion(relacion)){
            add(relacion);
        }

    }
    /** Busca las categorias relacionadas a una carta */
    public ArrayList<Categoria> search_categorias(int carta_id_relacionada){
        ArrayList<Categoria> res = new ArrayList<Categoria>();

        for (Entidad entidad:listaEntidades) {
          int cartas_id = entidad.getContent().getAsInteger("cartas_id");
          if (cartas_id == carta_id_relacionada){
              Categoria categoria = (Categoria) registroCategorias.search(entidad.getContent().getAsInteger("categoria_id"));
              res.add(categoria);
          }
        }
        return res;
    }
    /** Busca las cartas relacionadas a una categoria */
    public ArrayList<Entidad> search_cartas(int categoria_id_relacioanda){
        ArrayList<Entidad> res = new ArrayList<>();

        for (Entidad entidad:listaEntidades) {
            int categoria_id = entidad.getContent().getAsInteger("categoria_id");
            if (categoria_id == categoria_id_relacioanda){
                Carta carta = (Carta) registroCartas.search(entidad.getContent().getAsInteger("cartas_id"));
                res.add(carta);
            }
        }
        return res;
    }

    public void deleteRelacion(Categoria categoria, Carta carta){
        for (Entidad entidad:listaEntidades) {
           int id_categoria = entidad.getContent().getAsInteger("categoria_id");
           int id_carta = entidad.getContent().getAsInteger("cartas_id");
            if (id_carta == carta.getId() && id_categoria == categoria.getId()){
                delete(entidad);
                return;
            }
        }
    }




}
