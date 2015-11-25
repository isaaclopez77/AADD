package com.izv.dam.sqliteproyecto1.ingrediente;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.izv.dam.sqliteproyecto1.basedatos.Ayudante;
import com.izv.dam.sqliteproyecto1.basedatos.Contrato;
import com.izv.dam.sqliteproyecto1.receta.Receta;

import java.util.ArrayList;
import java.util.List;

public class GestorIngrediente {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorIngrediente(Context c){
        abd = new Ayudante(c);
    }
    public void open() {
        bd = abd.getWritableDatabase();
    }
    public void openRead() {
        bd = abd.getReadableDatabase();
    }
    public void close() {
        abd.close();
    }

    public long insert(Ingrediente p) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaIngredientes.NOMBRE, p.getNombre());
        long id = bd.insert(Contrato.TablaIngredientes.TABLA, null, valores);
        return id;
    }

    public int delete(Ingrediente p) {
        return delete(p.getId());
    }

    public int delete(long id){
        String condicion = Contrato.TablaIngredientes._ID + " = ?";
        String[] argumentos = { id + "" };
        int cuenta = bd.delete(
                Contrato.TablaIngredientes.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(Ingrediente p){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaIngredientes.NOMBRE, p.getNombre());
        String condicion = Contrato.TablaIngredientes._ID + " = ?";
        String[] argumentos = { p.getId() + "" };
        int cuenta = bd.update(Contrato.TablaIngredientes.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public Ingrediente getRow(Cursor c) {
        Ingrediente p = new Ingrediente();
        p.setId(c.getLong(c.getColumnIndex(Contrato.TablaIngredientes._ID)));
        p.setNombre(c.getString(c.getColumnIndex(Contrato.TablaIngredientes.NOMBRE)));
        return p;
    }

    public Ingrediente getRow(long id) {
        Cursor c = getCursor("_id = ?", new String[]{id+""});
        return getRow(c);
    }

    public Cursor getCursor(){
        return getCursor(null, null);
    }

    public Cursor getCursor(String condicion, String[] parametros) {
        Cursor cursor = bd.query(
                Contrato.TablaIngredientes.TABLA, null, condicion, parametros, null,
                null, Contrato.TablaIngredientes.NOMBRE);
        return cursor;
    }

    public List<Ingrediente> select(String condicion, String[] parametros) {
        List<Ingrediente> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        Ingrediente p;
        while (cursor.moveToNext()) {
            p = getRow(cursor);
            la.add(p);
        }
        cursor.close();
        return la;
    }

    public List<Ingrediente> select() {
        return select(null,null);
    }

}
