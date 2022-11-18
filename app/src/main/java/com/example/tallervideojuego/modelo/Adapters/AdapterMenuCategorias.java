package com.example.tallervideojuego.modelo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.BancoPreguntasControler;
import com.example.tallervideojuego.controlador.MenuCategoriasControler;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.entidades.Carta;
import com.example.tallervideojuego.modelo.entidades.Categoria;

import java.util.ArrayList;

public class AdapterMenuCategorias extends BaseAdapter {
    /**
     * Clase para el adapter del banco de preguntas (en realidad se refiere a retos)
     */

    private Context context;
    private ArrayList<Entidad> lista;

    private MenuCategoriasControler controlador;

    public AdapterMenuCategorias(Context context, ArrayList<Entidad> lista, MenuCategoriasControler controlador) {
        this.context = context;
        this.lista = lista;
        this.controlador = controlador;
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


        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.adapter_cards, null);
        }


        //Se crea el titulo de la carta, se le asigna el texto y se cambia a la tipografia correcta
        TextView textView = view.findViewById(R.id.titulo_card);
        textView.setText(((Categoria) lista.get(i)).getTitulo());
        textView.setTypeface(ResourcesCompat.getFont(context, R.font.fira_bold));

        //Se crea el boton de edit, se le asigna el texto y se cambia a la tipografia correcta
        Button edit = view.findViewById(R.id.editar_card);
        edit.setOnClickListener(edit(lista.get(i)));
        edit.setTypeface(ResourcesCompat.getFont(context, R.font.fira_bold));
        edit.setBackgroundTintList(context.getResources().getColorStateList(R.color.purple_500));

        //Se crea el boton de edit, se le asigna el texto y se cambia a la tipografia correcta
        Button del = view.findViewById(R.id.eliminar_card);
        del.setOnClickListener(delete(lista.get(i)));
        del.setTypeface(ResourcesCompat.getFont(context, R.font.fira_bold));
        del.setBackgroundTintList(context.getResources().getColorStateList(R.color.purple_500));


        return view;
    }
}
