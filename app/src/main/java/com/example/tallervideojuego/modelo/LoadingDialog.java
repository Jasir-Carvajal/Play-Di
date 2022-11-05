package com.example.tallervideojuego.modelo;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;

public class LoadingDialog {

    private AppCompatActivity act;
    private AlertDialog alertDialog;

    public LoadingDialog(AppCompatActivity act) {
        this.act = act;
    }

    public void starLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(act);

        LayoutInflater inflater = act.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissDialog(){
        alertDialog.dismiss();
    }
}
