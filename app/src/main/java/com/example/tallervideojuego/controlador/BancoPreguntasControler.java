package com.example.tallervideojuego.controlador;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.bace.Controlador;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.vista.EditarCarta_act;
import com.example.tallervideojuego.vista.Menu_act;

import java.util.ArrayList;
import java.util.List;

public class BancoPreguntasControler extends Controlador{

    private AppCompatActivity act;
    private Button btnGuardar, btnEditar, btnEditar2;

    private Spinner spinnerCat;

    private Registro registroCategorias;

    private ArrayAdapter<CharSequence> adapter;
    private ArrayList<String> catItems = new ArrayList<>();

    public BancoPreguntasControler(AppCompatActivity act) {
        super(act);
        this.act = act;

        registroCategorias = new Registro(Categoria.class);

        catItems = getStrings();

        catItems.add(0,"TODAS");


        spinnerCat = act.findViewById(R.id.spinnerCat);



        /*adapter = ArrayAdapter.createFromResource(this.act,
                R.array.planets_array, R.layout.style_spinner);*/
        adapter = new ArrayAdapter(this.act, R.layout.style_spinner,catItems);
        adapter.setDropDownViewResource(R.layout.style_dropdown_spinner);
        spinnerCat.setAdapter(adapter);

        //setFunctions();
    }

    public void setFunctions(){
        btnGuardar.setOnClickListener(onSave());
        btnEditar.setOnClickListener(onEdit());
        btnEditar2.setOnClickListener(onEdit());
    }

    public View.OnClickListener onEdit(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, EditarCarta_act.class);

                act.startActivity(intent);
            }
        };
    }

    public View.OnClickListener onSave(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, Menu_act.class);

                act.startActivity(intent);
                Toast.makeText(act, "Se guardaron los cambios correctamente ", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public ArrayList<String> getStrings(){
        ArrayList<String> listArray = new ArrayList<>();

        for (int i = 0; i<registroCategorias.getListaEntidades().size(); i++){

            Categoria categoria = (Categoria) registroCategorias.getEntidades().get(i);

            listArray.add(categoria.getTitulo());
        }

        return  listArray;
    }
}
