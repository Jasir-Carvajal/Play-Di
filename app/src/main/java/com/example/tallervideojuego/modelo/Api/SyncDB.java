package com.example.tallervideojuego.modelo.Api;

import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Carta;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.modelo.registro.RegistroCat_Car;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SyncDB extends Thread{
    private Registro rCarta, rCat,rRel,cambios;
    private ArrayList<Entidad> lista;

    public SyncDB(){
        rCarta = new Registro(Carta.class);
        rCat = new Registro(Categoria.class);
        rRel = new RegistroCat_Car();
        cambios = new Registro("Cambios");
        lista = cambios.getEntidades();
    }

    @Override
    public void run() {
        super.run();

    }

    public String makeJson()
    {
        String json="{";
        for (int i = 0; i < lista.size(); i++) {
            Entidad entidad = lista.get(i);
            Integer id_relacionada = entidad.getContent().getAsInteger("id_Relacionado");
            String tabla = entidad.getContent().getAsString("tabla");
            String accion = entidad.getContent().getAsString("accion");
            String fecha = entidad.getContent().getAsString("fecha");

            json = json+" '"+entidad.getId()+"':{";
            json+="'id_relacionada':"+id_relacionada+",";
            json+="'tabla':'"+tabla+"',";
            json+="'accion':'"+accion+"',";
            json+="'fecha':'"+fecha+"'";

            if (i < lista.size()-1)json = json+"},";
            if (i == lista.size()-1)json = json+"}";
        }
        json = json+"}";
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "fail";
        }

    }
}
