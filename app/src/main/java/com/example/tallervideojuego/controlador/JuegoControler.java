package com.example.tallervideojuego.controlador;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.base.Controlador;
import com.example.tallervideojuego.modelo.RandomPlayer;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Carta;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.modelo.entidades.Jugador;
import com.example.tallervideojuego.modelo.registro.RegistroCat_Car;

import java.util.ArrayList;
import java.util.Random;

public class JuegoControler extends Controlador {

    /**
     * La clase controlador para el activity jugar
     * numeroRetoTotal = la cantidad de veces que se ha avanzado de pantalla
     * numeroRetoActual = en la pantalla en la que estoy (esto porque se puede retroceder y avanzar)
     * listaJugadoresUsados = para ir guardando los datos de los jugadores que ya salieron
     * listaCartasUsados = para ir guardando los datos de los cartas que ya salieron
     * listaFondosUsados = para ir guardando los datos de los fondos que ya salieron
     * id = Se refiere al id de la categoria a la cual se va a jugar, el cual se pasa por el Intent
     */

    private AppCompatActivity act;
    private LinearLayout lytJugar;
    private ImageButton btnContinuar, btnRegresar;
    private TextView txtJugador, txtReto, txtCastigo;

    private int id;

    private int numeroRetoTotal = -1;
    private int numeroRetoActual = -1;

    private Registro registroCategorias;
    private RegistroCat_Car registroRelacion;

    private Categoria categoria;

//    private ArrayList<String> listaJugadores;
    private ArrayList<Jugador> listaJugadores;
    private ArrayList<Carta> listaCartas;

    private ArrayList<Jugador> listaJugadoresUsados = new ArrayList<>();
//    private ArrayList<String> listaJugadoresUsados = new ArrayList<>();
    private ArrayList<Carta> listaCartasUsadas = new ArrayList<>();
    private ArrayList<Integer> listaFondosUsados = new ArrayList<>();

    private Random random = new Random();

//    private RandomPlayer randomPlayer = new RandomPlayer();

    private Jugador[] jugadores;

    /**
     * Constructor de la clase
     * @param act La referencia del activity donde se inicializa el controlador
     */
    public JuegoControler(AppCompatActivity act) {
        super(act);
        this.act = act;

        lytJugar = this.act.findViewById(R.id.lytJugar);

        btnContinuar = this.act.findViewById(R.id.btnContinuar);
        btnRegresar = this.act.findViewById(R.id.btnRegresar);

        txtJugador = this.act.findViewById(R.id.txtJugador);
        txtReto = this.act.findViewById(R.id.txtReto);
        txtCastigo = this.act.findViewById(R.id.txtCastigo);

        listaJugadores = this.act.getIntent().getParcelableArrayListExtra("lista");
//        listaJugadores = this.act.getIntent().getParcelableArrayListExtra("lista");
        id = act.getIntent().getIntExtra("id",0);

        registroCategorias = new Registro(Categoria.class);
        registroRelacion = new RegistroCat_Car();

        fillJugadores();

        RandomPlayer.setJugadores(jugadores);

        setCategoria();

        listaCartas = getCartas();

        avanzarCarta();
        setFunctions();
    }

    /**
     * Este método funciona para asignar los View.OnClickListener a los botones o elementos necesarios
     */
    public void setFunctions(){
        btnContinuar.setOnClickListener(next());
        btnRegresar.setOnClickListener(toReturn());
    }

    /**
     * MÉTODO para la funcion de avanzar al siguiente reto
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener next(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avanzarCarta();
//                message(numeroRetoActual+"");
//                message(numeroRetoTotal+"");
            }
        };
    }

    /**
     * MÉTODO para la funcion de retroceder al reto anterior
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener toReturn(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numeroRetoActual > 0){
                    retrocederCarta();
//                    message(numeroRetoActual+"");
//                    message(numeroRetoTotal+"");
                } else {
                    message("No hay retos anteriores");
                }
            }
        };
    }


    /**
     * MÉTODO para asignar la categoria de juego en la que se esta jugando
     */
    public void setCategoria(){
        categoria = (Categoria) registroCategorias.search(id);
        categoria.setRegistroCat_car(registroRelacion);
    }

