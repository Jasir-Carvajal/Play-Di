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
import com.example.tallervideojuego.modelo.entidades.Carta;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.modelo.registro.RegistroCategorias;
import com.example.tallervideojuego.vista.Menu_act;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public final class MenuControler extends Controlador {
    private Menu_act menu;
    private LinearLayout container;
    private LinearLayout[] personalizados;
    private Button add, bancoPreguntas, regresar;
    private RegistroCategorias registro;

    public MenuControler(AppCompatActivity act) {
        menu = (Menu_act) act;
        registro = RegistroCategorias.getInstance();
        container = menu.findViewById(R.id.cat_container);
        add = menu.findViewById(R.id.add);
        regresar = menu.findViewById(R.id.regresar);
        bancoPreguntas = menu.findViewById(R.id.bancoPreguntas);
        addButtons();
        setFunctions();
    }

    private void addButtons() {
        ArrayList<Entidad> lista = registro.getEntidades();

        personalizados = new LinearLayout[lista.size()];


        LinearLayout.LayoutParams params_lyt = (LinearLayout.LayoutParams) menu.findViewById(R.id.lyt_personalizado).getLayoutParams();
        LinearLayout.LayoutParams params_btn_cat = (LinearLayout.LayoutParams) menu.findViewById(R.id.btn_personalizado).getLayoutParams();
        LinearLayout.LayoutParams params_btn_edit = (LinearLayout.LayoutParams) menu.findViewById(R.id.btnd_edit_personalizado).getLayoutParams();



        ContextThemeWrapper contextCat = new ContextThemeWrapper(menu, R.style.btn_cat);
        ContextThemeWrapper contextEdit = new ContextThemeWrapper(menu, R.style.edit_btn);
        ContextThemeWrapper contextLyt = new ContextThemeWrapper(menu, R.style.lyt_personalizados);

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

        Toast.makeText(menu, "Prueba btns", Toast.LENGTH_SHORT).show();
    }

    private void setFunctions(){
        add.setOnClickListener(onAdd());
        regresar.setOnClickListener(onRegresa());
        bancoPreguntas.setOnClickListener(onBancoPreguntas());

    }

    private View.OnClickListener onEdit(int id) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(menu, "editar "+((Categoria)registro.search(id)).getTitulo(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private View.OnClickListener onBancoPreguntas() {
        return null;
    }

    private View.OnClickListener onRegresa() {
        return null;
    }

    private View.OnClickListener onAdd() {
        return null;
    }

    public View.OnClickListener random(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }
}
