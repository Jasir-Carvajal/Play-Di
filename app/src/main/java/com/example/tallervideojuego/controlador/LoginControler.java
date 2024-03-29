package com.example.tallervideojuego.controlador;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.base.Controlador;
import com.example.tallervideojuego.modelo.Api.Api;
import com.example.tallervideojuego.modelo.Api.SyncDB;
import com.example.tallervideojuego.modelo.LoadingDialog;
import com.example.tallervideojuego.vista.Menu_act;
import com.example.tallervideojuego.vista.Registrar_act;
import com.google.android.material.textfield.TextInputEditText;
import com.google.common.util.concurrent.FutureCallback;

import java.util.Objects;

public class LoginControler extends Controlador {

    /**
     * La clase controlador para el activity login
     */


    private final AppCompatActivity act;
    private final Button btnLogin, btnRegistrarse;
    private final  TextInputEditText txtCorreo, txtPassword;
    private final TextView txtRecuperar;

    private final Api api;
    private final LoadingDialog loadingDialog;

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
        //api.sendCambios();


        setFunctions();

        SharedPreferences sharedPref = act.getSharedPreferences("playdi", act.MODE_PRIVATE);
        String token = sharedPref.getString("token", "");
        loadingDialog = new LoadingDialog(this.act);
        loadingDialog.starLoadingDialog();
        System.out.println("token: "+token);
        Api.isToken(token, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {

                if(result.equals("200")){
                    Api.setToken(token);
                    Api api = new Api();
                    api.sincronizar(new FutureCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            onSync();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            t.printStackTrace();
                            onSyncFail();
                        }
                    });
                }else {

                    loadingDialog.dismissDialog();

                    if (result.equals("01")&&token.length()>0){
                        onSync();
                    }else{
                        new SyncDB().delet();
                    }
                }
            }
            @Override
            public void onFailure(Throwable t) {

                t.printStackTrace();
                onSyncFail();
            }
        });


    }
    @WorkerThread
    public void onSyncFail(){
        act.runOnUiThread(() -> {
            loadingDialog.dismissDialog();
            Toast.makeText(act, "Algo salio muy mal", Toast.LENGTH_SHORT).show();

        });
    }

    @WorkerThread
    public void onSync(){
        act.runOnUiThread(() -> {

            loadingDialog.dismissDialog();
            Intent intent = new Intent(act, Menu_act.class);
            act.startActivity(intent);
            regresar();
        });
    }

    public void setFunctions(){
        btnLogin.setOnClickListener(login());
        btnRegistrarse.setOnClickListener(registrar());
    }

    /**
     * MÉTODO para la funcion de login
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener login(){
        return view -> {
//                Intent intent = new Intent(act, Menu_act.class);
//                act.startActivity(intent);

            String correo = Objects.requireNonNull(txtCorreo.getText()).toString().trim();
            String password = Objects.requireNonNull(txtPassword.getText()).toString().trim();
            String retorno;

            if (correo.isEmpty() || password.isEmpty()) {
                message("Debe de llenar todos los campos");

            }else if(!isValidEmail()){
                txtCorreo.setError("Correo invalido");
            } else {
                loadingDialog.starLoadingDialog();

                   api.login(correo, password, new FutureCallback<String>() {
                        @Override
                        public void onSuccess(String retorno) {
                            //este metodo para poder interactuar con la interface se debe declarar externamente
                            onLoginSuccess(retorno);
                        }

                        @Override
                        public void onFailure(@NonNull Throwable t) {
                            t.printStackTrace();
                            onSyncFail();
                        }
                    });


            }


        };
    }
    //WorkerThread para poder hacer uso de act
    @WorkerThread
    public void  onLoginSuccess(String retorno){

        //indica que se ejecutara dentro del hilo con acceso a la interface
            if (retorno!= null && retorno.equalsIgnoreCase("false")){
                act.runOnUiThread(() -> {
                    loadingDialog.dismissDialog();

                    txtCorreo.setError("Datos incorrectos");
                    txtPassword.setError("Datos incorrectos", null);
                });
            } else{

                SharedPreferences sharedPref = act.getSharedPreferences("playdi", act.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("token", retorno);
                editor.apply();


                Api api = new Api();
                api.sincronizar(new FutureCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        onSync();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                        onSyncFail();
                    }
                });

            }
    }


    /**
     * MÉTODO para la ir a la pantalla de registar
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener registrar(){
        return view -> {
            Intent intent = new Intent(act, Registrar_act.class);
            act.startActivity(intent);
        };
    }
    /**
     * MÉTODO para comprobar si se esta ingresando un
     * @return Retorna el View.OnClickListener
     */
    public boolean  isValidEmail() {
        String correo = Objects.requireNonNull(txtCorreo.getText()).toString().trim();

        return (Patterns.EMAIL_ADDRESS.matcher(correo).matches());
    }
}
