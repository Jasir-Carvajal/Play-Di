package com.example.tallervideojuego.vista;

import android.os.Bundle;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.LoginControler;
import com.example.tallervideojuego.modelo.base.DataBase;

public class Login_act extends Vista {

    private LoginControler controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBase.setContext(getApplicationContext());
        DataBase.setName("PlaydiDB");
        setContentView(R.layout.activity_login);

        controlador = new LoginControler(this);

    }
}