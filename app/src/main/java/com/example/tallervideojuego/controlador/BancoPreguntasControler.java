package com.example.tallervideojuego.controlador;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.bace.Controlador;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Carta;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.modelo.registro.RegistroCat_Car;
import com.example.tallervideojuego.vista.EditarCarta_act;
import com.example.tallervideojuego.vista.MenuCategorias_act;

import java.util.ArrayList;

public class BancoPreguntasControler extends Controlador{

    /**
     * La clase controlador para el activity banco_preguntas
     *
     * adapter = adapter para el spinner
     * itemSelected = el item que esta seleccionado en el spinner en ese momento
     */

    private AppCompatActivity act;

    private Button btnAplicar, btnAgregar, btnRegresar;

    private Spinner spinnerCat;
    private AdapterBancoPreguntas adapterBancoPreguntas;

    private Registro registroCategorias;
    private Registro registroCartas;
    private RegistroCat_Car registroRelacion;





    private ArrayAdapter<CharSequence> adapter;
    private ArrayList<String> catItems = new ArrayList<>();

    private ListView listaE;
    private String itemSelected;


    /**
     * Constructor de la clase
     * @param act La referencia del activity donde se inicializa el controlador
     */
    public BancoPreguntasControler(AppCompatActivity act) {
        super(act);
        this.act = act;

        btnAplicar = this.act.findViewById(R.id.btnAplicar);
        btnAgregar = this.act.findViewById(R.id.btnAgregar);
        btnRegresar = this.act.findViewById(R.id.btnRegresar);
        listaE = this.act.findViewById(R.id.lista_categorias);


        registroCategorias = new Registro(Categoria.class);
        registroCartas = new Registro(Carta.class);
        registroRelacion = new RegistroCat_Car();



        //  = this.act.findViewById(R.id.cartas_container);


        catItems = getStrings();

        catItems.add(0,"TODAS");

        //////////SPINNER MANEJO/////////////
        spinnerCat = act.findViewById(R.id.spinnerCat);

        adapter = new ArrayAdapter(this.act, R.layout.style_spinner,catItems);
        adapter.setDropDownViewResource(R.layout.style_dropdown_spinner);
        spinnerCat.setAdapter(adapter);

        //Manejo para controlar cada vez que el spinner cambia de seleccion
        spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemSelected = adapterView.getItemAtPosition(i).toString();
                apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //////////SPINNER MANEJO/////////////

        setFunctions();


        update(registroCartas.getListaEntidades());
    }


    /**
     * Este método funciona para asignar los View.OnClickListener a los botones o elementos necesarios
     */
    public void setFunctions(){
        btnAgregar.setOnClickListener(add());
        btnAplicar.setOnClickListener(apply());
        btnRegresar.setOnClickListener(onReturn());
    }

    /**
     * MÉTODO para la funcion de editar, se asigna a los botones en el AdapterBancoPreguntas
     * @param entidad Es la entidad que se pasa en el AdapterBancoPreguntas
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener edit(Entidad entidad){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = entidad.getId();
                Intent intent = new Intent(act, EditarCarta_act.class);
                intent.putExtra("id",id);
                act.startActivity(intent);

            }
        };
    }

    /**
     * MÉTODO para la funcion de eliminar, se asigna a los botones en el AdapterBancoPreguntas
     * @param entidad Es la entidad que se pasa en el AdapterBancoPreguntas
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener delete(Entidad entidad){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registroCartas.delete(entidad);
                Carta carta = (Carta) entidad;


                //Se obtiene la lista de categorias con las cuales la carta tiene una relacion
                ArrayList<Categoria> categoriasRelacionadas = carta.getCategoriasDeCartas();

                //Se recorre esta lista y se van eliminando esas relaciones
                for (Categoria categoria:categoriasRelacionadas){
                    carta.removeCategoria(categoria);
                }

                //Se actualiza la lista del adapter
                update(registroCartas.getListaEntidades());
            }
        };
    }

    /**
     * MÉTODO para la funcion de regresar (regresa a la pantalla anterior)
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener onReturn(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, MenuCategorias_act.class);
                act.startActivity(intent);
            }
        };
    }

    /**
     * Este método actualiza el adapter se llama cada vez que se hace un cambio a  la lista de retos
     * @param lista_usable lista de retos actualizada
     */
    private void update(ArrayList<Entidad> lista_usable) {
        adapterBancoPreguntas = new AdapterBancoPreguntas(lista_usable,act,this);
        listaE.setAdapter(adapterBancoPreguntas);
    }

    /**
     * MÉTODO para la funcion de agregar
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener add(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, EditarCarta_act.class);
                act.startActivity(intent);
            }
        };
    }

    /**
     * MÉTODO para la funcion de aplicar el filtro de categoria
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener apply(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Si en el spinner se tiene seleccionado "TODAS" se muestran todos los retos
               if(itemSelected.equalsIgnoreCase("TODAS")) {
                   update(registroCartas.getEntidades());

               //Si no se muestran los retos relacionados a la categoria seleccionada
               } else {
                   Categoria categoria = (Categoria) registroCategorias.search("titulo", itemSelected);
                   categoria.setRegistroCat_car(registroRelacion);
                   ArrayList<Entidad> cartas = categoria.getCartasDeCategoria();
                   update(cartas);
               }
            }
        };
    }

    /**
     * Este método obtiene los nombres de las categorias existentes, para utilizar en el spinner
     * @return Retorna la lista de String con los nombres
     */
    public ArrayList<String> getStrings(){
        ArrayList<String> listArray = new ArrayList<>();

        for (int i = 0; i< registroCategorias.getListaEntidades().size(); i++){

            Categoria categoria = (Categoria) registroCategorias.getEntidades().get(i);

            listArray.add(categoria.getTitulo());
        }

        return  listArray;
    }



}
