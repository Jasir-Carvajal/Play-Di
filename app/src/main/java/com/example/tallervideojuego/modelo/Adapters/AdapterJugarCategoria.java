package com.example.tallervideojuego.modelo.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import androidx.core.content.res.ResourcesCompat;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.EditarCartaControler;
import com.example.tallervideojuego.controlador.JugarCategoriasControler;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.entidades.Carta;
import com.example.tallervideojuego.modelo.entidades.Categoria;

import java.util.ArrayList;
import java.util.Collections;

public class AdapterJugarCategoria extends BaseAdapter {

    private Context context;
    private ArrayList<Entidad> lista;
    private JugarCategoriasControler controler;



    public AdapterJugarCategoria(Context context, ArrayList<Entidad> lista, JugarCategoriasControler controler) {
        this.context = context;
        this.lista = lista;
        this.controler = controler;

        Collections.reverse(this.lista);
    }

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
     * Este método se utiliza para asignar el método play del controlador al boton correspondiente
     * @param entidad La entidad que se va a enviar por el metodo onPlay
     * @return Retorna el View.OnClickListener de play del controlador
     */
    public View.OnClickListener play(Entidad entidad){
        return controler.onPlay(entidad);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_jugar_categoria,null);
        }
        //Se crea el boton de edit, se le asigna el texto y se cambia a la tipografia correcta

            Button categoria = view.findViewById(R.id.btnJugarCategoria);
            categoria.setOnClickListener(play(lista.get(i)));
            categoria.setTypeface(ResourcesCompat.getFont(context, R.font.fira_bold));
            categoria.setText(((Categoria) lista.get(i)).getTitulo());
//        categoria.setBackgroundResource(R.color.orange);


        return view;
    }
}
