package com.example.tallervideojuego.controlador;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.bace.Controlador;
import com.example.tallervideojuego.modelo.Api.Api;
import com.example.tallervideojuego.modelo.LoadingDialog;
import com.example.tallervideojuego.vista.MenuCategorias_act;
import com.example.tallervideojuego.vista.Registrar_act;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.logging.Handler;

public class LoginControler extends Controlador {

    private AppCompatActivity act;
    private Button btnLogin, btnRegistrarse;
    private TextInputEditText txtCorreo, txtPassword;
    private TextView txtRecuperar;

    private Api api;
    private LoadingDialog loadingDialog;

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

        api = new Api();

        loadingDialog = new LoadingDialog(this.act);

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
//                Intent intent = new Intent(act, Menu_act.class);
//                act.startActivity(intent);

                String correo = txtCorreo.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String retorno;

                if (correo.isEmpty() || password.isEmpty()) {
                    message("Debe de llenar todos los campos");
                }else if(!isValidEmail()){
                    txtCorreo.setError("Correo invalido");
                } else {
                    loadingDialog.starLoadingDialog();
                    try {
                        retorno = api.login(correo,password);;
                        System.out.println(retorno);



                        if (retorno!= null && retorno.equalsIgnoreCase("false")){
                           // loadingDialog.dismissDialog();
                            message("mensajito");
                            txtCorreo.setError("Datos incorrectos");
                            txtPassword.setError("Datos incorrectos",null);
                        } else{
                            loadingDialog.dismissDialog();
                            Intent intent = new Intent(act, MenuCategorias_act.class);
                            act.startActivity(intent);
                            regresar();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


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

    public boolean isValidEmail() {
        String correo = txtCorreo.getText().toString().trim();

        return (Patterns.EMAIL_ADDRESS.matcher(correo).matches());
    }
}
