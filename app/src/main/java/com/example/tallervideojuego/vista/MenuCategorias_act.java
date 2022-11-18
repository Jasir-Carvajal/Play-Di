package com.example.tallervideojuego.vista;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.MenuCategoriasControler;

public class MenuCategorias_act extends Vista {
    private MenuCategoriasControler controlador;
    private LinearLayout container;
    private Button random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_categorias);
        controlador = new MenuCategoriasControler(this);
    }
}