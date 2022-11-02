package com.example.tallervideojuego.vista;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.MenuControler;
import com.example.tallervideojuego.modelo.base.DataBase;
import com.example.tallervideojuego.modelo.base.Registro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class Menu_act extends Vista {
    private MenuControler controlador;
    private LinearLayout container;
    private Button random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        DataBase.setContext(getApplicationContext());
        DataBase.setName("PlaydiDB");
        controlador = new MenuControler(this);
    }
}