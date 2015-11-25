package com.izv.dam.sqliteproyecto1.adaptador;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.izv.dam.sqliteproyecto1.R;
import com.izv.dam.sqliteproyecto1.ingrediente.Ingrediente;
import com.izv.dam.sqliteproyecto1.receta.Receta;

import java.io.File;

public class AdaptadorIngredientes extends CursorAdapter{

    public AdaptadorIngredientes(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.adaptador_ingredientes, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv1 = (TextView)view.findViewById(R.id.tvNombreIngrediente);

        Ingrediente i = new Ingrediente();
        i.set(cursor);
        tv1.setText(i.getNombre());
    }
}
