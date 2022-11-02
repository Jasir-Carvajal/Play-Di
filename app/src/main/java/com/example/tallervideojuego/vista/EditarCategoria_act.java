package com.example.tallervideojuego.vista;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.EditarCategoriasControler;

import android.os.Bundle;

public class EditarCategoria_act extends Vista {

    private EditarCategoriasControler controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_categoria);
        controlador = new EditarCategoriasControler(this);
    }

}