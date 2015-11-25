package com.izv.dam.sqliteproyecto1.basedatos;

import android.os.Environment;
import android.provider.BaseColumns;

public class Contrato{

    private Contrato (){
    }

    public static abstract class TablaReceta implements BaseColumns{
        public static final String TABLA = "recetas";
        public static final String NOMBRE = "nombre";
        public static final String INSTRUCCIONES = "instrucciones";
        public static final String FOTO = "foto";
    }

    public static abstract class TablaIngredientes implements BaseColumns{
        public static final String TABLA = "ingredientes";
        public static final String NOMBRE = "nombre";
    }

    public static abstract class TablaRecetaIngredientes implements BaseColumns {
        public static final String TABLA = "recetaingredientes";
        public static final String ID_RECETA = "id_receta";
        public static final String ID_INGREDIENTE = "id_ingrediente";
        public static final String CANTIDAD = "cantidad";
    }
}