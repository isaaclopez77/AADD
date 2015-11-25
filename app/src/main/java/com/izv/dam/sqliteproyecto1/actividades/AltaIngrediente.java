package com.izv.dam.sqliteproyecto1.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.izv.dam.sqliteproyecto1.R;
import com.izv.dam.sqliteproyecto1.basedatos.Contrato;
import com.izv.dam.sqliteproyecto1.ingrediente.GestorIngrediente;
import com.izv.dam.sqliteproyecto1.ingrediente.Ingrediente;

public class AltaIngrediente extends AppCompatActivity {

    private GestorIngrediente gestor;
    private EditText et;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta_ingrediente);
        init();
    }

    public void init() {
        et = (EditText) findViewById(R.id.etIngrediente);
        btn = (Button) findViewById(R.id.btnAgregarIn);

        gestor = new GestorIngrediente(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gestor.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gestor.close();
    }

    public void agregarIngrediente(View v) {
        Ingrediente i = new Ingrediente();

        i.setNombre(et.getText().toString().trim());
        if (!i.getNombre().isEmpty()) {
            long r = gestor.insert(i);
            if (r > 0) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putLong("id", r);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            } else {
                tostada("No se ha podido insertar");
            }
        } else {
            tostada("El nombre es obligatorio");
        }
        Toast.makeText(this,i.toString()+"\n"+ Contrato.TablaIngredientes.NOMBRE,Toast.LENGTH_SHORT).show();


    }

    public void tostada(String t){
        Toast.makeText(this,t,Toast.LENGTH_SHORT).show();
    }
}
