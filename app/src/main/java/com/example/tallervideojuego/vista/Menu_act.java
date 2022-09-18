package com.example.tallervideojuego.vista;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.MenuControler;
import com.example.tallervideojuego.controlador.bace.Controlador;

import android.os.Bundle;
import android.widget.Button;

public class Menu_act extends AppCompatActivity {
    private MenuControler controlador;
    private Button random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controlador = new MenuControler(this);
        setContentView(R.layout.activity_main);

    }
    private void setControlador(){

        random.setOnClickListener(controlador.random());
    }
}