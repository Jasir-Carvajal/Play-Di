package com.example.tallervideojuego.controlador;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.base.Controlador;
import com.example.tallervideojuego.modelo.Adapters.AdapterJugarCategoria;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.vista.AddJugadores_act;

import java.util.ArrayList;

public class JugarCategoriasControler extends Controlador {

    private AppCompatActivity act;

    private ListView listaAdapterCat;

    private Registro registroCategorias;

    private AdapterJugarCategoria adapterJugarCategoria;

    /**
     * Constructor de la clase
     *
     * @param act se refiere al Activity que corresponde este controlador
     */
    public JugarCategoriasControler(AppCompatActivity act) {
        super(act);

        this.act = act;

        listaAdapterCat = this.act.findViewById(R.id.listaAdapterCat);

        registroCategorias = new Registro(Categoria.class);

        adapterJugarCategoria = new AdapterJugarCategoria(this.act, registroCategorias.getListaEntidades(), this);
        listaAdapterCat.setAdapter(adapterJugarCategoria);

    }


    /**
     * MÉTODO para la funcion de jugar (dirige a la pantalla de añadir jugadores)
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener onPlay(int id){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, AddJugadores_act.class);

                intent.putExtra("id",id);

                act.startActivity(intent);

            }
        };
    }
}
