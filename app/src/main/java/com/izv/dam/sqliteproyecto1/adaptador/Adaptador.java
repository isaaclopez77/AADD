package com.izv.dam.sqliteproyecto1.adaptador;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.izv.dam.sqliteproyecto1.R;
import com.izv.dam.sqliteproyecto1.receta.Receta;

import java.io.File;

public class Adaptador extends CursorAdapter{

    public Adaptador (Context co, Cursor cu) {
        super(co, cu, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.item, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv1 = (TextView)view.findViewById(R.id.tvParte1);
        TextView tv2 = (TextView)view.findViewById(R.id.tvParte2);
        ImageView img = (ImageView)view.findViewById(R.id.imgReceta);

        Receta p = new Receta();
        p.set(cursor);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + p.getFoto() + ".jpg");
        Uri uri = Uri.fromFile(file);
        img.setImageURI(uri);
        tv1.setText(p.getNombre());
        tv2.setText(p.getInstrucciones());
    }
}
