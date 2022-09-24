package com.example.tallervideojuego.vista;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.AddJugadoresControler;

import android.os.Bundle;

public class AddJugadores_act extends AppCompatActivity {
    private AddJugadoresControler controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jugadores);
        controlador = new AddJugadoresControler(this);
    }
}