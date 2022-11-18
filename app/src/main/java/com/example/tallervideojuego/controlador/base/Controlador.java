package com.example.tallervideojuego.controlador.base;

import android.util.TypedValue;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

    /**
     * La clase padre que se utilizará para los controladores
     */

    public class Controlador {
        protected AppCompatActivity act;

        /**
         * Constructor de la clase
         * @param act se refiere al Activity que corresponde este controlador
         */

        public Controlador(AppCompatActivity act) {
            this.act = act;
        }


        public int dp( int n){
            return  ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, act.getResources().getDisplayMetrics()))*n;
        }

        public void regresar(){
            act.finish();
        }

        /**
         * MÉTODO para crear un mensaje por Toast
         * @param text texto para el mensaje
         */
        public void message(String text){
            Toast.makeText(act, text, Toast.LENGTH_SHORT).show();
        }

     }

