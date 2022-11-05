package com.example.tallervideojuego;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.tallervideojuego.modelo.Radom_player;
import com.example.tallervideojuego.modelo.entidades.Jugador;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testExample() {
        assertEquals(0, 0);
    }

    @Test
    public void time() {
        long start = System.nanoTime();
        while ( System.nanoTime() < (start+(1*1000000000L)) ){
            try {
                Thread.sleep(  10 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertEquals(0, 0);
    }



    @Test
    public void Radom_player_Test() {
        System.out.print("\n\n \n\n");
        System.out.print(System.nanoTime());
        ArrayList<Jugador> prelista = new ArrayList<>();

        for (int i = 0; i < 18; i++) {
            prelista.add(new Jugador(""+(i+1)));
        }
        Jugador[] example = new Jugador[1];

        Jugador[] jugadores = prelista.toArray(example);
        Radom_player.setJugadores(jugadores);
        ArrayList<Jugador> res = new ArrayList();

        for (int i = 0; i < 4500; i++) {
            res.add(Radom_player.RANDOM());
        }


        int[] revision = new int[jugadores.length];
        int n = 0;

        for (int i = 0; i < res.size(); i++) {

            for (int j = 0; j < jugadores.length; j++) {
                int x = j+1;
                String name =res.get(i).getNombre();
                if (name.equalsIgnoreCase((x+""))) revision[j]++;
                if (revision[j]>2) assertEquals(2, revision[j]);
            }
            System.out.print("\n"+i+" - "+res.get(i)+" - "+n+" - "+ Arrays.toString(revision)+"\n");

            if (n==(jugadores.length*2)-1){
                revision = new int[jugadores.length];
            }
            n = n==(jugadores.length*2)-1?0:n+1;

        }


        System.out.print("\n\nfin test\n\n");
        System.out.print(System.nanoTime());
        assertEquals(0, 0);
    }
}