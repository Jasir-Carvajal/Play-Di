package com.example.tallervideojuego.controlador;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.bace.Controlador;
import com.example.tallervideojuego.modelo.AdapterCategorías;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Carta;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.modelo.registro.RegistroCat_Car;

import java.util.ArrayList;

public class EditarCartaControler extends Controlador {

    /**
     * La clase controlador para el activity editar_carta
     *
     * categoriasRelacionadas = la lista de categorias relacionadas a la carta
     * categoriasRelacionadas = la lista de categorias que antes estaban relacionadas, pero ahora se van a eliminar
     */

    private AppCompatActivity act;

    private Button btnAgregar, btnGuardar, btnCancelar;
    private EditText txtTitulo, txtDescripcion, txtCastigo;
    private ListView listCat;

    private ScrollView miScrollView;
    private Spinner spinnerCat;
    private ArrayAdapter<CharSequence> adapter;

    private Registro registroCategorias;
    private Registro registroCartas;
    private RegistroCat_Car registroRelacion;

    private ArrayList<String> catItems = new ArrayList<>();

    private ArrayList<Categoria> categoriasRelacionadas = new ArrayList<>();
    private ArrayList<Categoria> categoriasEliminadas = new ArrayList<>();

    private String itemSelected;

    private AdapterCategorías adapterCategorías;

    private Carta carta;
    private Boolean isNew;

    /**
     * Constructor de la clase
     * @param act La referencia del activity donde se inicializa el controlador
     */
    @SuppressLint("ClickableViewAccessibility")
    public EditarCartaControler(AppCompatActivity act) {
        super(act);
        this.act = act;

        int id = act.getIntent().getIntExtra("id",-1);



        btnAgregar = this.act.findViewById(R.id.btnAgregar);
        btnCancelar = this.act.findViewById(R.id.btnCancelar);
        btnGuardar = this.act.findViewById(R.id.btnGuardar);

        txtTitulo = this.act.findViewById(R.id.txtTitulo);
        txtDescripcion = this.act.findViewById(R.id.txtDescripcion);
        txtCastigo = this.act.findViewById(R.id.txtCastigo);

        listCat = this.act.findViewById(R.id.listCat);
        miScrollView = this.act.findViewById(R.id.miScrollView);

        registroCategorias = new Registro(Categoria.class);
        registroCartas = new Registro(Carta.class);
        registroRelacion = new RegistroCat_Car();

        catItems = getStrings();


        //////////SPINNER MANEJO/////////////
        spinnerCat = this.act.findViewById(R.id.spinner);

        adapter = new ArrayAdapter(this.act, R.layout.style_spinner,catItems);
        adapter.setDropDownViewResource(R.layout.style_dropdown_spinner);
        spinnerCat.setAdapter(adapter);

        spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemSelected = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //////////SPINNER MANEJO/////////////



        setFunctions();

        //Al ser una pantalla en la cual se pueden tanto crear nuevas cartas como editar cartas existentes,se necesita comprobar si es nueva o no
        //Esto se maneja aquí, si es nueva se crea una nueva referencia, y si no, se busca en la base de datos
        if(id!=-1){
            carta = (Carta) registroCartas.search(id);
            fill();
            isNew = false;
        }else{
            carta = new Carta();
            isNew = true;
            //message(""+carta.getId());
        }

    }

    /**
     * Este método rellena los campos en caso de que la carta NO sea nueva y al contrario, se va a editar
     */
    public void fill(){
        txtTitulo.setText(carta.getTitulo());
        txtDescripcion.setText(carta.getReto());
        txtCastigo.setText(carta.getCastigo());



        categoriasRelacionadas = carta.getCategoriasDeCartas();

        updateAdapter(categoriasRelacionadas);
    }

    /**
     * Este método funciona para asignar los View.OnClickListener a los botones o elementos necesarios
     */
    public void setFunctions(){
        btnAgregar.setOnClickListener(add());
        btnGuardar.setOnClickListener(save());
        btnCancelar.setOnClickListener(cancel());

        miScrollView.setOnTouchListener(scrollTouch());
        listCat.setOnTouchListener(listViewTouch());
    }


