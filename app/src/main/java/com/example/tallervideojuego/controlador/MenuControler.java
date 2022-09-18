package com.example.tallervideojuego.controlador;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.controlador.bace.Controlador;

public class MenuControler extends Controlador {

    public MenuControler(AppCompatActivity act) {

    }

    public View.OnClickListener random(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }
}
