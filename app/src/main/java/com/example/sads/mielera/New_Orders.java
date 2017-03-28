package com.example.sads.mielera;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class New_Orders extends AppCompatActivity {
    Button btndate,btncancel, btnOrders;
    EditText txtdate,quantity, discount,client;
    TextView TxtTotal;
    Spinner spinner;
    private int day;
    private int month;
    private int year;
    String contenido ;
    JSONArray arreglo;
    JSONObject objet, object;
    Context con;
    URL url ;
    int idpro;
    String name, idProduct="0";
    LlenarPersonal  llenadoPersonal;
    private static  final int TIPO_DIALOG=0;
    private static DatePickerDialog.OnDateSetListener ListenerDate;
    int total_almacen;
    String idalmacen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__orders);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        btndate = (Button) findViewById(R.id.date);
        btncancel = (Button) findViewById(R.id.btncancelar);
        txtdate = (EditText) findViewById(R.id.txtdate);
        btnOrders =(Button) findViewById(R.id.btnpedido);
        quantity = (EditText) findViewById(R.id.txtquantity);
        discount = (EditText) findViewById(R.id.txtdiscount);
        client = (EditText) findViewById(R.id.txtclient);
        spinner = (Spinner) findViewById(R.id.spinner);
        TxtTotal = (TextView) findViewById(R.id.TextoTotal);
        Calendar calendar  = Calendar.getInstance();
        year  = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day  = calendar.get(Calendar.DAY_OF_MONTH);
        SetDate();
        client.setText(getIntent().getExtras().getString("name"));
        discount.setText("0");
        ListenerDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year  = i;
                month= i1;
                day=i2;
                SetDate();
            }
        };
        btndate.setOnClickListener(push);
        btncancel.setOnClickListener(cancel);
        url = new URL();
        con =this;
        name  = getIntent().getExtras().getString("name");
        ObtenerPersonal(url.GetUrl()+"/Movil/Select_Products.php");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idProduct = llenadoPersonal.ObtenerIdPersona(i);
                idpro=i;
                try {
                    object= arreglo.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                counttotal();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnOrders.setOnClickListener(enviar );
        discount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(!quantity.getText().toString().equals("") && !discount.getText().toString().equals("")) {
                    if(keyEvent.getKeyCode()==67) {
                                counttotal();
                    }else{


                                counttotal();


                    }
                }
                return false;
            }
        });
        quantity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                 if(!quantity.getText().toString().equals("") && !discount.getText().toString().equals("") ) {
                     if(keyEvent.getKeyCode()==67) {
                       counttotal();

                     }else{
                         try {
                             if(Integer.parseInt(quantity.getText().toString())>object.getInt("Cantidad_total")){
                                 Toast.makeText(con,"No tiene tantos productos en su almance",Toast.LENGTH_LONG).show();
                             }else{
                                 total_almacen = object.getInt("Cantidad_total") - Integer.parseInt(quantity.getText().toString());
                                    idalmacen = object.getString("idAlmacen");
                                 counttotal();
                             }
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }

                     }

                 }
                     return false;
            }
        });
    }

public void counttotal(){
    try {
        objet = arreglo.getJSONObject(idpro);

        TxtTotal.setText(String.valueOf(Integer.parseInt(quantity.getText().toString()) * (objet.getInt("Price")-Integer.parseInt(discount.getText().toString()))));

    } catch (JSONException e) {
        Toast.makeText(con, e.toString(), Toast.LENGTH_LONG).show();
    }
}
    private View.OnClickListener enviar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (txtdate.getText().toString().equals("") || quantity.getText().toString().equals("")) {
                Toast.makeText(con, "CAMPOS VACIOS", Toast.LENGTH_LONG).show();
            } else {
                if(discount.getText().toString().equals("")){
                    discount.setText("0");
                }
                String post[] = new String[]{"Date", "Quantity", "Discount","Total","Payment_Complete", "Client_idClient", "Product_idProduct","new_total","idAlmacen"};
                String date[] = new String[post.length];
                date[0] = txtdate.getText().toString().trim();
                date[1] = quantity.getText().toString().trim();
                date[2] = discount.getText().toString().trim();
                date[3]= TxtTotal.getText().toString().trim();
                date[4] = "0";
                date[5] = getIntent().getExtras().getString("id");
                date[6] = idProduct;
                date[7]= String.valueOf(total_almacen);
                date[8]= idalmacen;

                Consultas consulta = new Consultas(con);
                consulta.AgregarOrder(con, post, date, url.GetUrl() + "/Movil/Insert_Orders.php");
            }
        }
    };
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

    private View.OnClickListener  cancel = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
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
        txtdate.setText(year + "-"+ (month+1)+"-"+ day);
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

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
