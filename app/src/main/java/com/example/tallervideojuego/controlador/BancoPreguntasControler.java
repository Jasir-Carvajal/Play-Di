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
import com.example.tallervideojuego.vista.EditarCarta_act;
import com.example.tallervideojuego.vista.Menu_act;

public class BancoPreguntasControler extends Controlador{

    private AppCompatActivity act;
    private Button btnGuardar, btnEditar, btnEditar2;

    private Spinner spinner;

    ArrayAdapter<CharSequence> adapter;

    public BancoPreguntasControler(AppCompatActivity act) {
        super(act);
        this.act = act;

        btnGuardar = this.act.findViewById(R.id.btnGuardar);
        btnEditar = this.act.findViewById(R.id.btnEditar);
        btnEditar2 = this.act.findViewById(R.id.btnEditar2);

        spinner = act.findViewById(R.id.spinner);

        adapter = ArrayAdapter.createFromResource(this.act,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        setFunctions();
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
}
