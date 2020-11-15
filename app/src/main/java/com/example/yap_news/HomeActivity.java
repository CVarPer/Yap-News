package com.example.yap_news;

import android.content.Intent;
import android.os.Bundle;
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

import io.github.ponnamkarthik.richlinkpreview.RichLinkViewTelegram;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private adapterNoticias adapter;
    private LinearLayoutManager layoutManager;
    private final com.example.yap_news.Stack<Noticias> Stack = new Stack<Noticias>();

    private RichLinkViewTelegram richLinkViewTelegram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        Button botonComunidad = findViewById(R.id.botonComunidad);
        toolbar = findViewById(R.id.home_activity_toolbar);
        setSupportActionBar(toolbar);

        //Menú desplegable
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_View);
        View navHeader = navigationView.inflateHeaderView(R.layout.menu_header);

        //Hamburguer icon
        actionBarDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
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

        String[][] strings = {{"El Tiempo", "Carlos", "14/11/2020","8:18","https://www.eltiempo.com"},
                {"El Espectador","Ramiro","15/11/2020","9:30", "https://www.elespectador.com"}};

        for (int i=0; i<strings.length; i++) {
            Noticias noticias = new Noticias();
            noticias.setNombre(strings[i][0]);
            noticias.setAutor(strings[i][1]);
            noticias.setFecha(strings[i][2]);
            noticias.setHora(strings[i][3]);
            noticias.setNewsUrl(strings[i][4]);

            adapter = new adapterNoticias(Stack);
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




        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addUrl(insertUrl.getText().toString());
            }
        });
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
    }

    //private void MostrarNoticias() {
        /*FirebaseRecyclerAdapter<Noticias, NoticiasViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Noticias, NoticiasViewHolder>() {
            @Override
            protected void onBindViewHolder(@NonNull NoticiasViewHolder holder, int position, @NonNull Noticias model) {

            }

            @NonNull
            @Override
            public NoticiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };*/
    //}
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
                Toast.makeText(HomeActivity.this, "Cerraste sesion", Toast.LENGTH_SHORT).show();
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