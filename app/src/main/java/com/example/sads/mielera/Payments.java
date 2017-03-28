package com.example.sads.mielera;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class Payments extends AppCompatActivity {
    String total, quantity,discount, idOrder;
    EditText quantity_payment, date;
    TextView txtTotal,txtQuantity;
    Button btnDate,BtnPayment;
    String price;
    String discount1;
    int day, month, year;
    Context con;
    String contenido ;
    private JSONArray arreglo;
    private JSONObject objet;
    URL url;
    private static  final int TIPO_DIALOG=0;
    private static DatePickerDialog.OnDateSetListener ListenerDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        txtTotal = (TextView) findViewById(R.id.txtCantidadTotal);
        price = getIntent().getExtras().getString("price");
        discount1= getIntent().getExtras().getString("discount");
        txtQuantity = (TextView) findViewById(R.id.quantity_payment);
        date = (EditText) findViewById(R.id.txtdate) ;
        btnDate = (Button) findViewById(R.id.date);
        BtnPayment = (Button) findViewById(R.id.button2);
        btnDate.setOnClickListener(push);
        BtnPayment.setOnClickListener(addpayment);
        con = this;
        url = new URL();
        Payment_quantity_total(url.GetUrl()+"/Movil/Select_Quantity_Payment.php");
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
        quantity_payment = (EditText) findViewById(R.id.txtquantity);
        quantity_payment.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(!quantity_payment.getText().toString().equals("")){
                    if(Integer.parseInt(quantity_payment.getText().toString())<=
                            Integer.parseInt(txtTotal.getText().toString())){
                            ContTotal();
                        BtnPayment.setEnabled(true);

                    }else{
                        Toast.makeText(getApplicationContext(),"ERROR LA CANTIDAD TOTAL DEL PRODUCTO ES MENOR A LO QUE INGRESASTE",Toast.LENGTH_LONG).show();
                        BtnPayment.setEnabled(false);
                    }
                }
                return false;
            }
        });

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

    private View.OnClickListener addpayment  = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String post[] = new String[]{
              "quantity_payment","payment_received","date_payment","idorder","update"
            };
            String date1[] = new String[post.length];
            date1[0]=quantity_payment.getText().toString();
            date1[1]= txtQuantity.getText().toString();
            date1[2]=date.getText().toString();
            date1[3]=getIntent().getExtras().getString("id");
            if(Integer.parseInt(quantity_payment.getText().toString())==
                    Integer.parseInt(txtTotal.getText().toString())){
                date1[4]="1";

            }else{
            date1[4]="0";

            }
            Consultas consulta  = new Consultas(con);
            consulta.AgregarPayment(con,post,date1,url.GetUrl() +"/Movil/Insert_Payments.php");
        }
    };

    public void Payment_quantity_total(String url){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            int total,totalpagado;
                        if (response.length() > 0) {
                           Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                            total  =Integer.parseInt(getIntent().getExtras().getString("quantity"));
                            totalpagado = Integer.parseInt(response);
                            txtTotal.setText(String.valueOf(total-totalpagado));

                        } else {
                            total  =Integer.parseInt(getIntent().getExtras().getString("quantity"));
                            txtTotal.setText(String.valueOf(total));

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
                params.put("idOrders",getIntent().getExtras().getString("id"));
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(con);
        requestQueue.add(stringRequest);



    }


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
        date.setText(year + "-"+ (month+1)+"-"+ day);
    }
    public void ContTotal(){

        txtQuantity.setText(String.valueOf(Integer.parseInt(quantity_payment.getText().toString())*(Integer.parseInt(price)-Integer.parseInt(discount1))));
    }
}
