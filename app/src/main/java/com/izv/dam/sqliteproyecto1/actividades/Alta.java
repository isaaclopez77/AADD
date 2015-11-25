package com.izv.dam.sqliteproyecto1.actividades;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.izv.dam.sqliteproyecto1.R;
import com.izv.dam.sqliteproyecto1.adaptador.AdaptadorIngredientes;
import com.izv.dam.sqliteproyecto1.basedatos.Contrato;
import com.izv.dam.sqliteproyecto1.ingrediente.GestorIngrediente;
import com.izv.dam.sqliteproyecto1.ingrediente.Ingrediente;
import com.izv.dam.sqliteproyecto1.ingrediente_receta.GestorIngredienteReceta;
import com.izv.dam.sqliteproyecto1.ingrediente_receta.IngredienteReceta;
import com.izv.dam.sqliteproyecto1.receta.GestorReceta;
import com.izv.dam.sqliteproyecto1.receta.Receta;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Alta extends AppCompatActivity {

    private android.widget.TextView tvTexto;
    private android.widget.EditText etNombre;
    private android.widget.EditText etInstrucciones;
    private EditText etId;
    private EditText foto;

    private GestorReceta gestorReceta;
    private GestorIngrediente gestorIngrediente;
    private GestorIngredienteReceta gestorIngRec;
    private long r;
    private Cursor c;
    private Ingrediente ingredienteSeleccionado;
    private android.widget.Button btAdd;
    private Intent iFoto;
    private Receta p;
    private List<Ingrediente> listaIngredientes;
    private List<IngredienteReceta> lir;
    private ListView lvIngredientes;
    private int cont=0;
    private IngredienteReceta ir;
    private AdaptadorIngredientes ad;
    private static final int IDACTIVIDADFOTO=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta);
        this.foto=(EditText)findViewById(R.id.etFoto);
        Log.v("ESTADO","oncreate");
    }

    private void init(){
        this.btAdd = (Button) findViewById(R.id.btAdd);
        this.etNombre = (EditText) findViewById(R.id.etNombre);
        this.etInstrucciones = (EditText) findViewById(R.id.etInstrucciones);

        Log.v("ESTADO","init");
        lvIngredientes = (ListView)findViewById(R.id.lvIngredientes);
        ad = new AdaptadorIngredientes(this,c);
        lvIngredientes.setAdapter(ad);
        lir = new ArrayList<>();
        lvIngredientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ingredienteSeleccionado = new Ingrediente();
                ingredienteSeleccionado.setNombre(c.getString(c.getColumnIndex(Contrato.TablaIngredientes.NOMBRE)));
                ingredienteSeleccionado.setId(c.getLong(c.getColumnIndex(Contrato.TablaIngredientes._ID)));
                tostada("" + ingredienteSeleccionado.toString());
                ir = new IngredienteReceta();
                ir.setId_ingrediente((int) ingredienteSeleccionado.getId());
                lir.add(ir);

            }
        });
        ad.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK && requestCode==IDACTIVIDADFOTO){
            Log.v("ESTADO","onActivityResult");
            Bundle b =data.getExtras();
            Bitmap picture = (Bitmap)b.get("data");
            FileOutputStream salida;
            try {
                Log.v("NOMBREFOTO_ACTRESULT",foto.getText().toString());
                salida = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + foto.getText().toString() + ".jpg");
                picture.compress(Bitmap.CompressFormat.JPEG, 90, salida);
            } catch (FileNotFoundException e) {
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gestorIngrediente = new GestorIngrediente(this);
        gestorIngRec = new GestorIngredienteReceta(this);
        gestorReceta = new GestorReceta(this);
        gestorReceta.open();
        gestorIngRec.open();
        gestorIngrediente.open();
        c = gestorIngrediente.getCursor();
        Log.v("ESTADO","onResume");
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gestorReceta.close();
        gestorIngrediente.close();
        gestorIngRec.close();
    }

    public void add(View v){
        p = new Receta();
        p.setNombre(etNombre.getText().toString().trim());
        p.setInstrucciones(etInstrucciones.getText().toString().trim());
        p.setFoto(foto.getText().toString().trim());
        if(!p.getNombre().isEmpty() || p.getFoto().isEmpty()) {
            r = gestorReceta.insert(p);
            if (r>0) {
                Intent i = new Intent();
                Bundle bundle = new Bundle();
                bundle.putLong("id", r);
                Log.v("ID_RECETA",r+"");
                i.putExtras(bundle);
                setResult(Activity.RESULT_OK, i);
                finish();
            }else {
                tostada("No se ha podido insertar");
            }
        } else{
            tostada("El nombre es obligatorio");
        }

        for(IngredienteReceta ing : lir){
            ing.setId_receta((int) r);
            Log.v("INGREDIENTERECETA", ing.toString());
            long x = gestorIngRec.insert(ing);
            if(x>0){
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putLong("id",x);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

    private void tostada(int i){
        tostada(i + "");
    }
    private void tostada(String i){
        Toast.makeText(this,i,Toast.LENGTH_LONG).show();
    }


    private void view(){
        List<Receta> l = gestorReceta.select();
        tvTexto.setText("");
        for(Receta p: l){
            tvTexto.append(p.toString());
        }
    }

    public void insertarFoto(View v){
        Log.v("ESTADO","insertarFoto");
        String nombrefoto = foto.getText().toString();
        Log.v("NOMBREFOTO",nombrefoto);
        if(nombrefoto.matches("")){
            tostada("Inserte titulo a la foto");
        }else {
            iFoto = new Intent ("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(iFoto, IDACTIVIDADFOTO);
        }
    }

    public void verIngredientes(View v){
        cont++;
        Log.v("CONTADOR",cont+"");
        Log.v("CONTADOR VIS",""+lvIngredientes.getVisibility());
        if(cont%2==0)
            lvIngredientes.setVisibility(View.VISIBLE);
        else
            lvIngredientes.setVisibility(View.GONE);
    }

    public void borrar(View v){
        int id = Integer.parseInt(etId.getText().toString());
        int r = gestorReceta.delete(id);
        tostada(r);
        view();
    }

    public void editar(View v){
        Receta p = new Receta();
        p.setNombre(etNombre.getText().toString());
        p.setInstrucciones(etInstrucciones.getText().toString());
        int id = Integer.parseInt(etId.getText().toString());
        p.setId(id);
        int r = gestorReceta.update(p);
        tostada(r);
        view();
    }
}