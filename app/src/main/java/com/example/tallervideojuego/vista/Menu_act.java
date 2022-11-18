package com.example.tallervideojuego.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.MenuControler;
import com.example.tallervideojuego.modelo.base.DataBase;

public class Menu_act extends Vista {

    private MenuControler controlador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        DataBase.setContext(getApplicationContext());
        DataBase.setName("PlaydiDB");

        controlador = new MenuControler(this);
    }
}