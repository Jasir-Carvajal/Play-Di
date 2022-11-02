package com.example.tallervideojuego.vista;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Vista extends AppCompatActivity {
    boolean firstTime = true;

    @Override
    public void onResume() {
        // ejecuta el codigo aqui...
        super.onResume();
        if (!firstTime){
            recreate();
        }
        firstTime = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
