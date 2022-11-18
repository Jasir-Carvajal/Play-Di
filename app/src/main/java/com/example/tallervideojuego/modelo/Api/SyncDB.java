package com.example.tallervideojuego.modelo.Api;

import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Carta;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.modelo.registro.RegistroCat_Car;

import java.util.ArrayList;

public class SyncDB  {
    private Registro rCarta, rCat,rRel,cambios;
    private ArrayList<Entidad> lista;

    public SyncDB(){

        rCarta = new Registro(Carta.class);
        rCat = new Registro(Categoria.class);
        rRel = new RegistroCat_Car();
        cambios = new Registro("Cambios");
        lista = cambios.getEntidades();
    }



    public String makeJson()
    {
        Entidad cambio  = cambios.search(0);
        String  tabla = cambio.getContent().getAsString("tabla"),
                fecha= cambio.getContent().getAsString("fecha"),
                accion= cambio.getContent().getAsString("accion"),
                contenido= cambio.getContent().getAsString("contenido"),
                id_relacionado= cambio.getContent().getAsString("id_relacionado");

        String cambioJSoon = "{ \"tabla\":\""+tabla+"\", \"fecha\":\""+fecha+"\",\"accion\":\""+accion+"\",  \"id_relacionado\":\""+id_relacionado+"\",\"contenido\":"+contenido+" }";


        String json = "{ ";

        json+="\"cartas\":[";

        for (Entidad entidad:rCarta.getEntidades()) {
            Carta carta = (Carta) entidad;
            json+=carta.getJson()+",";
        }
        json = json.substring(0, json.length() - 1);
        json+="],";

        json+="\"categorias\":[";

        for (Entidad entidad:rCat.getEntidades()) {
            Categoria categoria = (Categoria) entidad;
            json+=categoria.getJson()+",";
        }
        json = json.substring(0, json.length() - 1);

        json+="],";
        json+="\"cambio\":"+cambioJSoon;


        json+="}";
        return json;
    }
}
