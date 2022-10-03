package com.example.tallervideojuego.controlador;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.bace.Controlador;

public class JuegoControler extends Controlador {
    private AppCompatActivity act;
    private Button btnContinuar, btnRegresar;
    private TextView txtTitulo, txtJugador, txtReto, txtCastigo;
    private int n = 0;

    public JuegoControler(AppCompatActivity act) {
        super(act);
        this.act = act;

        btnContinuar = act.findViewById(R.id.btnContinuar);
        btnRegresar = act.findViewById(R.id.btnRegresar);

        txtTitulo = act.findViewById(R.id.txtTitulo);
        txtJugador = act.findViewById(R.id.txtJugador);
        txtReto = act.findViewById(R.id.txtReto);
        txtCastigo = act.findViewById(R.id.txtCastigo);

        setFunctions();
    }

    public void setFunctions(){
        btnContinuar.setOnClickListener(next());
        btnRegresar.setOnClickListener(toReturn());
    }

    public View.OnClickListener next(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (n>=0){
                    sumar();
                    resetTitles();
                    txtTitulo.setText(txtTitulo.getText().toString()+""+n);
                    txtJugador.setText(txtJugador.getText().toString()+""+n);
                    txtReto.setText(txtReto.getText().toString()+""+n);
                    txtCastigo.setText(txtCastigo.getText().toString()+""+n);
                }
            }
        };
    }

    public View.OnClickListener toReturn(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (n>0){
                    restar();
                    resetTitles();
                    txtTitulo.setText(txtTitulo.getText().toString()+""+n);
                    txtJugador.setText(txtJugador.getText().toString()+""+n);
                    txtReto.setText(txtReto.getText().toString()+""+n);
                    txtCastigo.setText(txtCastigo.getText().toString()+""+n);
                } else {
                    Toast.makeText(act, "No hay opción anterior ", Toast.LENGTH_SHORT).show();
                }

            }
        };
    }

    public void sumar(){
        n= n+1;
    }

    public void restar(){
        n= n-1;
    }

    public void resetTitles(){
        txtTitulo.setText("Titulo");
        txtJugador.setText("Jugador");
        txtReto.setText("Reto");
        txtCastigo.setText("Castigo");
    }

}
