package com.example.tallervideojuego.controlador;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.bace.Controlador;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.modelo.registro.RegistroCategorias;
import com.example.tallervideojuego.vista.EditarCategoria_act;
import com.example.tallervideojuego.vista.Menu_act;

public class EditarCategoriasControler extends Controlador {

    private AppCompatActivity act;
    private EditText txtTitulo;
    private Button btnRegresar, btnGuardar;
    private RegistroCategorias registro;

    public EditarCategoriasControler(AppCompatActivity act) {
        super(act);
        this.act = act;

        txtTitulo = this.act.findViewById(R.id.txtTitulo);
        btnRegresar = this.act.findViewById(R.id.btnRegresar);
        btnGuardar = this.act.findViewById(R.id.btnGuardar);
        registro = RegistroCategorias.getInstance();

        System.out.println(btnRegresar.getText());

        setFunctions();
        fillSpace();
    }

    public void setFunctions(){
        btnRegresar.setOnClickListener(toReturn());
        btnGuardar.setOnClickListener(saveTitle());
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
                int id = act.getIntent().getIntExtra("id",0);

                Categoria cat = (Categoria) registro.search(id);

                if(txtTitulo.getText().toString().isEmpty()){
                    message("Debe de rellenar el campo de texto");
                } else {
                    cat.setTitulo(txtTitulo.getText().toString().trim());

                    Intent intent = new Intent(act, Menu_act.class);

                    act.startActivity(intent);

                }
            }
        };
    }

    public void fillSpace(){
        int id = act.getIntent().getIntExtra("id",0);

        Categoria cat = (Categoria) registro.search(id);

        txtTitulo.setText(cat.getTitulo());

    }

    public void message(String text){
        Toast.makeText(act, text, Toast.LENGTH_SHORT).show();
    }
}
