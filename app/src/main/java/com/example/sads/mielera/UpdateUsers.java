package com.example.sads.mielera;

import android.content.Context;
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

public class UpdateUsers extends AppCompatActivity {
    private EditText UdUsuario;
    private Button BtnUpdate, BtnCancel;
    Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_users);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        UdUsuario  =  (EditText) findViewById(R.id.TxtUser);
        UdUsuario.setText(getIntent().getExtras().getString("usuarios"));
        BtnUpdate = (Button) findViewById(R.id.BtnUpdate);
        BtnCancel = (Button) findViewById(R.id.BtnCancelUpdate);
        BtnUpdate.setOnClickListener(UpdateUsers);
        BtnCancel.setOnClickListener(cancel);
        con = this;

    }

    private View.OnClickListener UpdateUsers  = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!UdUsuario.getText().equals("")) {
                    String[] contenido = new String[3];
                    contenido[0] = getIntent().getExtras().getString("id");
                    contenido[1] = UdUsuario.getText().toString();
                    Consultas consulta = new Consultas(getApplicationContext());
                    consulta.UpdateUsers(con, contenido, "http://mieleradzibsosa.esy.es/Movil/Update_Users.php");
            }else{
                Toast.makeText(getApplicationContext(),"El campo esta vacio",Toast.LENGTH_LONG).show();
            }
        }
    };

private View.OnClickListener cancel  = new View.OnClickListener() {
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
