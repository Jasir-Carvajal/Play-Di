package com.example.tallervideojuego.vista;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.BancoPreguntasControler;

import android.os.Bundle;

public class BancoPreguntas_act extends Vista {

    private BancoPreguntasControler controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco_preguntas);

        controlador = new BancoPreguntasControler(this);
    }

}