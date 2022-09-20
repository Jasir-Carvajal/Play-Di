package com.example.tallervideojuego.modelo.base;

import com.example.tallervideojuego.controlador.base.ErrorManager;

import java.util.ArrayList;

public class Registro {
    /**Registro de elementos**/
    protected ArrayList<Entidad> entidades;

    public Registro() {
        this.entidades = new ArrayList<Entidad>();
    }



    /**
     * @param target id de la entidad a buscar
     * @return Entidad|null retorna el elemento encontado o null en caso de no encontrar nada
     * **/
    public Entidad search(int target){
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
    public int getIndex(int id){
        Entidad entidad_ = search(id);
        if (entidades.contains(entidad_)) return entidades.indexOf(entidad_);
        ErrorManager.error(3);
        return -1;
    }

    /** Añade un elemento al registro solo
     *  si no exite ya un elemento igual;
     *
     * @param entidad entidad a ser guardada
     * @return boolean si fue exitoso o no;
     * **/
    public boolean add(Entidad entidad){
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
     * @param entidad entidad editada a ser registrada
     * @return boolean retorna true si se modifico y false sino;
     * **/
    public boolean modify(Entidad entidad){
       int index = getIndex(entidad.id);
       entidades.set(index,entidad);
       if (entidades.get(index).equals(entidad)) return true;
       ErrorManager.error(4);
       return false;
    }

    /** Elimina el elemento del registro
     * @param entidad elemento a eliminar
     * @return boolean retorna true si se elimino y false sino;
     * **/
    public boolean delete(Entidad entidad){
        entidades.remove(entidad);
        if (!entidades.contains(entidad))return true;
        ErrorManager.error(5);
        return false;
    }

    /**Getter del ArrayList**/
    public ArrayList<Entidad> getEntidades() {
        return entidades;
    }

    /**Setter del ArrayList**/
    public void setEntidades(ArrayList<Entidad> entidades) {
        this.entidades = entidades;
    }

    protected static Registro instancia;


}
