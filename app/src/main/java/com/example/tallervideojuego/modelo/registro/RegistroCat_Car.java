package com.example.tallervideojuego.modelo.registro;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;

import java.util.ArrayList;

public class RegistroCat_Car extends Registro {

    public RegistroCat_Car() {
        super("Carta_Categoria");
    }

    public ArrayList<Integer> search_cat(int categoria_id){
        ArrayList<Integer> res = new ArrayList<Integer>();

        for (Entidad entidad:listaEntidades) {
          int idRelacion = entidad.getContent().getAsInteger("categoria_id");
          if (idRelacion == categoria_id)res.add(categoria_id);
        }
        return res;
    }

    public ArrayList<Integer> search_car(int carta_id){
        ArrayList<Integer> res = new ArrayList<Integer>();

        for (Entidad entidad:listaEntidades) {
            int idRelacion = entidad.getContent().getAsInteger("cartas_id");
            if (idRelacion == carta_id)res.add(carta_id);
        }
        return res;
    }





}
