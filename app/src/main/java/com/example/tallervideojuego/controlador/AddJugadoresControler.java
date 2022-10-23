package com.example.tallervideojuego.controlador;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.bace.Controlador;
import com.example.tallervideojuego.modelo.AdapterCategorías;
import com.example.tallervideojuego.modelo.AdapterJugadores;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.vista.Jugar_act;
import com.example.tallervideojuego.vista.Menu_act;

import java.util.ArrayList;
import java.util.Collections;

public class AddJugadoresControler extends Controlador {
    private AppCompatActivity act;

    private Button btnAgregar, btnRegresar, btnJugar;
    private EditText txtJugador;
    private ListView listJ;

    private ArrayList<String> listaJugadores;

    private AdapterJugadores adapterJugadores;
    private int alto;

    public int size(float porcentaje){

        return Math.round(porcentaje*alto);

    }

    public AddJugadoresControler(AppCompatActivity act) {
        super(act);
        this.act = act;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        alto = displayMetrics.heightPixels;

        btnAgregar = this.act.findViewById(R.id.btnAgregar);
        btnRegresar = this.act.findViewById(R.id.btnRegresar);
        btnJugar = this.act.findViewById(R.id.btnJugar);

        txtJugador = this.act.findViewById(R.id.txtJugador);

        listJ = this.act.findViewById(R.id.listJ);
        if (alto>2800 ) listJ.getLayoutParams().height = size(0.50F);
        if (alto>1900 && alto<2800) listJ.getLayoutParams().height = size(0.40F);
        if ( alto<1900) listJ.getLayoutParams().height = size(0.35F);

        listaJugadores = new ArrayList<>();

        setFunctions();
    }

    public void setFunctions(){

        btnRegresar.setOnClickListener(toReturn());
        btnAgregar.setOnClickListener(add());
        btnJugar.setOnClickListener(play());
    }

    public View.OnClickListener add(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtJugador.getText().toString().trim().isEmpty()){
                    message("Debe de ingresar un nombre");
                } else {
                    if (comprobarJugador(txtJugador.getText().toString())){
                        listaJugadores.add(0,txtJugador.getText().toString());
                        updateAdapter(listaJugadores);
                        limpiar();
                    } else {
                        message("Ese jugador ya fue agregado");
                    }
                }
            }
        };
    }

    public View.OnClickListener play(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, Jugar_act.class);
                act.startActivity(intent);
            }
        };
    }

    public View.OnClickListener toReturn(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, Menu_act.class);
                act.startActivity(intent);
            }
        };
    }

    public View.OnClickListener delete(int i){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaJugadores.remove(i);
                updateAdapter(listaJugadores);
            }
        };
    }

    private void updateAdapter(ArrayList<String> lista_usable) {
        adapterJugadores = new AdapterJugadores(act,lista_usable,this);
        listJ.setAdapter(adapterJugadores);
    }

    private boolean comprobarJugador(String jugador){
        for (int i = 0; i < listaJugadores.size(); i++) {
            if(listaJugadores.get(i).equalsIgnoreCase(jugador)){
                return false;
            }
        }
        return true;
    }

    public void message(String text){
        Toast.makeText(act, text, Toast.LENGTH_SHORT).show();
    }

    public void limpiar(){
        txtJugador.setText("");
    }


}
