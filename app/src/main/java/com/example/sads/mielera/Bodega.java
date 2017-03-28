package com.example.sads.mielera;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Bodega extends Fragment {
    private RecyclerView mRecyclerView;
    int posicion;
    ImageButton buttonImage ;
    private ViewAdapterWarehouse adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Data_Payment> datos;
    String contenido ;
    private JSONArray arreglo;
    private JSONObject objet;
    Activity prin;
    String infor;
    String url;
    URL getUrl;
    Context con;
    public Bodega() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bodega, container, false);
        datos = new ArrayList<Data_Payment>();
        getUrl= new URL();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_bodega);
        buttonImage = (ImageButton) view.findViewById(R.id.imageButtonBodega);
        registerForContextMenu(mRecyclerView);
        con = getContext();
        WareHouse(getUrl.GetUrl() +"/Movil/Select_Warehouse.php");
        buttonImage.setOnClickListener(add);
        return view;
    }
    private View.OnClickListener add = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getContext(), AddWareHouse.class);
            startActivity(i);
        }
    };
    public void WareHouse(String url) {

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

                                    datos.add(new Data_Payment(objet.getString("Cantidad_total"), R.mipmap.ic_launcher, objet.getString("Fecha_ingreso"), objet.getString("Size")));


                                }


                                mLayoutManager = new LinearLayoutManager(con, LinearLayoutManager.VERTICAL, false);
                                mRecyclerView.setLayoutManager(mLayoutManager);

                                adapter = new ViewAdapterWarehouse(datos, con);// Send data at ViewAdapter
                                adapter.setOnLongClickListener(new View.OnLongClickListener() {//call de other method to class WiewAdapter
                                    @Override
                                    public boolean onLongClick(View view) {
                                        posicion = mRecyclerView.getChildAdapterPosition(view); //get position of my reciclerView
                                        //infor = datos.get(posicion).getTexto();//get content

                                        // Toast.makeText(prin, String.valueOf(posicion), Toast.LENGTH_LONG).show();
                                        return false;
                                    }
                                });
                                adapter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(con, "hola goa", Toast.LENGTH_LONG).show();
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
                params.put("idPayment", "");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        requestQueue.add(stringRequest);
    }

}
