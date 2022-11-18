package com.example.tallervideojuego.modelo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.AddJugadoresControler;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.modelo.entidades.Jugador;

import java.util.ArrayList;

public class AdapterJugadores extends BaseAdapter {

    Context context;
    ArrayList<Jugador> lista;
    AddJugadoresControler controler;

    public AdapterJugadores(Context context, ArrayList<Jugador> lista, AddJugadoresControler controler) {
        this.context = context;
        this.lista = lista;
        this.controler = controler;
    }

    @Override
    public int getCount() {
        return lista.size();
    }


    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    public View.OnClickListener delete(int i){
        return controler.delete(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_jugador, null);
        }
        TextView jugador_nombre = view.findViewById(R.id.jugador_nombre);
        Button eliminar_jugador = view.findViewById(R.id.eliminar_jugador);

        jugador_nombre.setText(lista.get(i).getNombre());

        jugador_nombre.setTypeface(ResourcesCompat.getFont(context, R.font.fira_bold));
        eliminar_jugador.setTypeface(ResourcesCompat.getFont(context, R.font.fira_bold));

        eliminar_jugador.setOnClickListener(delete(i));



        return view;
    }
}
