package com.example.tallervideojuego.controlador;


import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.bace.Controlador;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.modelo.registro.RegistroCategorias;
import com.example.tallervideojuego.vista.Menu_act;

import java.util.ArrayList;

public final class MenuControler extends Controlador {
    private LinearLayout container;
    private LinearLayout[] personalizados;
    private Button add, bancoPreguntas, regresar;
    private RegistroCategorias registro;

    public MenuControler(AppCompatActivity act) {
        super(act);
        registro = RegistroCategorias.getInstance();
        container = this.act.findViewById(R.id.cat_container);
        add = this.act.findViewById(R.id.add);
        regresar = this.act.findViewById(R.id.regresar);
        bancoPreguntas = this.act.findViewById(R.id.bancoPreguntas);
        //____________________________________________________________
        //____________________________________________________________
        //____________________________________________________________
        registro.add(new Categoria(0,"Prueba1"));
        //____________________________________________________________
        //____________________________________________________________
        //____________________________________________________________

        addButtons();
        setFunctions();
    }

    private void addButtons() {

        ArrayList<Entidad> lista = registro.getEntidades();

        System.out.println(lista.size());

        personalizados = new LinearLayout[lista.size()];

        ContextThemeWrapper contextCat = new ContextThemeWrapper(act, R.style.btn_cat);
        ContextThemeWrapper contextEdit = new ContextThemeWrapper(act, R.style.edit_btn);
        ContextThemeWrapper contextLyt = new ContextThemeWrapper(act, R.style.lyt_personalizados);

        LinearLayout.LayoutParams params_lyt =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params_lyt.leftMargin = dp(40);//por alguna razón no sirve con 30 como en de random
        params_lyt.topMargin = dp(10);
        params_lyt.rightMargin = dp(40);//

        LinearLayout.LayoutParams params_btn_cat = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params_btn_cat.width=0;
        params_btn_cat.weight= (float) 0.65;
        params_btn_cat.topMargin =dp(8);
        params_btn_cat.bottomMargin = dp(8);


        LinearLayout.LayoutParams params_btn_edit = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params_btn_edit.width=0;
        params_btn_edit.weight= (float) 0.35;
        params_btn_edit.topMargin = dp(8);
        params_btn_edit.rightMargin = dp(8);
        params_btn_edit.bottomMargin = dp(8);




        for (Entidad entidad:lista) {
            //obtiene los parametros de layaout de los botones ya definodos
            Categoria cat = (Categoria)entidad;
            personalizados[lista.indexOf(entidad)] = new LinearLayout(contextLyt);
            Button btn_cat = new Button(contextCat);
            Button btn_editar = new Button(contextEdit);

            btn_editar.setBackgroundResource(R.drawable.btns);
            //aplica parametros de layaout anteriormente obtenidos
            btn_cat.setLayoutParams(params_btn_cat);
            btn_editar.setLayoutParams(params_btn_edit);
            personalizados[lista.indexOf(entidad)] .setLayoutParams(params_lyt);

            //quita sombra
            btn_cat.setStateListAnimator(null);
            btn_editar.setStateListAnimator(null);
            //coloca titulos
            btn_cat.setText(cat.getTitulo());
            btn_editar.setText("Editar");

            btn_editar.setOnClickListener(onEdit(cat.getId()));

            //añade a la vista
            personalizados[lista.indexOf(entidad)] .addView(btn_cat,0);
            personalizados[lista.indexOf(entidad)] .addView(btn_editar,1);
            container.addView(personalizados[lista.indexOf(entidad)] );
        }

        Toast.makeText(act, "Prueba btns", Toast.LENGTH_SHORT).show();
    }

    private void setFunctions(){
        add.setOnClickListener(onAdd());
       // regresar.setOnClickListener(onRegresa());
        bancoPreguntas.setOnClickListener(onBancoPreguntas());

    }

    private View.OnClickListener onEdit(int id) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(act, "editar "+((Categoria)registro.search(id)).getTitulo(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private View.OnClickListener onBancoPreguntas() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(act, "editar ", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private View.OnClickListener onRegresa() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(act, "editar ", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private View.OnClickListener onAdd() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(act, "editar ", Toast.LENGTH_SHORT).show();
            }
        };
    }


    public View.OnClickListener random(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }
}
