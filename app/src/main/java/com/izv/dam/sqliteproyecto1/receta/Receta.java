package com.izv.dam.sqliteproyecto1.receta;

import android.database.Cursor;

import com.izv.dam.sqliteproyecto1.basedatos.Contrato;

import java.io.Serializable;

public class Receta implements Serializable {

    private long id;
    private String nombre;
    private String instrucciones;
    private String foto;

    public Receta(long id, String nombre, String instrucciones, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.instrucciones = instrucciones;
    }

    public Receta() {
        this(0, "", "", "");
    }

    public void set(Cursor c){
        setId(c.getLong(c.getColumnIndex(Contrato.TablaReceta._ID)));
        setNombre(c.getString(c.getColumnIndex(Contrato.TablaReceta.NOMBRE)));
        setInstrucciones(c.getString(c.getColumnIndex(Contrato.TablaReceta.INSTRUCCIONES)));
        setFoto(c.getString(c.getColumnIndex(Contrato.TablaReceta.FOTO)));
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getInstrucciones() {
        return instrucciones;

    }

    @Override
    public String toString() {
        return "Receta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", instrucciones='" + instrucciones + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }
}