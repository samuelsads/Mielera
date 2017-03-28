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

public class AddUsers extends AppCompatActivity {
Button BtnAdd,BtnCancel;
    EditText user;
    Context context;
    Usuarios usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        BtnAdd = (Button) findViewById(R.id.BtnAdd);
        BtnCancel = (Button) findViewById(R.id.BtnCancel);
        user = (EditText) findViewById(R.id.editText3);
        context=this;
        BtnAdd.setOnClickListener(Add);
        BtnCancel.setOnClickListener(cancel);
    }
private View.OnClickListener cancel  = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        onBackPressed();
    }
};
    private View.OnClickListener Add = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!user.getText().toString().trim().equals("")) {
                Consultas consulta = new Consultas(getApplicationContext());
                consulta.AgregarUser(context, user.getText().toString().trim(), "http://192.168.1.67/MieleraDzibSosa/Movil/Insert_Clients.php");





            }else{
                Toast.makeText(context,"Campo vacio",Toast.LENGTH_LONG).show();

            }
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
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
