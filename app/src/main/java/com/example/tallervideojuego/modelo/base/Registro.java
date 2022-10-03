package com.example.tallervideojuego.modelo.base;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.tallervideojuego.modelo.entidades.Categoria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Registro {


    protected ArrayList<Entidad> listaEntidades;
    protected SQLiteDatabase DB;
    protected String tabla;
    protected ArrayList<String> columnas;
    protected Class hijo;

    public Registro(Class Hijo_) {
        try {
            this.hijo = Hijo_;
            this.tabla = (String) Hijo_.getDeclaredField("Tabla").get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        listaEntidades = new ArrayList<Entidad>();
        this.DB = (new DataBase( null, 1)).       getWritableDatabase();
        loadDb();
        Cursor cursor = DB.rawQuery ("SELECT * FROM "+tabla+";" ,null);
        columnas = new ArrayList<String>( (List<String>)Arrays.asList(cursor.getColumnNames()) );
    }
    public Registro(String tabla) {

        this.tabla = tabla;
        listaEntidades = new ArrayList<Entidad>();
        this.DB = (new DataBase( null, 1)).getWritableDatabase();
        loadDb();
        Cursor cursor = DB.rawQuery ("SELECT * FROM "+tabla+";" ,null);
        columnas = new ArrayList<String>( (List<String>)Arrays.asList(cursor.getColumnNames()) );
    }

    public boolean add( Entidad entidad){
        listaEntidades.add(entidad);
        boolean res = this.DB.insert(tabla,null,entidad.getContent())>0;
        loadDb();
        return res;

    }

    public Entidad search(String columna, Object value){
        if (columnas.contains(columna)){
            for (Entidad entidad:listaEntidades) {
                if (entidad.getContent().get(columna).equals(value)){
                    return entidad;
                }
            }
        }
        return null;
    }

    public void delete(Entidad entidad){
        listaEntidades.remove(entidad);
        this.DB.delete(tabla,"id="+entidad.getId(),null);
    }

    public void update(Entidad entidad){

        listaEntidades.set(getIndex(entidad.getId()),entidad);
        this.DB.update(tabla,entidad.getContent(),"id="+entidad.getId(),null);
    }

    private int getIndex(int id) {
        return  listaEntidades.indexOf(search(id));
    }

    public ArrayList<Entidad> getEntidades() {
        return listaEntidades;
    }

    public Entidad search(int id) {
        for (Entidad entidad: listaEntidades) {
            if (entidad.getId()==id)return entidad;
        }
        return null;
    }

    public Entidad search_posicion(int i) {
        return listaEntidades.get(i);
    }

    public void loadDb (){
        Cursor cursor = DB.rawQuery ("SELECT * FROM "+tabla+";" ,null);
        cursor.moveToFirst();
        listaEntidades = new ArrayList<Entidad>();

        while(!cursor.isAfterLast()){
            ContentValues data = new ContentValues();
            Object entidad = new Entidad();
            try {
                if (hijo!=null){
                    entidad = hijo.newInstance();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

            for (String columna:cursor.getColumnNames()) {
                int index = cursor.getColumnIndex(columna);
                int type = cursor.getType(index);
                if (!columna.equalsIgnoreCase("id")){
                    switch (type){
                        case Cursor.FIELD_TYPE_INTEGER:
                            int INTEGER  = cursor.getInt(index);
                            data.put(columna,INTEGER);
                            break;
                        case  Cursor.FIELD_TYPE_BLOB:
                            byte[] BLOB  = cursor.getBlob(index);
                            data.put(columna,BLOB);
                            break;
                        case Cursor.FIELD_TYPE_FLOAT:
                            float FLOAT  = cursor.getFloat(index);
                            data.put(columna,FLOAT);
                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            String string  = cursor.getString(index);
                            data.put(columna,string);
                            break;
                        default:
                            break;
                    }
                }else {
                    ((Entidad)entidad).setId(cursor.getInt(index));
                }
            }

            ((Entidad)entidad).setContenido(data);
            listaEntidades.add((Entidad) entidad);
            cursor.moveToNext();
        }
        cursor.close();
    }



}
