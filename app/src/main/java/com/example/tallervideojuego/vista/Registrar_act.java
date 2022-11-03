package com.example.tallervideojuego.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.RegistrarControler;

public class Registrar_act extends Vista {

    RegistrarControler controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        controlador = new RegistrarControler(this);
    }
}