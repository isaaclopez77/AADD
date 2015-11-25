package com.izv.dam.sqliteproyecto1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.izv.dam.sqliteproyecto1.actividades.Alta;
import com.izv.dam.sqliteproyecto1.actividades.AltaIngrediente;
import com.izv.dam.sqliteproyecto1.adaptador.Adaptador;
import com.izv.dam.sqliteproyecto1.ingrediente_receta.GestorIngredienteReceta;
import com.izv.dam.sqliteproyecto1.ingrediente_receta.IngredienteReceta;
import com.izv.dam.sqliteproyecto1.receta.GestorReceta;
import com.izv.dam.sqliteproyecto1.receta.MostrarReceta;
import com.izv.dam.sqliteproyecto1.receta.Receta;
import com.izv.dam.sqliteproyecto1.util.Dialogo;
import com.izv.dam.sqliteproyecto1.util.OnDialogoListener;

public class Principal extends AppCompatActivity {

    private GestorReceta gp;
    private Adaptador adp;
    private ListView lv;
    private Cursor c;
    private GestorIngredienteReceta gir;
    private Cursor cir;
    private final static int ALTA=1;

    /********** @OVERRIDES ***************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.mnAñadirIngrediente:
                Intent i = new Intent(this, AltaIngrediente.class);
                startActivity(i);
                break;
            case R.id.mnAcercaDe:
                tostada("Proyecto BD SQLite Recetario \n Isaac López Delgado 2ºDAM");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pulsado=item.getItemId();
        switch (pulsado){
            case R.id.mnEditar:
                final Receta p = new Receta();
                p.set(c);//
                final OnDialogoListener odl = new OnDialogoListener() {
                    @Override
                    public void onPreShow(View v) {
                        Button b = (Button) v.findViewById(R.id.btAdd);
                        b.setVisibility(View.GONE);
                        EditText etTitulo = (EditText) v.findViewById(R.id.etNombre);
                        etTitulo.setText(p.getNombre());
                        EditText etDirector = (EditText) v.findViewById(R.id.etInstrucciones);
                        etDirector.setText(p.getInstrucciones());
                        Button btFoto = (Button) v.findViewById(R.id.button);
                        btFoto.setVisibility(View.GONE);
                        EditText nombreFoto = (EditText) v.findViewById(R.id.etFoto);
                        nombreFoto.setVisibility(View.GONE);
                        Button btnIngredientes = (Button)v.findViewById(R.id.button2);
                        btnIngredientes.setVisibility(View.GONE);
                    }

                    @Override
                    public void onOkSelecter(View v) {
                        tostada("Receta insertada");
                        EditText etTitulo = (EditText) v.findViewById(R.id.etNombre);
                        EditText etDirector = (EditText) v.findViewById(R.id.etInstrucciones);
                        p.setNombre(etTitulo.getText().toString());
                        p.setInstrucciones(etDirector.getText().toString());
                        int r = gp.update(p);
                        //Actualizar cursor
                        c = gp.getCursor();
                        adp.changeCursor(c);
                    }

                    @Override
                    public void onCancelSelecter(View v) {
                        tostada("Cancelado");
                    }
                };
                tostada(p.toString());
                Dialogo d = new Dialogo(Principal.this, R.layout.alta, odl);
                d.show();
                break;
            case R.id.mnBorrar:
                //
                break;
        }
        adp.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        gp = new GestorReceta(this);
        gp.open();
        c = gp.getCursor();

        gir = new GestorIngredienteReceta(this);
        gir.open();
        cir = gir.getCursor();

        adp = new Adaptador(this, c);
        lv = (ListView)findViewById(R.id.lv);
        lv.setAdapter(adp);
        init();
        super.onResume();
    }

    @Override
    protected void onPause() {
        gp.close();
        gir.close();
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case ALTA:
                    //Actualizar cursor
                    //c=gp.getCursor();
                    //adp.changeCursor(c);
                    //tostada("Insertado correctamente");
                    break;
            }
        }
    }

    /************ MÉTODOS ********************************/

    private void tostada(int i){
        tostada(i + "");
    }

    private void tostada(String i){
        Toast.makeText(this, i, Toast.LENGTH_LONG).show();
    }

    public void insertar(View v){
        Intent i=new Intent(this,Alta.class);
        startActivityForResult(i, ALTA);
    }

    private void init(){

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Receta p = new Receta();
                p.set(c);
;
                Intent i = new Intent(Principal.this, MostrarReceta.class);
                i.putExtra("receta",p);
                startActivity(i);
            }
        });
        registerForContextMenu(lv);
    }
}