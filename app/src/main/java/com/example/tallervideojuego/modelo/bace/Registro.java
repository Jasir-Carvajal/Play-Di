package com.example.tallervideojuego.modelo.bace;

import com.example.tallervideojuego.controlador.bace.ErrorManager;

import java.util.ArrayList;

public class Registro {
    /**Registro de elementos**/
    protected ArrayList<Entidad> entidades;

    /**
     * @param target id de la entidad a bsucar
     * @return Entidad|null retorna el elemento encontado o null en caso de no encontrar nada
     * **/
    protected Entidad search(int target){
        for (Entidad entidad:entidades) {
            if (entidad.id== target){
                return entidad;
            }
        }
        ErrorManager.error(3);//no encontrado
        return null;
    }

    /**retorna la posicion del index
     * @param id id de la entidad de la que se busca la posicion
     * **/
    protected int getIndex(int id){
        Entidad entidad_ = search(id);
        if (entidades.contains(entidad_)) return entidades.indexOf(entidad_);
        ErrorManager.error(3);
        return -1;
    }

    /** Añade un elemento al registro solo
     *  si no exite ya un elemento igual;
     *
     * @param entidad entidad a ser guardada
     * @return bolean si fue exitoso o no;
     * **/
    protected boolean add(Entidad entidad){
        if (entidades.contains(entidad)){
            return ErrorManager.error(1);
        }
        entidades.add(entidad);
        if (entidades.contains(entidad)){
            return true;
        }
        return ErrorManager.error(2);
    }

    /** Modifica un elemento del Registro
     * @param entidad enrtidad editada a ser registrada
     * @return boolean retorna true si se modifico y false sino;
     * **/
    protected boolean modify(Entidad entidad){
       int index = getIndex(entidad.id);
       entidades.set(index,entidad);
       if (entidades.get(index).equals(entidad)) return true;
       ErrorManager.error(4);
       return false;
    }

    /** Elimina el elemetno del registro
     * @param entidad elemento a eliminar
     * @return boolean retorna true si se elimino y false sino;
     * **/
    protected boolean delete(Entidad entidad){
        entidades.remove(entidad);
        if (!entidades.contains(entidad))return true;
        ErrorManager.error(5);
        return false;
    }



    protected static Registro instancia;

    protected static Registro getInstance(){
        if (instancia==null){
            instancia = new Registro();
        }
        return instancia;
    }

}
