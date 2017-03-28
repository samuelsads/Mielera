package com.example.sads.mielera;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
public class Usuarios extends Fragment {
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
    Information infor;
    ImageView imageView;
    Drawable drawable;
    Bitmap originalBitmap;
    RoundedBitmapDrawable roundedDrawable;
    ImageButton imageButton;
    URL getUrl;
    Boolean registro= false;
     String REGISTER_URL = "/MieleraDzibSosa/Movil/Select_Users.php";
    public static final String KEY_PASSWORD = "password";
    public boolean update=false;

    public Usuarios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);
        imageButton = (ImageButton) view.findViewById(R.id.imageButton);
        prin =getActivity();// get Actitivy
        datos = new ArrayList<Information>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view1);
        registerForContextMenu(mRecyclerView);
        getUrl  = new URL();
        getUrl.GetUrl();
        REGISTER_URL  = getUrl.GetUrl() +"/MieleraDzibSosa/Movil/Select_Users.php";
        AllUser();

        imageButton.setOnClickListener(NewUser);
        return view;
    }



 private View.OnClickListener NewUser = new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         abrir(0);
     }
 };




    public void AllUser(){



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
                                    datos.add(new Information(objet.getString("Name").toString(), R.mipmap.ic_launcher, ""));// Fill Data

                                }



                                mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                                mRecyclerView.setLayoutManager(mLayoutManager);

                                adapter = new ViewAdapter(datos,getActivity());// Send data at ViewAdapter
                                adapter.setOnLongClickListener(new View.OnLongClickListener() {//call de other method to class WiewAdapter
                                    @Override
                                    public boolean onLongClick(View view) {
                                        posicion=mRecyclerView.getChildAdapterPosition(view); //get position of my reciclerView
                                        infor = datos.get(posicion);//get content

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





    public void abrir (int j) {
        if (j == 0) {
            Intent i = new Intent(getContext(), AddUsers.class);
            startActivity(i);
        } else if (j == 1) {
            Intent i = new Intent(getContext(), UpdateUsers.class);
            try {
                i.putExtra("usuarios", objet.getString("Name"));
                i.putExtra("id", objet.getString("idClient"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(i);
        } else if (j == 2) {
            Intent i = new Intent(getContext(), New_Orders.class);
            try {
                i.putExtra("id", objet.getString("idClient"));
                i.putExtra("name", objet.getString("Name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(i);
        } else if (j == 3) {
            Intent i = new Intent(getContext(), MyOrders.class);
            try {
                i.putExtra("id", objet.getString("idClient"));
                if(registro==true){
                    i.putExtra("estado", "1");
                    registro=false;
                }else {
                    i.putExtra("estado", "0");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(i);

        }
        else if (j == 4) {
            Intent i = new Intent(getContext(), HistoryPayments.class);
            try {
                i.putExtra("id", objet.getString("idClient"));
                if(registro==true){
                    i.putExtra("estado", "1");
                    registro=false;
                }else {
                    i.putExtra("estado", "0");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(i);

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        int id= v.getId();
        MenuInflater menuInflater = prin.getMenuInflater();
        switch (id){
            case R.id.my_recycler_view1:
                menuInflater.inflate(R.menu.menu_clients,menu) ;
                break;
        }
    }

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
                ifor= infor.getTexto();
                Consultas con = new Consultas(prin);
                con.DeleteUser(prin, ifor.toString(),"http://mieleradzibsosa.esy.es/Movil/Delete_Clients.php");
                datos.remove(posicion);
                adapter.notifyItemRemoved(posicion);
                return true;
            case R.id.NuevoPedido:
                try {
                    objet = arreglo.getJSONObject(posicion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                abrir(2);
                return true;
            case R.id.VerPedidos:
                try {
                    objet = arreglo.getJSONObject(posicion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                abrir(3);
                return true;
            case R.id.VerPedidosPagados:
                try {
                    objet = arreglo.getJSONObject(posicion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                registro=true;
                abrir(3);
                return true;
            case R.id.VerPediditosTotales:
                try {
                    objet = arreglo.getJSONObject(posicion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                registro=true;
                abrir(4);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
