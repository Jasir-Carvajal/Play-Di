package com.example.tallervideojuego.controlador;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.bace.Controlador;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.vista.Menu_act;

public class EditarCategoriasControler extends Controlador {

    private EditText txtTitulo;
    private Button btnRegresar, btnGuardar,btnEliminar;
    private Registro registro;
    private Categoria cat;
    private boolean isNew;
    public EditarCategoriasControler(AppCompatActivity act) {
        super(act);

        registro = new Registro(Categoria.class);

        int id = act.getIntent().getIntExtra("id",-1);

        txtTitulo = this.act.findViewById(R.id.txtTitulo);

        btnRegresar = this.act.findViewById(R.id.btnRegresar);
        btnGuardar = this.act.findViewById(R.id.btnGuardar);
        btnEliminar = this.act.findViewById(R.id.btnEliminar);


        setFunctions();

        if(id!=-1){
            cat = (Categoria) registro.search(id);
            fill();
            isNew =false;
            txtTitulo.setText(cat.getTitulo());
        }else{
            cat = new Categoria();
            txtTitulo.setHint("Personalizado");
            isNew = true;
        }

    }

    private void fill() {
        txtTitulo.setHint(cat.getTitulo());
    }

    public void setFunctions(){
        btnRegresar.setOnClickListener(toReturn());
        btnGuardar.setOnClickListener(saveTitle());
        btnEliminar.setOnClickListener(eliminar());
    }

    public View.OnClickListener toReturn(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(act, Menu_act.class);
                act.startActivity(intent);
            }
        };
    }

    public View.OnClickListener saveTitle(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtTitulo.getText().toString().isEmpty()){
                    message("Debe de rellenar el campo de texto");
                } else {

                    if (!isNew){
                        Entidad old = registro.search("titulo",txtTitulo.getText().toString().trim());
                        if (old == null || old.getId() == cat.getId()) {
                            cat.setTitulo(txtTitulo.getText().toString().trim());

                            registro.update(cat);
                            Intent intent = new Intent(act, Menu_act.class);
                            act.startActivity(intent);
                        } else message("Este nombre ya existe");

                    }else {
                        cat.setTitulo(txtTitulo.getText().toString().trim());

                        //Se comprueba que no repita un nombre ya existente
                        Entidad old = registro.search("titulo",cat.getTitulo());
                        if (old == null){
                            registro.add(cat);
                            Intent intent = new Intent(act, Menu_act.class);
                            act.startActivity(intent);
                        } else message("Este nombre ya existe");

                    }



                }
            }
        };
    }



    public View.OnClickListener eliminar(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registro.delete(cat);
                Intent intent = new Intent(act, Menu_act.class);
                act.startActivity(intent);
            }
        };
    }

    public void message(String text){
        Toast.makeText(act, text, Toast.LENGTH_SHORT).show();
    }
}
