package com.example.tallervideojuego.controlador;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.ArrayList;

public class EditarCartaControler extends Controlador {

    private AppCompatActivity act;

    private Button btnAgregar, btnGuardar, btnCancelar;

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
        }

    }

    public void fill(){

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
                    if (categoriasRelacionadas.contains(categoria)){
                        Toast.makeText(act, "Esa categoría ya fue agregada", Toast.LENGTH_SHORT).show();
                    } else {
                        categoriasRelacionadas.add(categoria);

                        adapterCategorías = new AdapterCategorías(act.getApplicationContext(),categoriasRelacionadas);
                        listCat.setAdapter(adapterCategorías);
                    }

                }
            }
        };
    }

    public View.OnClickListener delet(Categoria cat){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoriasRelacionadas.remove(cat);
            }
        };
    }

    public View.OnClickListener save(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, BancoPreguntas_act.class);

                act.startActivity(intent);
                Toast.makeText(act, "Se guardaron los cambios correctamente ", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public View.OnClickListener cancel(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, BancoPreguntas_act.class);

                act.startActivity(intent);
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
}
