package com.example.tallervideojuego.controlador;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.bace.Controlador;
import com.example.tallervideojuego.vista.Menu_act;
import com.example.tallervideojuego.vista.Registrar_act;
import com.google.android.material.textfield.TextInputEditText;

public class LoginControler extends Controlador {

    private AppCompatActivity act;
    private Button btnLogin, btnRegistrarse;
    private TextInputEditText txtCorreo, txtPassword;
    private TextView txtRecuperar;

    /**
     * Constructor de la clase
     *
     * @param act se refiere al Activity que corresponde este controlador
     */
    public LoginControler(AppCompatActivity act) {
        super(act);
        this.act = act;

        btnLogin = this.act.findViewById(R.id.btnLogin);
        btnRegistrarse = this.act.findViewById(R.id.btnRegistrarse);

        txtCorreo = this.act.findViewById(R.id.txtCorreo);
        txtPassword = this.act.findViewById(R.id.txtPassword);

        txtRecuperar = this.act.findViewById(R.id.txtRecuperar);

        setFunctions();
    }

    public void setFunctions(){
        btnLogin.setOnClickListener(login());
        btnRegistrarse.setOnClickListener(registrar());
    }

    public View.OnClickListener login(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, Menu_act.class);
                act.startActivity(intent);
            }
        };
    }

    public View.OnClickListener registrar(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, Registrar_act.class);
                act.startActivity(intent);
            }
        };
    }
}
