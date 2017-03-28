package com.example.sads.mielera;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddWareHouse extends AppCompatActivity {
    Spinner spinner;
    URL url ;
    String idProduct="0";
    int idpro;
    String contenido ;
    JSONArray arreglo;
    JSONObject objet;
    Context con;
    private int day;
    private int month;
    private int year;
    private static  final int TIPO_DIALOG=0;
    private static DatePickerDialog.OnDateSetListener ListenerDate;
    EditText fecha,cantidad;
    Button btnFecha,BtnWareHouse;

    LlenarPersonal  llenadoPersonal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ware_house);
        spinner = (Spinner) findViewById(R.id.spinner);
        url = new URL();
        con = this;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        fecha  = (EditText) findViewById(R.id.FECHA);
        btnFecha = (Button) findViewById(R.id.BTNFECHA);
        BtnWareHouse = (Button) findViewById(R.id.btnWareHouse);
        BtnWareHouse.setOnClickListener(WareHouse);
        cantidad  = (EditText) findViewById(R.id.txtquantity);
        ObtenerPersonal(url.GetUrl()+"/Movil/Select_Only_Products.php");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idProduct = llenadoPersonal.ObtenerIdPersona(i);

                idpro=i;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        Calendar calendar  = Calendar.getInstance();
        year  = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day  = calendar.get(Calendar.DAY_OF_MONTH);
        SetDate();
        ListenerDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year  = i;
                month= i1;
                day=i2;
                SetDate();
            }
        };
        btnFecha.setOnClickListener(push);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private View.OnClickListener WareHouse = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!cantidad.getText().toString().equals("")) {
                if( Integer.parseInt(cantidad.getText().toString())>0) {
                    String post[] = new String[]{
                            "Cantidad_total", "Fecha_ingreso", "Product_idProduct"
                    };
                    String data[] = new String[post.length];
                    data[0] = cantidad.getText().toString();
                    data[1] = fecha.getText().toString();
                    data[2] = idProduct;
                    Toast.makeText(getApplicationContext(), data[0] + data[1] + data[2], Toast.LENGTH_LONG).show();
                    Consultas consulta = new Consultas(con);
                    consulta.AgregarWarehouse(con, post, data, url.GetUrl() + "/Movil/Insert_Warehouse.php");
                }else{
                    Toast.makeText(getApplicationContext(),"LA CANTIDAD ES 0 NO SE PUEDE INGRESAR EN BODEGA",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"ERROR EL CAMPO ESTA VACIO",Toast.LENGTH_LONG).show();
            }
        }
    };


    private View.OnClickListener push = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SetCalendar(view);
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case 0:
                return new DatePickerDialog(this,ListenerDate,year,month,day);
        }
        return  null;
    }

    private void SetCalendar(View dialog){
        showDialog(TIPO_DIALOG);
    }

    private void SetDate(){
        Toast.makeText(getApplicationContext(),"entre",Toast.LENGTH_LONG).show();
        fecha.setText(year + "-"+ (month+1)+"-"+ day);
    }

    public void ObtenerPersonal(String url){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            arreglo = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (response.length() > 0) {

                            //res =response;

                            llenadoPersonal = new LlenarPersonal(response,"");
                            ArrayAdapter<String> adapter= new ArrayAdapter<String>(con,android.R.layout.simple_list_item_1,llenadoPersonal.LlenarContenido());
                            spinner.setAdapter(adapter);
                            spinner.setSelection(llenadoPersonal.GetID());

                        } else {

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

              /*  params.put(KEY_USERNAME,"tiendita1");
                params.put(KEY_PASSWORD,"samuel");
                params.put(KEY_id,getPhoneNumber());
                params.put(KEY_privilegio,key);
                params.put(KEY_personal,"43939");
                params.put(KEY_EMAIL,"samueldzibsads@gmail.com");

                */
                params.put("producto", "samuel");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }
}
