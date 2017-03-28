package com.example.sads.mielera;

import android.content.ContentUris;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddProducts extends AppCompatActivity {
Button BtnAgregar, BtnCancelar;
    EditText Size, Price;
    Context con;
    URL url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        BtnAgregar  = (Button) findViewById(R.id.BtnAddProducts);
        Size  = (EditText) findViewById(R.id.Size);
        Price  = (EditText) findViewById(R.id.Price);
        url= new URL();

        con  = this;
        BtnAgregar.setOnClickListener(Agregar);
    }
     private View.OnClickListener Agregar  = new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String [] post = new String[] {"Size","Price"};
             String []datos  = new String[2];
              datos[0] = Size.getText().toString().trim();
              datos[1] = Price.getText().toString().trim();

                Consultas AddProducts = new Consultas(con);
                AddProducts.AgregarProducts(con, post,datos,url.GetUrl()+"/Movil/Insert_Products.php");
         }
     };

    private View.OnClickListener cancelar  = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
