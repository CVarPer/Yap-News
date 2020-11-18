package com.example.yap_news;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private adapterNoticias adapter;
    private LinearLayoutManager layoutManager;


    //private RichLinkViewTelegram richLinkViewTelegram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        long tInicio = System.currentTimeMillis();
        Button botonComunidad = findViewById(R.id.botonComunidad);
        toolbar = findViewById(R.id.home_activity_toolbar);
        setSupportActionBar(toolbar);

        //Menú desplegable
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_View);
        View navHeader = navigationView.inflateHeaderView(R.layout.menu_header);

        //Hamburguer icon
        actionBarDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                drawerView.bringToFront();
                drawerLayout.requestLayout();
            }
        };
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RecyclerView (vista de las noticias)
        recyclerView = findViewById(R.id.items_noticias);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);

        //del archivo de datos pasar a un arreglo (?
        //iterar arreglo, crear modelo, asginar atributos
        //push modelo en el stack
        //settear adaptador
        int pruebaN = 100;
        String[][] datosPrueba = new String[pruebaN][5];
        String[] datosUrl = new String[pruebaN];
        datosUrl = setDatosUrl(pruebaN);
        String[] nombre = randomString(pruebaN);
        String[] autor = randomString(pruebaN);
        String[] fecha = randomString(pruebaN);
        String[] hora = randomString(pruebaN);

        for(int i=0; i<datosPrueba.length; i++){
            datosPrueba[i][0] = nombre[i];
            datosPrueba[i][1] = autor[i];
            datosPrueba[i][2] = fecha[i];
            datosPrueba[i][3] = hora[i];
            datosPrueba[i][4] = datosUrl[i];
        }


        List<Noticias> stack = new ArrayList<>();
        for (String[] string : datosPrueba) {
            Noticias noticias = new Noticias();
            noticias.setNombre(string[0]);
            noticias.setAutor(string[1]);
            noticias.setFecha(string[2]);
            noticias.setHora(string[3]);
            noticias.setNewsUrl(string[4]);

            adapter = new adapterNoticias(stack);
            adapter.addNoticia(noticias);
            recyclerView.setAdapter(adapter);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectorMenu(item);
                return false;
            }
        });
        /*
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        textView = findViewById(R.id.webView);
                        webView = findViewById(R.id.webView_Noticia);
                        webView.loadUrl(textView.getText().toString());
                    }
                });*/
        botonComunidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent comunidad = new Intent(HomeActivity.this, ComunityActivity.class);
                startActivity(comunidad);
            }
        });
        long tFinal = System.currentTimeMillis();

        Log.d("TIEMPO", "Número de datos: "+pruebaN);
        Log.d("TIEMPO DE EJECUCIÓN", tFinal-tInicio+" ms");
    }

    private String[] setDatosUrl(int n)  {
        AssetManager am = HomeActivity.this.getAssets();
        String[] lines = new String[n];
        try{
            InputStream inputStream = am.open("url_pruebas.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            int i = 0;
            while (reader.readLine()!=null && i < n) {
                lines[i] = reader.readLine();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return lines;
    }
    private String[] randomString(int n){
        String[] randString = new String[n];
        for(int i=0; i<randString.length; i++){
            Random aleatorio = new Random();
            String alfa = "ABCDEFGHIJKLMNOPQRSTVWXYZ";
            String cadena = "";
            int numero;
            int forma;
            forma = (int)(aleatorio.nextDouble() * alfa.length()-1+0);
            numero = (int)(aleatorio.nextDouble() * 99+100);
            cadena = cadena+alfa.charAt(forma)+numero;
            randString[i] = cadena;
        }
        return randString;
    }
    /*private void MostrarNoticias() {
        FirebaseRecyclerAdapter<Noticias, NoticiasViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Noticias, NoticiasViewHolder>() {
            @Override
            protected void onBindViewHolder(@NonNull NoticiasViewHolder holder, int position, @NonNull Noticias model) {

            }

            @NonNull
            @Override
            public NoticiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };
    }*/
    /*public static class NoticiasViewHolder extends RecyclerView.ViewHolder{
        View view;
        public NoticiasViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
    }*/
    private void selectorMenu(MenuItem item) {
        switch(item.getItemId()){
            case R.id.navPerfil:
                Toast.makeText(HomeActivity.this, "Perfil", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navGuardados:
                Toast.makeText(HomeActivity.this, "Colecciones", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navAjustes:
                Toast.makeText(HomeActivity.this, "Ajustes", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navCerrar:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HomeActivity.this, "Cerraste sesión", Toast.LENGTH_SHORT).show();
                onBackPressed();
                startActivity(new Intent(HomeActivity.this, AuthActivity.class));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }
}

  //Documentation: https://developer.android.com/guide/topics/ui/layout/recyclerview#add-support-library