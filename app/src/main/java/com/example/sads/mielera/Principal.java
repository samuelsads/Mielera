package com.example.sads.mielera;

import android.content.res.Configuration;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;

import android.widget.ListView;


public class Principal extends AppCompatActivity {

    private String[] op;
    private DrawerLayout drawerLayout;
    private ListView listView;
    private CharSequence tituloSect;
    private CharSequence tituloaplicacion;
    android.support.v7.app.ActionBarDrawerToggle drawerToggle;
    String b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        op = new String[]{"Clientes", "Productos", "Pedidos", "Bodega"};
        drawerLayout = (DrawerLayout) findViewById(R.id.contenedorPrincipal);
        listView = (ListView) findViewById(R.id.menuIzq);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, op));
        b=getIntent().getExtras().getString("parametro");
        if(b.equals("on")){//receive b for it knows if have got something
            fragmentos(0);
        }else if (b.equals("pro")){
            fragmentos(1);
        }else if (b.equals("pe")){
            fragmentos(2);
        }else if (b.equals("warehouse")){
            fragmentos(3);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
           fragmentos(i);

            }
        });
        tituloSect = getTitle();
        tituloaplicacion = getTitle();
        drawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, drawerLayout, R.string.abierto, R.string.cerrado );
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
public void fragmentos(int i){
    Fragment fragment = null;
    if (i == 0) {
        fragment = new Usuarios();
    }
    else if (i == 1) {
        fragment = new Producto();
    } else if (i == 2) {
        fragment = new All_Order();
    } else if (i == 3) {
        fragment = new Bodega();
                  /*  Bundle args = new Bundle();
                    args.putString("pruebajson", j);
                    fragment.setArguments(args);*/
    }
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.contenedorFrame, fragment).commit();
    //fragmentManager.beginTransaction().replace(R.id.contenedorFrame, fragment).addToBackStack(null).commit(); sirve para guardar los estados al dar clic derecho

    listView.setItemChecked(i, true);
    tituloSect = op[i];
    getSupportActionBar().setTitle(tituloSect);
    drawerLayout.closeDrawer(listView);
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
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