    /**
     * MÉTODO para crear la lista de retos que estan asociados a la categoria que se esta jugando
     * @return Retorna la lista
     */
    public  ArrayList<Carta> getCartas(){

        ArrayList<Carta> listaConvertida = new ArrayList<>();
        Carta carta;

        for (int i = 0; i < categoria.getCartasDeCategoria().size(); i++) {
            carta = (Carta) categoria.getCartasDeCategoria().get(i);
            listaConvertida.add(carta);
        }
        return listaConvertida;
    }

    /**
     * Este método controla la funcion de avanzar carta
     * Hay dos tipos de avanzar:
     * - Avanzar creando un nuevo reto, se crean nuevos datos
     * - Avanzar luego de retroceder, osea se tienen que volver a poner los datos que ya habían salido, NO crear nuevos
     */
    public void avanzarCarta(){
        //Se comprueba que tipo de avanzar es.
        // Si el numero actual es igual al total, es el primer tipo
        if (numeroRetoActual == numeroRetoTotal) {
            //Se asigna la cantidad de jugadores y cartas que hay
            int cantidadJugadores = listaJugadores.size();
            int cantidadCartas = listaCartas.size();

            //Se saca un numero random
            Jugador jugadorRandom = RandomPlayer.RANDOM();
//            int jugadorRandom = random.nextInt(cantidadJugadores);
            int cartaRandom = random.nextInt(cantidadCartas);
            int fondoRandom = random.nextInt(3);

            //Se asignan los valores en la pantalla
            txtJugador.setText(jugadorRandom.getNombre());
//            txtJugador.setText(listaJugadores.get(jugadorRandom));
            txtReto.setText(listaCartas.get(cartaRandom).getReto());
            txtCastigo.setText(listaCartas.get(cartaRandom).getCastigo());
            cambiarFondo(fondoRandom);

            //Se guardan los datos que salieron
            listaJugadoresUsados.add(jugadorRandom);
//            listaJugadoresUsados.add(listaJugadores.get(jugadorRandom));
            listaCartasUsadas.add(listaCartas.get(cartaRandom));
            listaFondosUsados.add(fondoRandom);

            //Se aumentan los contadores
            numeroRetoTotal++;
            numeroRetoActual++;
        } else {
            //Segundo tipo de avanzar
            //Se aumenta el contador actual
            numeroRetoActual++;

            //Se buscan los datos que ya salieron
            String jugador = listaJugadoresUsados.get(numeroRetoActual).getNombre();
            Carta carta = listaCartasUsadas.get(numeroRetoActual);
            int fondo = listaFondosUsados.get(numeroRetoActual);

            //Se asignan los valores a la pantalla
            cambiarFondo(fondo);
            txtJugador.setText(jugador);
            txtReto.setText(carta.getReto());
            txtCastigo.setText(carta.getCastigo());
        }
    }

    /**
     * Este método controla la funcion de retroceder carta
     */
    public void retrocederCarta(){
        //Se disminuye el reto actual
        numeroRetoActual--;

        //Busca los valores que ya habían salido
        String jugador = listaJugadoresUsados.get(numeroRetoActual).getNombre();
        Carta carta = listaCartasUsadas.get(numeroRetoActual);
        int fondo = listaFondosUsados.get(numeroRetoActual);

        //Los asigna a la pantalla
        cambiarFondo(fondo);
        txtJugador.setText(jugador);
        txtReto.setText(carta.getReto());
        txtCastigo.setText(carta.getCastigo());
    }

    /**
     * Este método controla la funcion de cambiar de fondo entre 3 tipos
     * @param fondoRandom El numero de fondo al que va a cambiar, entre los 3 tipos
     */
    public void cambiarFondo(int fondoRandom){

        switch (fondoRandom){
            case 0:
                lytJugar.setBackgroundResource(R.drawable.fondo1);
                break;
            case 1:
                lytJugar.setBackgroundResource(R.drawable.fondo2);
                break;
            case 2:
                lytJugar.setBackgroundResource(R.drawable.fondo3);
                break;
        }
    }


    public void fillJugadores(){
        jugadores = new Jugador[listaJugadores.size()];

        for (int i = 0; i < listaJugadores.size(); i++) {
            jugadores[i] = listaJugadores.get(i);
        }
    }

}
