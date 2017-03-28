package com.example.sads.mielera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
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

public class MyOrders extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    int posicion;
    ImageButton buttonImage ;
    private ViewAdapterOrders adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Data_Orders> datos;
    String contenido ;
    private JSONArray arreglo;
    private JSONObject objet;
    Activity prin;
    String infor;
    String url;
    URL getUrl;

    Context con;
    android.support.v7.app.ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        datos = new ArrayList<Data_Orders>();
        getUrl= new URL();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_orders);
        registerForContextMenu(mRecyclerView);
        con = this;
        AllOrders(getUrl.GetUrl() +"/Movil/Select_Orders_Id.php");

    }

    public void AllOrders(String url){



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
                                    datos.add(new Data_Orders(objet.getString("Name"),R.mipmap.ic_launcher,objet.getString("Date"),objet.getString("Quantity"),objet.getString("Discount"),objet.getString("Size")));
                                    //  datos.add(new Information(objet.getString("Size"), R.mipmap.ic_launcher, objet.getString("Price")));// Fill Data

                                }



                                mLayoutManager = new LinearLayoutManager(con,LinearLayoutManager.VERTICAL,false);
                                mRecyclerView.setLayoutManager(mLayoutManager);

                                adapter = new ViewAdapterOrders(datos,con);// Send data at ViewAdapter
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
                            Toast.makeText(con, "NO HAY PEDIDOS PARA ESTE CLIENTE", Toast.LENGTH_LONG).show();
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
                params.put("idClient",getIntent().getExtras().getString("id"));
                params.put("Count",getIntent().getExtras().getString("estado"));
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        requestQueue.add(stringRequest);



    }

   public void  abrir(int j){
        if(j==0){
            Intent i = new Intent(con, Payments.class);
            try {

                i.putExtra("total", objet.getString("Total"));
                i.putExtra("quantity", objet.getString("Quantity"));
                i.putExtra("discount", objet.getString("Discount"));
                i.putExtra("price", objet.getString("Price"));
                i.putExtra("id", objet.getString("idOrders"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(i);
        }else if (j==1){
            Intent i = new Intent(con, Payments_Client.class);
            try {
                i.putExtra("id", objet.getString("idOrders"));
                i.putExtra("quantity", objet.getString("Quantity"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(i);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.depositar:
                try {
                    objet = arreglo.getJSONObject(posicion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                abrir(0);
                return true;
            case R.id.Ver_Pagos:
                try {
                    objet = arreglo.getJSONObject(posicion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                abrir(1);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        int id= v.getId();
        MenuInflater menuInflater = this.getMenuInflater();
        switch (id){
            case R.id.my_orders:
                menuInflater.inflate(R.menu.menu_myorder,menu) ;
                break;

        }
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
