package com.izv.dam.sqliteproyecto1.receta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.izv.dam.sqliteproyecto1.basedatos.Ayudante;
import com.izv.dam.sqliteproyecto1.basedatos.Contrato;
import java.util.ArrayList;
import java.util.List;

public class GestorReceta {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorReceta(Context c){
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

    public long insert(Receta p) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaReceta.NOMBRE, p.getNombre());
        valores.put(Contrato.TablaReceta.INSTRUCCIONES, p.getInstrucciones());
        valores.put(Contrato.TablaReceta.FOTO,p.getFoto());
        long id = bd.insert(Contrato.TablaReceta.TABLA, null, valores);
        return id;
    }

    public int delete(Receta p) {
        return delete(p.getId());
    }

    public int delete(long id){
        String condicion = Contrato.TablaReceta._ID + " = ?";
        String[] argumentos = { id + "" };
        int cuenta = bd.delete(
                Contrato.TablaReceta.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(Receta p){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaReceta.NOMBRE, p.getNombre());
        valores.put(Contrato.TablaReceta.INSTRUCCIONES, p.getInstrucciones());
        String condicion = Contrato.TablaReceta._ID + " = ?";
        String[] argumentos = { p.getId() + "" };
        int cuenta = bd.update(Contrato.TablaReceta.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public Receta getRow(Cursor c) {
        Receta p = new Receta();
        p.setId(c.getLong(c.getColumnIndex(Contrato.TablaReceta._ID)));
        p.setNombre(c.getString(c.getColumnIndex(Contrato.TablaReceta.NOMBRE)));
        p.setInstrucciones(c.getString(c.getColumnIndex(Contrato.TablaReceta.INSTRUCCIONES)));
        p.setFoto(c.getString(c.getColumnIndex(Contrato.TablaReceta.FOTO)));
        return p;
    }

    public Receta getRow(long id) {
        Cursor c = getCursor("_id = ?", new String[]{id+""});
        return getRow(c);
    }

    public Cursor getCursor(){
        return getCursor(null, null);
    }

    public Cursor getCursor(String condicion, String[] parametros) {
        Cursor cursor = bd.query(
                Contrato.TablaReceta.TABLA, null, condicion, parametros, null,
                null, Contrato.TablaReceta.NOMBRE+", "+Contrato.TablaReceta.INSTRUCCIONES+", "+Contrato.TablaReceta.FOTO);
        return cursor;
    }

    public List<Receta> select(String condicion, String[] parametros) {
        List<Receta> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        Receta p;
        while (cursor.moveToNext()) {
            p = getRow(cursor);
            la.add(p);
        }
        cursor.close();
        return la;
    }

    public List<Receta> select() {
        return select(null,null);
    }
}