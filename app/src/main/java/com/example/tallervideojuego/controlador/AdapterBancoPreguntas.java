package com.example.tallervideojuego.controlador;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.tallervideojuego.R;

import com.example.tallervideojuego.controlador.BancoPreguntasControler;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Carta;
import com.example.tallervideojuego.vista.EditarCarta_act;

import java.util.ArrayList;

public class AdapterBancoPreguntas  extends BaseAdapter {

    private Context context;
    private ArrayList<Entidad> lista;
    private AppCompatActivity vista;
    private BancoPreguntasControler controlador;

    public AdapterBancoPreguntas( ArrayList<Entidad> lista, AppCompatActivity app,BancoPreguntasControler controlador) {
        this.context = app.getApplicationContext();
        this.lista = lista;
        this.vista = app;
        this.controlador = controlador;
    }//Fin del constructor

    //Devuelve el tamaño de la lista
    @Override
    public int getCount() {
        return lista.size();
    }

    //Devuelve un objeto en una posición
    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    //Retorna una posicion
    @Override
    public long getItemId(int i) {
        return i;
    }



    public View.OnClickListener edit(int id){
        return controlador.edit(id);
    }

    public View.OnClickListener delete(Entidad entidad){
        return controlador.delete(entidad);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.adapter_cards,null);
            TextView textView = view.findViewById(R.id.titulo_card);
            textView.setText(((Carta)lista.get(i)).getTitulo());
            Button edit = view.findViewById(R.id.editar_card);
            edit.setOnClickListener(edit(i));
            Button del = view.findViewById(R.id.eliminar_card);
            del.setOnClickListener(delete(lista.get(i)));
        }

        return view;
    }

}//Fin de la clase Adapter
