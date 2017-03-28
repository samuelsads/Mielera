package com.example.sads.mielera;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateProducts extends AppCompatActivity {
    Button BtnUpdate, BtnCancel ;
    EditText txtSize,txtPrice;
    String s,p;
    Context con;
    URL url  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_update_products);
        txtSize = (EditText) findViewById(R.id.TxtSize);
        txtPrice = (EditText) findViewById(R.id.TxtPrice);
        s=getIntent().getExtras().getString("size");
        txtSize.setText(s);
        p=getIntent().getExtras().getString("price");
        txtPrice.setText(p);
        BtnCancel = (Button) findViewById(R.id.BtnCancelUpdateProduct);
        BtnUpdate = (Button) findViewById(R.id.BtnUpdateProduct);
        url = new URL();
        con=this;
        BtnCancel.setOnClickListener(cancel);
        BtnUpdate.setOnClickListener(update);

    }

   private View.OnClickListener cancel  = new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           onBackPressed();
       }
   };

    private View.OnClickListener update  = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
if (txtSize.getText().toString().trim().equals("")|| txtPrice.getText().toString().trim().equals("")) {

    Toast.makeText(getApplicationContext(), "Los campos estan vacios", Toast.LENGTH_LONG).show();
}else{
    if(txtPrice.getText().equals(p)|| txtSize.getText().equals(s)){
        Toast.makeText(getApplicationContext(), "No es necesario actualizar, los campos no han sido modificados", Toast.LENGTH_LONG).show();
    }else{
        String[] post =  new String[]{"id","Size","Price"};
        String datos[] = new String[3];
        datos[0]=getIntent().getExtras().getString("id");
        datos[1]=txtSize.getText().toString().trim();
        datos[2]= txtPrice.getText().toString().trim();
        Consultas update_product = new Consultas(con);
        update_product.UpdateProducts(con,post,datos,url.GetUrl()+"/Movil/Update_Products.php");
    }
}

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

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
