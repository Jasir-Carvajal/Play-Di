package com.example.tallervideojuego.controlador;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.base.Controlador;
import com.example.tallervideojuego.modelo.Api.Api;
import com.example.tallervideojuego.modelo.Api.SyncDB;
import com.example.tallervideojuego.vista.BancoPreguntas_act;
import com.example.tallervideojuego.vista.JugarCategorias_act;
import com.example.tallervideojuego.vista.Login_act;
import com.example.tallervideojuego.vista.MenuCategorias_act;

public class MenuControler extends Controlador {

    private AppCompatActivity act;
    private Button btnJugar, btnEditarCategorias, btnEditarRetos, btnLogOut;

    /**
     * Constructor de la clase
     *
     * @param act se refiere al Activity que corresponde este controlador
     */
    public MenuControler(AppCompatActivity act) {
        super(act);
        Api api =  new Api();
        api.sincronizar();
        this.act=act;

        btnJugar = this.act.findViewById(R.id.btnJugar);
        btnEditarCategorias = this.act.findViewById(R.id.btnEditarCategorias);
        btnEditarRetos = this.act.findViewById(R.id.btnEditarRetos);
        btnLogOut = this.act.findViewById(R.id.btnLogOut);

        setFunctions();
    }

    public void setFunctions(){
        btnJugar.setOnClickListener(onPlay());
        btnEditarCategorias.setOnClickListener(onCategorias());
        btnEditarRetos.setOnClickListener(onRetos());
        btnLogOut.setOnClickListener(LogOut());
    }

    public View.OnClickListener onPlay(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, JugarCategorias_act.class);
                act.startActivity(intent);
            }
        };
    }

    public View.OnClickListener onCategorias(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, MenuCategorias_act.class);
                act.startActivity(intent);
            }
        };
    }
    public View.OnClickListener onRetos(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, BancoPreguntas_act.class);
                act.startActivity(intent);
            }
        };
    }
    public View.OnClickListener LogOut(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, Login_act.class);
                act.startActivity(intent);
                SyncDB syncDB = new SyncDB();
                syncDB.delet();

                SharedPreferences sharedPref = act.getSharedPreferences("playdi", act.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("token", "");
                editor.apply();
                Api.setToken("00");
                regresar();
            }
        };
    }
}
