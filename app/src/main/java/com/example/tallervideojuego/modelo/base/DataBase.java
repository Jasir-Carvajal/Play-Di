package com.example.tallervideojuego.modelo.base;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**Creacion de la base de datos*/
public class DataBase  extends SQLiteOpenHelper{

    protected static  String BaseDatos = "Default";
    protected static Context context;

    public DataBase( @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, BaseDatos, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase baseDeDatos) {
        //Verificar que se permitan primari keys
        baseDeDatos.execSQL("PRAGMA foreign_keys = ON;");
        //crear tabla para objeto Carta
        baseDeDatos.execSQL("Create Table IF NOT EXISTS Cartas(id INTEGER primary key AUTOINCREMENT UNIQUE , titulo TEXT NOT NULL , reto TEXT NOT NULL, castigo TEXT NOT NULL )");
        //crear tabla para objeto Categoria
        baseDeDatos.execSQL("Create Table IF NOT EXISTS Categorias(id INTEGER primary key AUTOINCREMENT UNIQUE , titulo TEXT NOT NULL )");
        //crear tabla para relacion entre carta y categoria
        baseDeDatos.execSQL("create table Carta_Categoria   (id INTEGER primary key AUTOINCREMENT UNIQUE,  cartas_id  INTEGER  NOT NULL ,categoria_id  INTEGER  NOT NULL, FOREIGN KEY  (cartas_id) REFERENCES Cartas(id), FOREIGN KEY (categoria_id) REFERENCES Categorias(id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Ajustes basico para el funcionamiento de la Base de datos
    public static void setContext(Context context) {
        DataBase.context = context;
    }

    //Ajustes basico para el funcionamiento de la Base de datos
    public static void setName(String baseDatos) {
        BaseDatos = baseDatos;
    }
}


