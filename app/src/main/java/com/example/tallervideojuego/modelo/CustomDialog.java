package com.example.tallervideojuego.modelo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.MenuCategoriasControler;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Carta;
import com.example.tallervideojuego.modelo.entidades.Categoria;

import java.util.ArrayList;

public class CustomDialog {


    private final AlertDialog alertDialog;
    private Registro registroCategorias;
    private Registro registroCartas;
    private Button btnDialogAceptar, btnDialogCancelar;
    private Categoria categoria;
    private AppCompatActivity activity;
    private MenuCategoriasControler menuCategoriasControler;

    @SuppressLint("InflateParams")
    public CustomDialog(AppCompatActivity act, Categoria categoria, MenuCategoriasControler menuCategoriasControler, Registro registroCategorias) {
        AlertDialog.Builder builder = new AlertDialog.Builder(act);

        LayoutInflater inflater = act.getLayoutInflater();

        View view = inflater.inflate(R.layout.custom_dialog, null);

        this.categoria = categoria;

        activity = act;

        registroCartas = new Registro(Carta.class);
        this.registroCategorias = registroCategorias;

        this.menuCategoriasControler = menuCategoriasControler;

        btnDialogAceptar = view.findViewById(R.id.btnDialogAceptar);
        btnDialogCancelar = view.findViewById(R.id.btnDialogCancelar);

        setFunctions();

        builder.setView(view);
        builder.setCancelable(true);
        alertDialog = builder.create();
    }

    public void setFunctions(){
//        btnDialogAceptar.setOnClickListener(menuCategoriasControler.dialogAceptar(categoria,registroCartas));
        btnDialogCancelar.setOnClickListener(cancelar());
        btnDialogAceptar.setOnClickListener(dialogAceptar());
    }

    public View.OnClickListener dialogAceptar(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Primero obtengo las cartas que están relacionadas a la categoría que quiero eliminar
                ArrayList<Entidad> cartasRelacionadas = categoria.getCartasDeCategoria();

                //Luego recorro el arreglo de cartas
                for (Entidad entidad: cartasRelacionadas) {

                    //Transformo la entidad en carta
                    Carta carta = (Carta) entidad;

                    //Elimino la relación de la carta y la categoría
                    categoria.removeCarta(carta);

                    //Obtengo las categorias relacionadas a la carta
                    ArrayList<Categoria> categoriasRelacionadas = carta.getCategoriasDeCartas();

//                    System.out.println("Las categorias son ="+categoriasRelacionadas);

                    //Si esa carta no tiene ninguna otra relación con otra categoría se elimina
                    if (categoriasRelacionadas.isEmpty()){
                        registroCartas.delete(carta);
                    }
                }

                //Se elimina la categoria
                registroCategorias.delete(categoria);

                dismissDialog();

                menuCategoriasControler.updateDelete(registroCategorias.getEntidades());

            }
        };
    }

    public View.OnClickListener cancelar(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
            }
        };
    }

    public void starLoadingDialog(){
        alertDialog.show();
    }

    public void dismissDialog(){
        alertDialog.dismiss();
    }

    public Registro getRegistroCategorias() {
        return registroCategorias;
    }
}
