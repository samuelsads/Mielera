package com.example.sads.mielera;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Payments_Client extends AppCompatActivity {
    int posicion;
    private RecyclerView mRecyclerView;
    ImageButton buttonImage ;
    private ViewAdapterPayment adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Data_Payment> datos;
    String contenido ;
    private JSONArray arreglo;
    private JSONObject objet;
    URL getUrl;
    Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments__client);
        datos = new ArrayList<Data_Payment>();
        getUrl= new URL();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_Payment_Client);
        registerForContextMenu(mRecyclerView);
        con = this;
        Payments_Client(getUrl.GetUrl() +"/Movil/Select_Payment_Id.php");

    }

    public void Payments_Client(String url){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.length() > 0) {
                            contenido = response;
                            try {
                                arreglo = new JSONArray(contenido);
                                for (int i = 0; i < arreglo.length(); i++) {
                                    objet = arreglo.getJSONObject(i);
                                   // datos.add(new Data_Payment("hola",R.mipmap.ic_launcher,"hola","hola"));
                                    datos.add(new Data_Payment(objet.getString("Quantity_payment"),R.mipmap.ic_launcher,objet.getString("Date_payment"),objet.getString("Payment_received")));


                                }



                                mLayoutManager = new LinearLayoutManager(con,LinearLayoutManager.VERTICAL,false);
                                mRecyclerView.setLayoutManager(mLayoutManager);

                                adapter = new ViewAdapterPayment(datos,con);// Send data at ViewAdapter
                                adapter.setOnLongClickListener(new View.OnLongClickListener() {//call de other method to class WiewAdapter
                                    @Override
                                    public boolean onLongClick(View view) {
                                        posicion=mRecyclerView.getChildAdapterPosition(view); //get position of my reciclerView
                                        //infor = datos.get(posicion).getTexto();//get content

                                        // Toast.makeText(prin, String.valueOf(posicion), Toast.LENGTH_LONG).show();
                                        return false;
                                    }
                                });
                                adapter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getApplicationContext(),"hola goa",Toast.LENGTH_LONG).show();
                                    }
                                });
                                mRecyclerView.setAdapter(adapter);




                                //objet = arreglo.getJSONObject(0);
                                // Toast.makeText(prin, "DATOS DEL USUARIO: " + objet.getString("usuarios").toString(), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {

                                Toast.makeText(con, e.toString(), Toast.LENGTH_LONG).show();
                            }
                            // getJSON(response);

                        } else {
                            Toast.makeText(con, "NO HAY PAGOS  PARA ESTA ORDEN", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        contenido = error.toString();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

              /*  params.put(KEY_USERNAME,"tiendita1");
                params.put(KEY_PASSWORD,"samuel");
                params.put(KEY_id,getPhoneNumber());
                params.put(KEY_privilegio,key);
                params.put(KEY_personal,"43939");
                params.put(KEY_EMAIL,"samueldzibsads@gmail.com");

                */
                params.put("idPayment",getIntent().getExtras().getString("id"));
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        requestQueue.add(stringRequest);



    }
}
