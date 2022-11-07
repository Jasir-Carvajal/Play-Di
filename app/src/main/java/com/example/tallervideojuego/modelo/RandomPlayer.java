package com.example.tallervideojuego.modelo;

import com.example.tallervideojuego.modelo.entidades.Jugador;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer {

    static private Jugador[] jugadores;
    static private ArrayList<Jugador> jugadoresProbabilidades;

    public static Jugador[] getJugadores() {
        return jugadores;
    }

    public static void setJugadores(Jugador[] jugadores) {
        RandomPlayer.jugadores = jugadores;
        Probabilidades();
    }

    private static void Probabilidades(){
        jugadoresProbabilidades = new ArrayList<>();
        int n = 0;
        for (int i = 0; i < jugadores.length; i++) {
            RandomPlayer.jugadoresProbabilidades.add(jugadores[n]);
            n = n==jugadores.length-1?0:n+1;
        }
    }

    public static Jugador RANDOM(){
        Random r = new Random();
        int low = 0;
        int high = jugadoresProbabilidades.size();
        int result = r.nextInt(high-low) + low;
        Jugador res = jugadoresProbabilidades.get(result);
        jugadoresProbabilidades.remove(result);

        if (jugadoresProbabilidades.size()==0){
            Probabilidades();
        }

        return res;
    }


}
