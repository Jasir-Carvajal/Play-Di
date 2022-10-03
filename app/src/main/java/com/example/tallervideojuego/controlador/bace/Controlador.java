package com.example.tallervideojuego.controlador.bace;

import android.util.TypedValue;

import androidx.appcompat.app.AppCompatActivity;

    public class Controlador {
        protected AppCompatActivity act;
        public Controlador(AppCompatActivity act) {
            this.act = act;

        }


        public int dp( int n){
            return  ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, act.getResources().getDisplayMetrics()))*n;
        }

    }

