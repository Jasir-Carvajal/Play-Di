package com.example.tallervideojuego.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.JugarCategoriasControler;

public class JugarCategorias_act extends AppCompatActivity {

    JugarCategoriasControler controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar_categorias);

        controlador = new JugarCategoriasControler(this);
    }
}