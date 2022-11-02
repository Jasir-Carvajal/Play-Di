package com.example.tallervideojuego.vista;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.EditarCartaControler;

import android.os.Bundle;

public class EditarCarta_act extends Vista {

    private EditarCartaControler controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_carta);
        controlador = new EditarCartaControler(this);
    }
}