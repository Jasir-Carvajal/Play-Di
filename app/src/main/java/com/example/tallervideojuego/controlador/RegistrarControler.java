package com.example.tallervideojuego.controlador;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.bace.Controlador;
import com.google.android.material.textfield.TextInputEditText;

public class RegistrarControler extends Controlador {
    private AppCompatActivity act;
    private Button btnRegistrarse;
    private TextInputEditText txtNombre, txtCorreo, txtPassword;

    /**
     * Constructor de la clase
     *
     * @param act se refiere al Activity que corresponde este controlador
     */
    public RegistrarControler(AppCompatActivity act) {
        super(act);
        this.act = act;

        btnRegistrarse = this.act.findViewById(R.id.btnRegistrarse);

        setFunctions();
    }

    public void setFunctions(){
        btnRegistrarse.setOnClickListener(registrarse());
    }

    public View.OnClickListener registrarse(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresar();
            }
        };
    }
}
