package com.izv.dam.sqliteproyecto1.receta;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.izv.dam.sqliteproyecto1.R;
import com.izv.dam.sqliteproyecto1.basedatos.Contrato;
import com.izv.dam.sqliteproyecto1.ingrediente.GestorIngrediente;
import com.izv.dam.sqliteproyecto1.ingrediente.Ingrediente;
import com.izv.dam.sqliteproyecto1.ingrediente_receta.GestorIngredienteReceta;
import com.izv.dam.sqliteproyecto1.ingrediente_receta.IngredienteReceta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MostrarReceta extends AppCompatActivity{
    private Cursor c;
    private GestorIngredienteReceta gir;
    private GestorIngrediente gestorIngrediente;
    private Cursor cIngrediente;
    private TextView tvIng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_receta);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gir = new GestorIngredienteReceta(this);
        gir.open();
        c=gir.getCursor();

        gestorIngrediente = new GestorIngrediente(this);
        gestorIngrediente.open();
        cIngrediente = gestorIngrediente.getCursor();
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gir.close();
        gestorIngrediente.close();
    }

    private void init(){
        Receta p = (Receta)getIntent().getExtras().getSerializable("receta");

        ImageView img = (ImageView)findViewById(R.id.img);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/"+p.getFoto()+".jpg");
        Uri uri = Uri.fromFile(file);
        img.setImageURI(uri);

        TextView tvNombre = (TextView)findViewById(R.id.tvNombreRec);
        tvNombre.setText("Receta: "+p.getNombre());

        TextView tvInstruc = (TextView)findViewById(R.id.tvInstruccionesRec);
        tvInstruc.setText(p.getInstrucciones());

        tvIng = (TextView)findViewById(R.id.tvVerIng);

        List<IngredienteReceta> list = gir.select(Contrato.TablaRecetaIngredientes.ID_RECETA + " = ?", new String[]{p.getId() + ""});
        int cont=0;

        List<String> idIngredientes = new ArrayList<>();

        for(IngredienteReceta ir : list){
            //tvIng.append((cont+1)+": "+ ir.getId_ingrediente()+"\n");
            idIngredientes.add(ir.getId_ingrediente()+"");
            cont++;
        }
        String[] listIds = new String[idIngredientes.size()];
        cont=0;
        for (String id : idIngredientes){
            listIds[cont] = id;
            cont++;
        }

        List<Ingrediente> lIng = new ArrayList<>();

        if(listIds.length==1){
            lIng = gestorIngrediente.select(Contrato.TablaIngredientes._ID + " = ?",listIds);
            metodo(lIng);
        }else if(listIds.length==2){
            lIng = gestorIngrediente.select(Contrato.TablaIngredientes._ID + " IN ( ? , ? )",listIds);
            metodo(lIng);
        }else if(listIds.length==3){
            lIng = gestorIngrediente.select(Contrato.TablaIngredientes._ID + " IN ( ? , ? , ? )",listIds);
            metodo(lIng);
        }else if(listIds.length==4){
            lIng = gestorIngrediente.select(Contrato.TablaIngredientes._ID + " IN ( ? , ? , ? , ? )",listIds);
            metodo(lIng);
        }else{
            tvIng.setText("SIN INGREDIENTES");
        }
        /*cont=0;
        for(Ingrediente ing : lIng){
            tvIng.append((cont + 1) + ": " + ing.getNombre() + "\n");
            cont++;
        }*/

    }

    public void metodo(List<Ingrediente> list){
         int cont=0;
        for(Ingrediente ing : list){
            tvIng.append((cont + 1) + ": " + ing.getNombre() + "\n");
            cont++;
        }
    }
}
