package com.example.tallervideojuego.controlador;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.bace.Controlador;
import com.example.tallervideojuego.vista.Jugar_act;
import com.example.tallervideojuego.vista.Menu_act;

public class AddJugadoresControler extends Controlador {
    private AppCompatActivity act;
    private Button btnContinuar, btnRegresar;

    public AddJugadoresControler(AppCompatActivity act) {
        super(act);
        this.act = act;

        btnContinuar = act.findViewById(R.id.btnContinuar);
        btnRegresar = act.findViewById(R.id.btnRegresar);

        setFunctions();
    }

    public void setFunctions(){
        btnContinuar.setOnClickListener(next());
        btnRegresar.setOnClickListener(toReturn());
    }

    public View.OnClickListener next(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, Jugar_act.class);
                act.startActivity(intent);
            }
        };
    }

    public View.OnClickListener toReturn(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, Menu_act.class);
                act.startActivity(intent);
            }
        };
    }
}
