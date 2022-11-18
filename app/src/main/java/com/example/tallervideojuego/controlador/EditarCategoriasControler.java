package com.example.tallervideojuego.controlador;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.base.Controlador;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.modelo.registro.RegistroCat_Car;

public class EditarCategoriasControler extends Controlador {

    /**
     * La clase controlador para el activity editar_categoria
     */

    private EditText txtTitulo;
    private Button btnGuardar, btnCancelar;
    private Registro registro;
    private RegistroCat_Car registroRelacion;
    private Categoria cat;
    private boolean isNew;

    /**
     * Constructor de la clase
     * @param act La referencia del activity donde se inicializa el controlador
     */
    public EditarCategoriasControler(AppCompatActivity act) {
        super(act);

        registro = new Registro(Categoria.class);
        registroRelacion = new RegistroCat_Car();

        int id = act.getIntent().getIntExtra("id",-1);

        txtTitulo = this.act.findViewById(R.id.txtTitulo);

        btnGuardar = this.act.findViewById(R.id.btnGuardar);
        btnCancelar = this.act.findViewById(R.id.btnCancelar);


        setFunctions();


        //Al ser una pantalla en la cual se pueden tanto crear nuevas categorias como editar categorias existentes,se necesita comprobar si es nueva o no
        //Esto se maneja aquí, si es nueva se crea una nueva referencia, y si no, se busca en la base de datos
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

    /**
     * Este método rellena los campos en caso de que la categoria NO sea nueva y al contrario, se va a editar
     */
    private void fill() {
        txtTitulo.setHint(cat.getTitulo());
    }

    /**
     * Este método funciona para asignar los View.OnClickListener a los botones o elementos necesarios
     */
    public void setFunctions(){
        btnGuardar.setOnClickListener(saveTitle());
        btnCancelar.setOnClickListener(cancel());
    }

    /**
     * MÉTODO para la funcion de regresar (regresa a la pantalla anterior)
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener cancel(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresar();
                //Intent intent = new Intent(act, Menu_act.class);
                //act.startActivity(intent);
            }
        };
    }

    /**
     * MÉTODO para la funcion de guardar los datos creados (Si es nueva) o los cambios realizados (Si se esta editando una ya existente)
     * @return Retorna el View.OnClickListener
     */
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
//                            Intent intent = new Intent(act, MenuCategorias_act.class);
//                            act.startActivity(intent);
                            regresar();
                        } else message("Este nombre ya existe");

                    }else {
                        cat.setTitulo(txtTitulo.getText().toString().trim());

                        //Se comprueba que no repita un nombre ya existente
                        Entidad old = registro.search("titulo",cat.getTitulo());
                        if (old == null){
                            registro.add(cat);
//                            Intent intent = new Intent(act, MenuCategorias_act.class);
//                            act.startActivity(intent);
                            regresar();
                        } else message("Este nombre ya existe");

                    }



                }
            }
        };
    }


//    /**
//     * MÉTODO para la funcion de eliminar la categoria
//     * @return Retorna el View.OnClickListener
//     */
//    public View.OnClickListener eliminar(){
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isNew){
//                    registro.delete(cat);
//                    Intent intent = new Intent(act, MenuCategorias_act.class);
//                    act.startActivity(intent);
//                }else {
//                    cat.setRegistroCat_car(registroRelacion);
//                    ArrayList<Entidad> cartasRelacionadas = cat.getCartasDeCategoria();
//
//                    if(cartasRelacionadas.isEmpty()){
//                        registro.delete(cat);
//                        regresar();
//                        //Intent intent = new Intent(act, Menu_act.class);
//                        //act.startActivity(intent);
//                    } else {
//                        message("Esta categoría esta asignada, no se puede eliminar");
//                    }
//                }
//
//            }
//        };
//    }

}
