package com.example.tallervideojuego.modelo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.EditarCartaControler;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.entidades.Categoria;

import java.util.ArrayList;
import java.util.Collections;

public class AdapterCategorías extends BaseAdapter {

    Context context;
    ArrayList<Categoria> lista;
    EditarCartaControler controler;

    public AdapterCategorías(Context context, ArrayList<Categoria> lista, EditarCartaControler controler) {
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

    public View.OnClickListener delete(Categoria categoria){
        return controler.delete(categoria);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_cat, null);
        }
        TextView txtCat = view.findViewById(R.id.txtCat);
        ImageView imgTrash = view.findViewById(R.id.imgTrash);

        txtCat.setText(lista.get(i).getTitulo());
        imgTrash.setImageDrawable(context.getResources().getDrawable(R.drawable.trash));
        txtCat.setTypeface(ResourcesCompat.getFont(context, R.font.fira_bold));

        imgTrash.setOnClickListener(delete(lista.get(i)));



        return view;
    }
}
