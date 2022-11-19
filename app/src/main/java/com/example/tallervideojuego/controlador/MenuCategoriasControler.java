package com.example.tallervideojuego.controlador;


import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.base.Controlador;
import com.example.tallervideojuego.modelo.Adapters.AdapterBancoPreguntas;
import com.example.tallervideojuego.modelo.Adapters.AdapterMenuCategorias;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.modelo.registro.RegistroCat_Car;
import com.example.tallervideojuego.vista.AddJugadores_act;
import com.example.tallervideojuego.vista.BancoPreguntas_act;
import com.example.tallervideojuego.vista.EditarCategoria_act;
import com.example.tallervideojuego.vista.MenuCategorias_act;

import java.util.ArrayList;

public final class MenuCategoriasControler extends Controlador{
    /**
     * La clase controlador para el activity menu
     */

    private AppCompatActivity act;

    private Button btnAdd;
    private ListView listAdapterCat;

    private Registro registroCategorias;
    private RegistroCat_Car registroRelacion;

    private AdapterMenuCategorias adapterMenuCategorias;

    private int alto;

    /**
     * Constructor de la clase
     * @param act La referencia del activity donde se inicializa el controlador
     */
    public MenuCategoriasControler(AppCompatActivity act) {
        super(act);

        this.act=act;

        registroCategorias = new Registro(Categoria.class);
        registroRelacion = new RegistroCat_Car();

        btnAdd = this.act.findViewById(R.id.btnAdd);
        listAdapterCat = this.act.findViewById(R.id.listAdapterCat);


        Registro cambios = new Registro("Cambios");
        Toast.makeText(act, "Cambios:" +cambios.getEntidades().size(), Toast.LENGTH_SHORT).show();

        setFunctions();

        update(registroCategorias.getEntidades());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        alto = displayMetrics.heightPixels;

        if (alto>2800 ) listAdapterCat.getLayoutParams().height = size(1F);
        if (alto>1900 && alto<2800) listAdapterCat.getLayoutParams().height = size(0.65F);
        if ( alto<1900) listAdapterCat.getLayoutParams().height = size(0.35F);

    }

    /**
     * Este método funciona para asignar los View.OnClickListener a los botones o elementos necesarios
     */
    private void setFunctions(){
        btnAdd.setOnClickListener(onAdd());

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

    /**
     * MÉTODO para la funcion de editar categoria
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener edit(Entidad entidad) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(act, EditarCategoria_act.class);

                intent.putExtra("id",entidad.getId());

                act.startActivity(intent);

            }
        };
    }



    /**
     * MÉTODO para la funcion de añadir categoria
     * @return Retorna el View.OnClickListener
     */
    private View.OnClickListener onAdd() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, EditarCategoria_act.class);
                act.startActivity(intent);
            }
        };
    }

    /**
     * MÉTODO para la funcion de eliminar la categoria
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener delete(Entidad entidad){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Categoria cat = (Categoria) entidad;

//                cat.setRegistroCat_car(registroRelacion);
                ArrayList<Entidad> cartasRelacionadas = cat.getCartasDeCategoria();

                if(cartasRelacionadas.isEmpty()){
                    registroCategorias.delete(cat);
                    updateDelete(registroCategorias.getEntidades());
                } else {
                    message("Esta categoría esta asignada, no se puede eliminar");
                }

            }
        };
    }

    /**
     * Este método actualiza el adapter se llama cada vez que se hace un cambio a  la lista de retos
     * @param lista_usable lista de retos actualizada
     */
    private void update(ArrayList<Entidad> lista_usable) {

        lista_usable.remove(lista_usable.size()-1);

        adapterMenuCategorias = new AdapterMenuCategorias(act, lista_usable, this);
        listAdapterCat.setAdapter(adapterMenuCategorias);
    }

    /**
     * Este método actualiza el adapter se llama cada vez que se hace un cambio a  la lista de retos
     * @param lista_usable lista de retos actualizada
     */
    private void updateDelete(ArrayList<Entidad> lista_usable) {


        adapterMenuCategorias = new AdapterMenuCategorias(act, lista_usable, this);
        listAdapterCat.setAdapter(adapterMenuCategorias);
    }

    public int size(float porcentaje){

        return Math.round(porcentaje*alto);

    }







}
