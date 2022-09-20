package com.example.tallervideojuego.modelo.registro;

import com.example.tallervideojuego.modelo.base.Registro;

public class RegistroCategorias extends Registro {


    public static RegistroCategorias getInstance(){
        if (instancia==null){
            instancia = new RegistroCategorias();
        }
        return (RegistroCategorias) instancia;
    }
}