    /**
     * MÉTODO para la funcion de agregar una nueva categoria asociada a la carta, este metodo se asigna a los botones en el AdapterCategorias
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener add(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Categoria categoria = getCatPorTitulo(itemSelected);

                //Se comprueba que la categoria que se quiere agregar se una valida
                if(categoria !=  null){
                    //Se comprueba que no haya sido agregada antes
                    if (!comprobarCat(itemSelected)){
                        Toast.makeText(act, "Esa categoría ya fue agregada", Toast.LENGTH_SHORT).show();
                    } else {
                        categoriasRelacionadas.add(categoria);

                        for (Categoria categoria2:categoriasEliminadas){
                            if (categoria2.getTitulo().equalsIgnoreCase(categoria.getTitulo())){
                                categoriasEliminadas.remove(categoria2);
                            }
                        }

                        updateAdapter(categoriasRelacionadas);
                    }

                }
            }
        };
    }
    /**
     * MÉTODO para la funcion de eliminar las categorias relacionadas, este metodo se asigna a los botones en el AdapterCategorias
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener delete(Categoria cat){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Elimina la categoria de las relacionadas y la agrega a las que se van a eliminar
                categoriasRelacionadas.remove(cat);
                updateAdapter(categoriasRelacionadas);
                categoriasEliminadas.add(cat);
//                carta.setRegistroCat_car(registroRelacion);
//                carta.removeCategoria(cat);
            }
        };
    }

    /**
     * MÉTODO para la funcion de guardar los datos creados (Si es nueva) o los cambios realizados (Si se esta editando una ya existente)
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener save(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Se comprueba que los campos de texto NO esten vacios
                if(txtTitulo.getText().toString().trim().isEmpty()
                        || txtDescripcion.getText().toString().trim().isEmpty()
                        || txtCastigo.getText().toString().trim().isEmpty() || categoriasRelacionadas.isEmpty()){

                    message("Debe de ingresar todos los valores");

                } else {
                    //Se comprueba si es nueva o no
                    if (!isNew){

                        Entidad old = registroCartas.search("titulo",txtTitulo.getText().toString().trim());

                        //Se comprueba que el nombre no exista, ya que los nombres deben de ser unicos
                        if (old == null || old.getId() == carta.getId()) {
                            //Se asignan los valores
                            carta.setTitulo(txtTitulo.getText().toString().trim());
                            carta.setReto(txtDescripcion.getText().toString().trim());
                            carta.setCastigo(txtCastigo.getText().toString().trim());


                            for (Categoria categoria:categoriasRelacionadas){
                                carta.addCategoria(categoria);
                            }

                            for (Categoria categoria:categoriasEliminadas){
                                carta.removeCategoria(categoria);
                            }

                            //Se actualiza la carta
                            registroCartas.update(carta);

                            regresar();
                            //Intent intent = new Intent(act, BancoPreguntas_act.class);
                            //act.startActivity(intent);
                        } else message("Ese nombre ya existe");

                    } else {
                        Entidad old = registroCartas.search("titulo",txtTitulo.getText().toString().trim());

                        //Se comprueba que el nombre no exista, ya que los nombres deben de ser unicos
                        if (old == null) {

                            //Se asignan los valores
                            carta.setTitulo(txtTitulo.getText().toString().trim());
                            carta.setReto(txtDescripcion.getText().toString().trim());
                            carta.setCastigo(txtCastigo.getText().toString().trim());

                            registroCartas.add(carta);

                            carta = (Carta) registroCartas.getEntidades().get(0);



                            for (Categoria categoria:categoriasRelacionadas){
                                carta.addCategoria(categoria);
                            }


                             regresar();
                            //Intent intent = new Intent(act, BancoPreguntas_act.class);
                           // act.startActivity(intent);
                        } else message("Ese nombre ya existe");
                    }
                }
            }
        };
    }

    /**
     * MÉTODO para la funcion de cancelar la creacion o edicion de la carta, devuelve a la pantalla anterior
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener cancel(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresar();
            }
        };
    }

    /**
     * MÉTODO que controla si estoy tocando el ScrollView
     * @return Retorna el View.OnTouchListener
     */
    public View.OnTouchListener scrollTouch(){
        return new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                act.findViewById(R.id.listCat).getParent()
                        .requestDisallowInterceptTouchEvent(false);
                return false;
            }
        };
    }

    /**
     * MÉTODO que controla si estoy tocando el listView
     * @return Retorna el View.OnTouchListener
     */
    public View.OnTouchListener listViewTouch(){
        return new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
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

    /**
     * Este método comprueba que el nombre que ingresa por parametros sea el nombre de alguna categoria existente
     * @param titulo nombre que se desea comprobar
     * @return Retorna la categoria a la cual pertenece el nombre (si no retorna null)
     */
    public Categoria getCatPorTitulo(String titulo){

        Categoria cat = null;

        for(Entidad entidad: registroCategorias.getEntidades()){
            Categoria categoria = (Categoria) entidad;

            if(categoria.getTitulo().equalsIgnoreCase(titulo)){
                cat = categoria;
            }
        }
        return cat;
    }

    /**
     * Este método comprueba que la categoria no este ya asignada
     * @param titulo nombre que se desea comprobar
     * @return Retorna true si no esta asignada y false si ya se encuentra
     */
    public boolean comprobarCat (String titulo){
        for(Categoria categoria1: categoriasRelacionadas){
            Categoria categoria = categoria1;

            if(categoria.getTitulo().equalsIgnoreCase(titulo)){
                return false;
            }
        }
        return true;
    }


    /**
     * Este método actualiza el adapter se llama cada vez que se hace un cambio a  la lista de de categorias
     * @param lista_usable lista de categorias actualizada
     */
    private void updateAdapter(ArrayList<Categoria> lista_usable) {
        adapterCategorías = new AdapterCategorías(act,lista_usable,this);
        listCat.setAdapter(adapterCategorías);
    }

    /**
     * MÉTODO para crear un mensaje por Toast
     * @param text texto para el mensaje
     */
    public void message(String text){
        Toast.makeText(act, text, Toast.LENGTH_SHORT).show();
    }
}
