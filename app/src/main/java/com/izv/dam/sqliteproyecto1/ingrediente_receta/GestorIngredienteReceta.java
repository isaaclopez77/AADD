package com.izv.dam.sqliteproyecto1.ingrediente_receta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.izv.dam.sqliteproyecto1.basedatos.Ayudante;
import com.izv.dam.sqliteproyecto1.basedatos.Contrato;
import com.izv.dam.sqliteproyecto1.receta.Receta;

import java.util.ArrayList;
import java.util.List;

public class GestorIngredienteReceta {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorIngredienteReceta(Context c){
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

    public long insert(IngredienteReceta ir) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaRecetaIngredientes.ID_INGREDIENTE, ir.getId_ingrediente());
        valores.put(Contrato.TablaRecetaIngredientes.ID_RECETA, ir.getId_receta());
        valores.put(Contrato.TablaRecetaIngredientes.CANTIDAD,ir.getCantidad());
        long id = bd.insert(Contrato.TablaRecetaIngredientes.TABLA, null, valores);
        return id;
    }

    public int delete(IngredienteReceta p) {
        return delete(p.getId());
    }

    public int delete(long id){
        String condicion = Contrato.TablaRecetaIngredientes._ID + " = ?";
        String[] argumentos = { id + "" };
        int cuenta = bd.delete(
                Contrato.TablaRecetaIngredientes.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(IngredienteReceta p){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaRecetaIngredientes.ID_INGREDIENTE, p.getId_ingrediente());
        valores.put(Contrato.TablaRecetaIngredientes.ID_RECETA, p.getId_receta());
        String condicion = Contrato.TablaRecetaIngredientes._ID + " = ?";
        String[] argumentos = { p.getId() + "" };
        int cuenta = bd.update(Contrato.TablaRecetaIngredientes.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public IngredienteReceta getRow(Cursor c) {
        IngredienteReceta p = new IngredienteReceta();
        p.setId(c.getLong(c.getColumnIndex(Contrato.TablaRecetaIngredientes._ID)));
        p.setId_ingrediente(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngredientes.ID_INGREDIENTE)));
        p.setId_receta(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngredientes.ID_RECETA)));
        p.setCantidad(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngredientes.CANTIDAD)));
        return p;
    }

    public IngredienteReceta getRow(long id) {
        Cursor c = getCursor("_id = ?", new String[]{id+""});
        return getRow(c);
    }

    public Cursor getCursor(){
        return getCursor(null, null);
    }

    public Cursor getCursor(String condicion, String[] parametros) {
        Cursor cursor = bd.query(
                Contrato.TablaRecetaIngredientes.TABLA, null, condicion, parametros, null, null,
                Contrato.TablaRecetaIngredientes.ID_INGREDIENTE+", "+Contrato.TablaRecetaIngredientes.ID_RECETA+", "+Contrato.TablaRecetaIngredientes.CANTIDAD);
        return cursor;
    }

    public List<IngredienteReceta> select(String condicion, String[] parametros) {
        List<IngredienteReceta> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        IngredienteReceta p;
        while (cursor.moveToNext()) {
            p = getRow(cursor);
            la.add(p);
        }
        cursor.close();
        return la;
    }

    public List<IngredienteReceta> select() {
        return select(null,null);
    }
}
