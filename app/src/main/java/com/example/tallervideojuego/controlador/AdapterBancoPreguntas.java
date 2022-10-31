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

    /**
     * Clase para el adapter del banco de preguntas (en realidad se refiere a retos)
     */

    private Context context;
    private ArrayList<Entidad> lista;
    private AppCompatActivity vista;
    private BancoPreguntasControler controlador;

    /**
     * Constructor de la clase
     * @param lista La lista de retos
     * @param app La referencia al activity
     * @param controlador La referencia al controlador en el que llama la clase, en este caso BancoPreguntasControler
     */
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


    /**
     * Este método se utiliza para asignar el método edit del controlador al boton correspondiente
     * @param entidad La entidad que se va a enviar por el metodo edit
     * @return Retorna el View.OnClickListener de edit del controlador
     */
    public View.OnClickListener edit(Entidad entidad){
        return controlador.edit(entidad);
    }


    /**
     * Este método se utiliza para asignar el método delete del controlador al boton correspondiente
     * @param entidad La entidad que se va a enviar por el metodo delete
     * @return Retorna el View.OnClickListener de delete del controlador
     */
    public View.OnClickListener delete(Entidad entidad){
        return controlador.delete(entidad);
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.adapter_cards,null);
        }

        //Se crea el titulo de la carta, se le asigna el texto y se cambia a la tipografia correcta
        TextView textView = view.findViewById(R.id.titulo_card);
        textView.setText(((Carta)lista.get(i)).getTitulo());
        textView.setTypeface(ResourcesCompat.getFont(context, R.font.fira_bold));

        //Se crea el boton de edit, se le asigna el texto y se cambia a la tipografia correcta
        Button edit = view.findViewById(R.id.editar_card);
        edit.setOnClickListener(edit(lista.get(i)));
        edit.setTypeface(ResourcesCompat.getFont(context, R.font.fira_bold));

        //Se crea el boton de edit, se le asigna el texto y se cambia a la tipografia correcta
        Button del = view.findViewById(R.id.eliminar_card);
        del.setOnClickListener(delete(lista.get(i)));
        del.setTypeface(ResourcesCompat.getFont(context, R.font.fira_bold));

        return view;
    }

}//Fin de la clase Adapter
