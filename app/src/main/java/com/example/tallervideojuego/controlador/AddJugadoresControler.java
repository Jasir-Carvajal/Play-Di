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

    /**
     * La clase controlador para el activity add_jugadores
     *
     * id = Se refiere al id de la categoria a la cual se va a jugar, el cual se pasa por el Intent
     * alto = maneja el alto de la pantalla
     */

    private AppCompatActivity act;

    private Button btnAgregar, btnRegresar, btnJugar;
    private EditText txtJugador;
    private ListView listJ;

    private int id;

    private ArrayList<String> listaJugadores;

    private AdapterJugadores adapterJugadores;
    private int alto;

    public int size(float porcentaje){

        return Math.round(porcentaje*alto);

    }

    /**
     * Constructor de la clase
     * @param act La referencia del activity donde se inicializa el controlador
     */

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

        id = act.getIntent().getIntExtra("id",0);

        listJ = this.act.findViewById(R.id.listJ);
        if (alto>2800 ) listJ.getLayoutParams().height = size(0.50F);
        if (alto>1900 && alto<2800) listJ.getLayoutParams().height = size(0.40F);
        if ( alto<1900) listJ.getLayoutParams().height = size(0.35F);

        listaJugadores = new ArrayList<>();

        setFunctions();
    }

    /**
     * Este método funciona para asignar los View.OnClickListener a los botones o elementos necesarios
     */
    public void setFunctions(){
        btnRegresar.setOnClickListener(toReturn());
        btnAgregar.setOnClickListener(add());
        btnJugar.setOnClickListener(play());
    }


    /**
     * MÉTODO para la funcion de agregar
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener add(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Se comprueba que los campos de textos no esten vacios
                if(txtJugador.getText().toString().trim().isEmpty()){
                    message("Debe de ingresar un nombre");
                } else {
                    //Se comprueba que el jugador ya no haya sido agregado
                    if (comprobarJugador(txtJugador.getText().toString())){
                        //Se agrega el jugador, se actualiza el arraylist y se limpian los campos de texto
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


    /**
     * MÉTODO para la funcion de jugar
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener play(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Se comprueba que haya al menos dos jugadores agregados para poder comenzar el juego
                //Si es así se pasa el id de la categoría, para posteriormente tomar los retos asociados, y la lista de jugadores
                if(listaJugadores.size()>=2){
                    Intent intent = new Intent(act, Jugar_act.class);

                    intent.putExtra("id",id);
                    intent.putStringArrayListExtra("lista",listaJugadores);

                    act.startActivity(intent);
                } else {
                    message("Debe agregar al menos 2 jugadores");
                }
            }
        };
    }

    /**
     * MÉTODO para la funcion de regresar (regresa a la pantalla anterior)
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener toReturn(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresar();
            }
        };
    }

    /**
     * MÉTODO para la funcion de eliminar un jugador
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener delete(int i){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaJugadores.remove(i);
                updateAdapter(listaJugadores);
            }
        };
    }

    /**
     * Este método actualiza el adapter se llama cada vez que se hace un cambio a  la lista de jugadores
     * @param lista_usable lista de jugadores actualizada
     */
    private void updateAdapter(ArrayList<String> lista_usable) {
        adapterJugadores = new AdapterJugadores(act,lista_usable,this);
        listJ.setAdapter(adapterJugadores);
    }

    /**
     * Este metodo recorre la lista de jugadores y comprueba que el jugador que se pasa por parametros no se encuentra en la lista
     * @param jugador El nombre del jugador el cual se quiere comprobar
     * @return Retorna un true si el jugador no se encuentra y false si se encuentra ya agregado
     */
    private boolean comprobarJugador(String jugador){
        for (int i = 0; i < listaJugadores.size(); i++) {
            if(listaJugadores.get(i).equalsIgnoreCase(jugador)){
                return false;
            }
        }
        return true;
    }

    /**
     * MÉTODO para crear un mensaje por Toast
     * @param text texto para el mensaje
     */
    public void message(String text){
        Toast.makeText(act, text, Toast.LENGTH_SHORT).show();
    }


    /**
     * MÉTODO para limpiar los campos de texto
     */
    public void limpiar(){
        txtJugador.setText("");
    }


}
