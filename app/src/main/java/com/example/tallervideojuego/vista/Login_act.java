package com.example.tallervideojuego.vista;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.LoginControler;

public class Login_act extends Vista {

    private LoginControler controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        controlador = new LoginControler(this);
    }
}