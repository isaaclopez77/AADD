package com.izv.dam.sqliteproyecto1.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Ayudante extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "noprueba2.sqlite";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(Context context) {
        super(context, context.getExternalFilesDir(null) + "/" + DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        Log.v("SQLAAD","on upgrade");
        String sql="drop table if exists "
                + Contrato.TablaReceta.TABLA;
        db.execSQL(sql);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql="create table "+Contrato.TablaReceta.TABLA+
                " ("+
                  Contrato.TablaReceta._ID + " integer primary key autoincrement, "+
                  Contrato.TablaReceta.NOMBRE+" text, "+
                  Contrato.TablaReceta.INSTRUCCIONES+" text, "+
                Contrato.TablaReceta.FOTO+" text "+
                ")";
        db.execSQL(sql);

        String sql2;
        sql2="create table "+Contrato.TablaIngredientes.TABLA+
                " ("+
                Contrato.TablaIngredientes._ID + " integer primary key autoincrement, "+
                Contrato.TablaIngredientes.NOMBRE+" text "+
                ")";
        db.execSQL(sql2);

        String sql3;
        sql3="create table "+Contrato.TablaRecetaIngredientes.TABLA+
                " ("+
                Contrato.TablaRecetaIngredientes._ID + " integer primary key autoincrement, "+
                Contrato.TablaRecetaIngredientes.ID_INGREDIENTE+" integer, "+
                Contrato.TablaRecetaIngredientes.ID_RECETA+ " integer, "+
                Contrato.TablaRecetaIngredientes.CANTIDAD+" integer "+
                ")";
        db.execSQL(sql3);
    }
}