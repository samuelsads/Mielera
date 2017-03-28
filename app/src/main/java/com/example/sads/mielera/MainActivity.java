package com.example.sads.mielera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //private static final String REGISTER_URL = "http://mieleradzibsosa.esy.es/Movil/CheckUser.php";
    private static final String REGISTER_URL = "http://192.168.1.67/MieleraDzibSosa/Movil/CheckUser.php";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    EditText user;
    EditText pas;
    Button buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (EditText) findViewById(R.id.editText);
        pas = (EditText) findViewById(R.id.editText2) ;
        buttonRegister = (Button) findViewById(R.id.button);
        buttonRegister.setOnClickListener(push);

    }

     private View.OnClickListener push = new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if(!user.getText().toString().equals("") && !pas.getText().toString().equals("")) {
                 ingreso();
             }
         }
     };

    private void ingreso(){


        StringRequest j = new StringRequest(
                Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.length()>0) {
                            Toast.makeText(getApplicationContext(), response.toString() , Toast.LENGTH_LONG).show();
                                abrir();
                        }else{
                            Toast.makeText(getApplicationContext(), "USUARIO O CONTRASEÑA INCORRECTA, VERIFICAR! " , Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,user.getText().toString().trim() );
                params.put(KEY_PASSWORD, pas.getText().toString().trim());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(j);
    }

    public void abrir (){
        String CreateUsuario="off";
        Intent i = new Intent(this, Principal.class );
        i.putExtra("parametro", CreateUsuario.toString());
        startActivity(i);
    }
}
