package com.izv.dam.sqliteproyecto1.ingrediente_receta;


import android.database.Cursor;

import com.izv.dam.sqliteproyecto1.basedatos.Contrato;

import java.io.Serializable;

public class IngredienteReceta implements Serializable{

    private long id;
    private int id_ingrediente;
    private int id_receta;
    private int cantidad;

    public IngredienteReceta(long id, int id_ing, int id_rec, int cant){
        this.id=id;
        this.id_ingrediente=id_ing;
        this.id_receta=id_rec;
        this.cantidad=cant;

    }

    public IngredienteReceta(){
        this(0, 0, 0, 0);
    }

    public void set(Cursor c){
        setId(c.getLong(c.getColumnIndex(Contrato.TablaRecetaIngredientes._ID)));
        setId_ingrediente(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngredientes.ID_INGREDIENTE)));
        setId_receta(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngredientes.ID_RECETA)));
        setCantidad(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngredientes.CANTIDAD)));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getId_ingrediente() {
        return id_ingrediente;
    }

    public void setId_ingrediente(int id_ingrediente) {
        this.id_ingrediente = id_ingrediente;
    }

    public int getId_receta() {
        return id_receta;
    }

    public void setId_receta(int id_receta) {
        this.id_receta = id_receta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "IngredienteReceta{" +
                "id=" + id +
                ", id_ingrediente=" + id_ingrediente +
                ", id_receta=" + id_receta +
                ", cantidad=" + cantidad +
                '}';
    }
}
