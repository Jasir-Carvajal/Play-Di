package com.example.tallervideojuego.vista;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.MenuControler;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class Menu_act extends AppCompatActivity {
    private MenuControler controlador;
    private LinearLayout container;
    private Button random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        controlador = new MenuControler(this);
    }
}