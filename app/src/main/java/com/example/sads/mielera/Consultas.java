package com.example.sads.mielera;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sads on 13/07/16.
 */
public class Consultas {
    public static final String KEY_USERNAME = "username";
    public static final String KEY_ID = "id";
    Context context;

    public Consultas(Context context) {
        this.context = context;
    }

    public void AgregarUser(final Context ctx, final String user, final String url) {
        final Context context = ctx;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equals(true)) {
                            Toast.makeText(context, "Datos Agregados Correctamente", Toast.LENGTH_LONG).show();
                            abrir(context, 0);
                        } else {
                            Toast.makeText(context, "Error no se pudieron Agregar los datos", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME, user);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    public void abrir(Context context, int j) {// open new windows and send parament
        if (j == 0) {
            String CreateUsuario = "on";
            Intent i = new Intent(context, Principal.class);
            i.putExtra("parametro", CreateUsuario);
            context.startActivity(i);
        }
       else if (j == 1) {
            String CreateUsuario = "pro";
            Intent i = new Intent(context, Principal.class);
            i.putExtra("parametro", CreateUsuario);
            context.startActivity(i);
        }else if(j==2){
            String CreateUsuario = "warehouse";
            Intent i = new Intent(context, Principal.class);
            i.putExtra("parametro", CreateUsuario);
            context.startActivity(i);
        }

    }

    public void UpdateUsers(final Context ctx, final String[] user, final String url) {
        final Context context = ctx;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equals(true)) {
                            Toast.makeText(context, "Datos Actualizados Correctamente", Toast.LENGTH_LONG).show();
                            abrir(context, 0);
                        } else {
                            Toast.makeText(context, "Error no se pudieron Actualizar los datos, intente de nuevo", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_ID, user[0]);
                params.put(KEY_USERNAME, user[1]);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    public void DeleteUser(final Context context, final String dato, String url) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equals(true)) {
                            Toast.makeText(context, "Usuario eliminado correctamente", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Error al eliminar el dato", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(KEY_USERNAME, dato);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }




    public void AgregarProducts(final Context ctx,final String[]post, final String[] datos, final String url){
        final Context context= ctx;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equals(true)){
                            Toast.makeText(context, "Producto Agregados Correctamente", Toast.LENGTH_LONG).show();
                            abrir(context,1);
                        }else{
                            Toast.makeText(context, "Error no se pudo agregar el producto", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(post[0], datos[0]);
                params.put(post[1], datos[1]);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    public void DeleteProducts(final Context context, final String post, final String dato, String url) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equals(true)) {
                            Toast.makeText(context, "Producto eliminado correctamente", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Error al eliminar el dato", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(post, dato);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    public void UpdateProducts(final Context ctx,final String[]post, final String[] user, final String url) {
        final Context context = ctx;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equals(true)) {
                            Toast.makeText(context, "Producto Actualizado Correctamente", Toast.LENGTH_LONG).show();
                            abrir(context, 1);
                        } else {
                            Toast.makeText(context, "Error no se pudo Actualizar el producto, intente de nuevo", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(post[0], user[0]);
                params.put(post[1], user[1]);
                params.put(post[2], user[2]);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }


    public void AgregarOrder(final Context ctx,final String[]post, final String[] datos, final String url){
        final Context context= ctx;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equals(true)){
                            Toast.makeText(context, "Producto Agregados Correctamente", Toast.LENGTH_LONG).show();
                            abrir(context,0);
                        }else{
                            Toast.makeText(context, "Error no se pudo agregar el producto", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(post[0], datos[0]);
                params.put(post[1], datos[1]);
                params.put(post[2], datos[2]);
                params.put(post[3], datos[3]);
                params.put(post[4], datos[4]);
                params.put(post[5], datos[5]);
                params.put(post[6], datos[6]);
                params.put(post[7], datos[7]);
                params.put(post[8], datos[8]);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }


    public void AgregarPayment(final Context ctx,final String[]post, final String[] datos, final String url){
        final Context context= ctx;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equals(true)){
                            Toast.makeText(context, "Pago Agregados Correctamente", Toast.LENGTH_LONG).show();
                            abrir(context,0);
                        }else{
                            Toast.makeText(context, "Error el pago no pudo ser depositado", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(post[0], datos[0]);
                params.put(post[1], datos[1]);
                params.put(post[2], datos[2]);
                params.put(post[3], datos[3]);
                params.put(post[4], datos[4]);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    public void AgregarWarehouse(final Context ctx,final String[]post, final String[] datos, final String url){
        final Context context= ctx;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equals(true)){
                            Toast.makeText(context, "Pago Agregados Correctamente", Toast.LENGTH_LONG).show();
                            abrir(context,2);
                        }else{
                            Toast.makeText(context, "Error el pago no pudo ser depositado", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(post[0], datos[0]);
                params.put(post[1], datos[1]);
                params.put(post[2], datos[2]);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }
}
