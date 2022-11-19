package com.example.tallervideojuego.modelo.Api;

import com.example.tallervideojuego.modelo.base.Cambios;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Carta;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.modelo.registro.RegistroCat_Car;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private void deletAll(){
        for (Entidad entidad:rRel.getEntidades()) {
            rRel.delete(entidad);
        }
        for (Entidad entidad:rCarta.getEntidades()) {
            rCarta.delete(entidad);
        }
        for (Entidad entidad:rCat.getEntidades()) {
            if (!entidad.getContent().getAsString("titulo").equals("Random")) rCat.delete(entidad);
        }
    }

    public void sync(JSONArray cartas, JSONArray categorias, JSONArray cambios) throws JSONException {
        this.deletAll();

        for (int i = 0; i < categorias.length(); i++) {
            JSONObject obj =  categorias.getJSONObject(i);
            Categoria categoria = new Categoria();
            categoria.setTitulo(obj.getString("titulo"));
            rCat.add(categoria);
        }

        for (int i = 0; i < cartas.length(); i++) {
           JSONObject obj =  cartas.getJSONObject(i);
           Carta carta = new Carta();
           carta.setReto(obj.getString("reto"));
           carta.setTitulo(obj.getString("titulo"));
           carta.setCastigo(obj.getString("castigo"));
           rCarta.add(carta);
           JSONArray categoriasRelacioandas = obj.getJSONArray("categorias");
            for (int j = 0; j < categoriasRelacioandas.length(); j++) {
                Categoria categoria = (Categoria) rCat.search("titulo",categoriasRelacioandas.getJSONObject(i).getString("titulo"));
                carta.addCategoria(categoria);
            }
        }

        for (int i = 0; i < cambios.length(); i++) {
            JSONObject obj =  cambios.getJSONObject(i);

            Cambios cambio_ = new Cambios(obj.getString("tabla"),"x","sync",obj.getString("fecha"));
            this.cambios.add(cambio_);
        }

    }

    public String makeJson()
    {
        String cambioJSoon;
          if (lista.size()>0){
              Entidad cambio  = lista.get(0);
              String tabla = cambio.getContent().getAsString("tabla"),
                      fecha= cambio.getContent().getAsString("fecha"),
                      accion= cambio.getContent().getAsString("accion"),
                      contenido= cambio.getContent().getAsString("contenido"),
                      id_relacionado= cambio.getContent().getAsString("global_ID");



              cambioJSoon = "{ \"tabla\":\""+tabla+"\", \"fecha\":\""+fecha+"\",\"accion\":\""+accion+"\",  \"id_relacionado\":\""+id_relacionado+"\",\"contenido\":"+contenido+" }";

          }else {
              return "";
          }

        String json = "{ ";

        json+="\"cartas\":[";

        for (Entidad entidad:rCarta.getEntidades()) {
            Carta carta = (Carta) entidad;
            json+=carta.getJson()+",";
        }
        if (rCarta.getEntidades().size()>0){
            json = json.substring(0, json.length() - 1);
        }
        json+="],";

        json+="\"categorias\":[";

        for (Entidad entidad:rCat.getEntidades()) {
            Categoria categoria = (Categoria) entidad;
            json+=categoria.getJson()+",";
        }

        if (rCat.getEntidades().size()>0){
            json = json.substring(0, json.length() - 1);
        }
        json+="],";
        json+="\"cambio\":"+cambioJSoon;


        json+="}";
        return json;
    }
}
