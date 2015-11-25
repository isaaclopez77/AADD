package com.izv.dam.sqliteproyecto1.ingrediente;

import android.database.Cursor;

import com.izv.dam.sqliteproyecto1.basedatos.Contrato;

import java.io.Serializable;

public class Ingrediente implements Serializable {

    private long id;
    private String nombre;

    public Ingrediente(long id, String nombre){
        this.id=id;
        this.nombre=nombre;
    }

    public Ingrediente(){
        this(0,"");
    }

    public void set(Cursor c){
        setId(c.getLong(c.getColumnIndex(Contrato.TablaIngredientes._ID)));
        setNombre(c.getString(c.getColumnIndex(Contrato.TablaIngredientes.NOMBRE)));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingrediente that = (Ingrediente) o;

        return !(nombre != null ? !nombre.equals(that.nombre) : that.nombre != null);

    }

    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Ingrediente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
