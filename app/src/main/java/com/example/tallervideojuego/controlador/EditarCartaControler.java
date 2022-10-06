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
import com.example.tallervideojuego.vista.BancoPreguntas_act;

public class EditarCartaControler extends Controlador {

    private AppCompatActivity act;

    private Button btnGuardar, btnCancelar;

    private Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    public EditarCartaControler(AppCompatActivity act) {
        super(act);
        this.act = act;

        btnGuardar = act.findViewById(R.id.p);
        btnCancelar = act.findViewById(R.id.btnCancelar);


        spinner = act.findViewById(R.id.spinner);

        adapter = ArrayAdapter.createFromResource(this.act,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        setFunctions();
    }

    public void setFunctions(){
        btnGuardar.setOnClickListener(save());
        btnCancelar.setOnClickListener(cancel());
    }

    public View.OnClickListener save(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, BancoPreguntas_act.class);

                act.startActivity(intent);
                Toast.makeText(act, "Se guardaron los cambios correctamente ", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public View.OnClickListener cancel(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, BancoPreguntas_act.class);

                act.startActivity(intent);
            }
        };
    }
}
