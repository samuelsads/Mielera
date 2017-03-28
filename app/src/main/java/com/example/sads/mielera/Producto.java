package com.example.sads.mielera;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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
public class Producto extends Fragment {

    private RecyclerView mRecyclerView;
    int posicion;
    ImageButton buttonImage ;
    private ViewAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Information> datos;
    String contenido ;
    private JSONArray arreglo;
    private JSONObject objet;
    Activity prin;
    String infor;
    String url;
    URL getUrl;
    String REGISTER_URL = "/Movil/Select_Users.php";

    public static final String KEY_PASSWORD = "password";
    public Producto() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_producto, container, false);
        prin =getActivity();// get Actitivy
        datos = new ArrayList<Information>();
        getUrl  = new URL();
        buttonImage = (ImageButton) view.findViewById(R.id.imageButtonProduct);
        REGISTER_URL  = getUrl.GetUrl() +"/Movil/Select_Only_Products.php";
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_products);
        registerForContextMenu(mRecyclerView);
        AllProduct();
        buttonImage.setOnClickListener(BtnAddProducts);
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        int id= v.getId();
        MenuInflater menuInflater = prin.getMenuInflater();
        switch (id){
            case R.id.my_recycler_products:
                menuInflater.inflate(R.menu.menu_contextual,menu) ;
                break;
        }
    }

    private View.OnClickListener  BtnAddProducts = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            abrir(0);
        }
    };







    @Override
    public boolean onContextItemSelected(MenuItem item) {

        String ifor="";

        switch (item.getItemId()) {
            case R.id.editar:
                try {
                    objet = arreglo.getJSONObject(posicion);
                    abrir(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.eliminar:
                try {
                    objet = arreglo.getJSONObject(posicion);
                Consultas con = new Consultas(prin);
                con.DeleteProducts(prin,"Size", objet.getString("idProduct"),getUrl.GetUrl()+"/Movil/Delete_Products.php");
                datos.remove(posicion);
                adapter.notifyItemRemoved(posicion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    public void abrir (int j){
        if(j==0) {
            Intent i = new Intent(getContext(), AddProducts.class);
            startActivity(i);
        }else if(j==1){
            Intent i = new Intent(getContext(), UpdateProducts.class);
            try {
                i.putExtra("id", objet.getString("idProduct"));
                i.putExtra("size", objet.getString("Size"));
                i.putExtra("price", objet.getString("Price"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(i);
        }
    }



    public void AllProduct(){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.length() > 0) {
                            contenido = response;


                            try {
                                arreglo = new JSONArray(contenido);
                                for (int i = 0; i < arreglo.length(); i++) {
                                    objet = arreglo.getJSONObject(i);
                                    datos.add(new Information(objet.getString("Size"), R.mipmap.ic_launcher, objet.getString("Price")));// Fill Data

                                }



                                mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                                mRecyclerView.setLayoutManager(mLayoutManager);

                                adapter = new ViewAdapter(datos,getActivity());// Send data at ViewAdapter
                                adapter.setOnLongClickListener(new View.OnLongClickListener() {//call de other method to class WiewAdapter
                                    @Override
                                    public boolean onLongClick(View view) {
                                        posicion=mRecyclerView.getChildAdapterPosition(view); //get position of my reciclerView
                                        infor = datos.get(posicion).getTexto();//get content

                                        // Toast.makeText(prin, String.valueOf(posicion), Toast.LENGTH_LONG).show();
                                        return false;
                                    }
                                });

                                mRecyclerView.setAdapter(adapter);




                                //objet = arreglo.getJSONObject(0);
                                // Toast.makeText(prin, "DATOS DEL USUARIO: " + objet.getString("usuarios").toString(), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {

                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                            // getJSON(response);

                        } else {

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
                params.put(KEY_PASSWORD, "samuel");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);



    }


}
