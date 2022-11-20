package com.example.tallervideojuego.modelo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;

public class LoadingDialog {

    private final AlertDialog alertDialog;

    @SuppressLint("InflateParams")
    public LoadingDialog(AppCompatActivity act) {
        AlertDialog.Builder builder = new AlertDialog.Builder(act);

        LayoutInflater inflater = act.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(false);
        alertDialog = builder.create();
    }

    public void starLoadingDialog(){
            alertDialog.show();
    }

    public void dismissDialog(){
        alertDialog.dismiss();
    }
}
