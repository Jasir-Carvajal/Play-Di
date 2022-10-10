package com.example.tallervideojuego.controlador;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.tallervideojuego.vista.BancoPreguntas_act;
import com.example.tallervideojuego.vista.Menu_act;

import java.util.ArrayList;

public class EditarCartaControler extends Controlador {

    private AppCompatActivity act;

    private Button btnAgregar, btnGuardar, btnCancelar;
    private EditText txtTitulo, txtDescripcion, txtCastigo;
    private ListView listCat;

    private ScrollView miScrollView;
    private Spinner spinnerCat;
    ArrayAdapter<CharSequence> adapter;

    private Registro registroCategorias;
    private Registro registroCartas;
    private RegistroCat_Car registroRelacion;

    private ArrayList<String> catItems = new ArrayList<>();

    private ArrayList<Categoria> categoriasRelacionadas = new ArrayList<>();

    private String itemSelected;

    private AdapterCategorías adapterCategorías;

    private Carta carta;
    private Boolean isNew;

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

    public void fill(){
        txtTitulo.setText(carta.getTitulo());
        txtDescripcion.setText(carta.getReto());
        txtCastigo.setText(carta.getCastigo());

        carta.setRegistroCat_car(registroRelacion);

        categoriasRelacionadas = carta.getCategoriasDeCartas();

        update(categoriasRelacionadas);
    }

    public void setFunctions(){
        btnAgregar.setOnClickListener(add());
        btnGuardar.setOnClickListener(save());
        btnCancelar.setOnClickListener(cancel());

        miScrollView.setOnTouchListener(scrollTouch());
        listCat.setOnTouchListener(listViewTouch());
    }



    public View.OnClickListener add(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Categoria categoria = getCatPorTitulo(itemSelected);

                if(categoria !=  null){
                    if (!comprobarCat(itemSelected)){
                        Toast.makeText(act, "Esa categoría ya fue agregada", Toast.LENGTH_SHORT).show();
                    } else {
                        categoriasRelacionadas.add(categoria);

                        update(categoriasRelacionadas);
                    }

                }
            }
        };
    }

    public View.OnClickListener delete(Categoria cat){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoriasRelacionadas.remove(cat);
                update(categoriasRelacionadas);
            }
        };
    }

    public View.OnClickListener save(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtTitulo.getText().toString().trim().isEmpty()
                        || txtDescripcion.getText().toString().trim().isEmpty()
                        || txtCastigo.getText().toString().trim().isEmpty() || categoriasRelacionadas.isEmpty()){

                    message("Debe de ingresar todos los valores");

                } else {
                    if (!isNew){
                        Entidad old = registroCartas.search("titulo",txtTitulo.getText().toString().trim());

                        if (old.getId() == carta.getId()) {
                            carta.setTitulo(txtTitulo.getText().toString().trim());
                            carta.setReto(txtDescripcion.getText().toString().trim());
                            carta.setCastigo(txtCastigo.getText().toString().trim());
                            carta.setRegistroCat_car(registroRelacion);

                            for (Categoria categoria:categoriasRelacionadas){
                                carta.addCategoria(categoria);
                            }

                            registroCartas.update(carta);

                            Intent intent = new Intent(act, BancoPreguntas_act.class);
                            act.startActivity(intent);
                        } else message("Ese nombre ya existe");

                    } else {
                        Entidad old = registroCartas.search("titulo",txtTitulo.getText().toString().trim());

                        if (old == null) {
                            carta.setTitulo(txtTitulo.getText().toString().trim());
                            carta.setReto(txtDescripcion.getText().toString().trim());
                            carta.setCastigo(txtCastigo.getText().toString().trim());

                            registroCartas.add(carta);

                            carta = (Carta) registroCartas.getEntidades().get(0);
                            carta.setRegistroCat_car(registroRelacion);


                            for (Categoria categoria:categoriasRelacionadas){
                                carta.addCategoria(categoria);
                            }


                            Intent intent = new Intent(act, BancoPreguntas_act.class);
                            act.startActivity(intent);
                        } else message("Ese nombre ya existe");
                    }
                }
            }
        };
    }

    public View.OnClickListener cancel(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }

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

    public View.OnTouchListener listViewTouch(){
        return new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        };
    }

    public ArrayList<String> getStrings(){
        ArrayList<String> listArray = new ArrayList<>();

        for (int i = 0; i< registroCategorias.getListaEntidades().size(); i++){

            Categoria categoria = (Categoria) registroCategorias.getEntidades().get(i);

            listArray.add(categoria.getTitulo());
        }

        return  listArray;
    }

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

    public boolean comprobarCat (String titulo){
        for(Categoria categoria1: categoriasRelacionadas){
            Categoria categoria = categoria1;

            if(categoria.getTitulo().equalsIgnoreCase(titulo)){
                return false;
            }
        }
        return true;
    }

    private void update(ArrayList<Categoria> lista_usable) {
        adapterCategorías = new AdapterCategorías(act,lista_usable,this);
        listCat.setAdapter(adapterCategorías);
    }

    public void message(String text){
        Toast.makeText(act, text, Toast.LENGTH_SHORT).show();
    }
}
