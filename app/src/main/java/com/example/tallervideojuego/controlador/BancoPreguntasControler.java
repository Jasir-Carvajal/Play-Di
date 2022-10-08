package com.example.tallervideojuego.controlador;

import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.bace.Controlador;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Carta;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.modelo.registro.RegistroCat_Car;
import com.example.tallervideojuego.vista.EditarCarta_act;

import java.util.ArrayList;

public class BancoPreguntasControler extends Controlador{

    private AppCompatActivity act;

    private Button btnAplicar, btnAgregar;

    private Spinner spinnerCat;

    private Registro registroCategorias;
    private Registro registroCartas;
    private RegistroCat_Car registroRelacion;

    private LinearLayout container;
    private LinearLayout[] personalizados;

    private ArrayAdapter<CharSequence> adapter;
    private ArrayList<String> catItems = new ArrayList<>();
    private ArrayList<Entidad> listaCartas;
    private ArrayList<Entidad> listaCartasFiltradas;

    private String itemSelected;



    public BancoPreguntasControler(AppCompatActivity act) {
        super(act);
        this.act = act;

        btnAplicar = this.act.findViewById(R.id.btnAplicar);
        btnAgregar = this.act.findViewById(R.id.btnAgregar);

        registroCategorias = new Registro(Categoria.class);
        registroCartas = new Registro(Carta.class);
        registroRelacion = new RegistroCat_Car();


        container = this.act.findViewById(R.id.cartas_container);


        catItems = getStrings();

        catItems.add(0,"TODAS");

        //////////SPINNER MANEJO/////////////
        spinnerCat = act.findViewById(R.id.spinnerCat);

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


        addCartas();
    }

    public void addCartas(){

        listaCartas = registroCartas.getEntidades();

        personalizados = new LinearLayout[listaCartas.size()];

        ContextThemeWrapper contextLytExterno = new ContextThemeWrapper(act, R.style.lyt_cartas_externo);
        ContextThemeWrapper contextTitulo = new ContextThemeWrapper(act,R.style.txt_titulo);
        ContextThemeWrapper contextLytInterno = new ContextThemeWrapper(act, R.style.lyt_cartas_interno);
        ContextThemeWrapper contextEliminar = new ContextThemeWrapper(act, R.style.edit_btn);
        ContextThemeWrapper contextEditar = new ContextThemeWrapper(act, R.style.edit_btn);

        LinearLayout.LayoutParams params_lytExterno =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params_lytExterno.leftMargin = dp(40);
        params_lytExterno.topMargin = dp(30);
        params_lytExterno.rightMargin = dp(40);


        LinearLayout.LayoutParams params_txt_tiulo =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params_txt_tiulo.topMargin = dp(20);
        params_txt_tiulo.leftMargin = dp(10);

        LinearLayout.LayoutParams params_lytInterno =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params_lytInterno.leftMargin = dp(10);
        params_lytInterno.topMargin = dp(50);
        params_lytInterno.rightMargin = dp(10);
        params_lytInterno.bottomMargin = dp(30);

        LinearLayout.LayoutParams params_btn_eliminar = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params_btn_eliminar.width=0;
        params_btn_eliminar.weight= 1;
        params_btn_eliminar.rightMargin = dp(10);

        LinearLayout.LayoutParams params_btn_editar = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params_btn_editar.width=0;
        params_btn_editar.weight= 1;
        params_btn_editar.leftMargin = dp(10);

        for(Entidad entidad: listaCartas){
            Carta carta = (Carta) entidad;

            personalizados[listaCartas.indexOf(entidad)] = new LinearLayout(contextLytExterno);
            TextView titulo = new TextView(contextTitulo);

            LinearLayout lytInterno = new LinearLayout(contextLytInterno);
            Button btn_eliminar = new Button(contextEliminar);
            Button btn_editar = new Button(contextEditar);

            btn_eliminar.setBackgroundResource(R.drawable.btns);
            btn_editar.setBackgroundResource(R.drawable.btns);


            personalizados[listaCartas.indexOf(entidad)] .setLayoutParams(params_lytExterno);

            titulo.setLayoutParams(params_txt_tiulo);

            lytInterno.setLayoutParams(params_lytInterno);
            btn_eliminar.setLayoutParams(params_btn_eliminar);
            btn_editar.setLayoutParams(params_btn_editar);


            btn_eliminar.setText("Eliminar");
            btn_editar.setText("Editar");
            titulo.setText(carta.getTitulo());

            btn_eliminar.setTypeface(ResourcesCompat.getFont(act.getApplicationContext(), R.font.fira_bold));
            btn_editar.setTypeface(ResourcesCompat.getFont(act.getApplicationContext(), R.font.fira_bold));
            titulo.setTypeface(ResourcesCompat.getFont(act.getApplicationContext(), R.font.fira_bold));

            lytInterno.addView(btn_eliminar,0);
            lytInterno.addView(btn_editar,1);

            btn_editar.setOnClickListener(edit(carta.getId()));
            btn_eliminar.setOnClickListener(delete(carta));

            personalizados[listaCartas.indexOf(entidad)] .addView(titulo,0);
            personalizados[listaCartas.indexOf(entidad)] .addView(lytInterno,1);
            container.addView(personalizados[listaCartas.indexOf(entidad)] );
        }


    }

    public void setFunctions(){
        btnAgregar.setOnClickListener(add());
    }

    public View.OnClickListener edit(int id){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, EditarCarta_act.class);

                intent.putExtra("id",id);

                act.startActivity(intent);
            }
        };
    }

    public View.OnClickListener delete(Entidad entidad){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registroCartas.delete(entidad);
                act.recreate();
            }
        };
    }

    public View.OnClickListener add(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Carta carta = new Carta();

                carta.setTitulo("IMITAR A UN MONO");
                carta.setCastigo("Castigo ");
                carta.setReto("Rteo");

                registroCartas.add(carta);
                act.recreate();
            }
        };
    }

    public View.OnClickListener apply(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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



}
