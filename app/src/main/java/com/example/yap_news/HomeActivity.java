package com.example.yap_news;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
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

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private adapterNoticias adapter;
    private RecyclerView.LayoutManager layoutManager;
    private final com.example.yap_news.Stack<String> Stack = new Stack<>();
    private TextView textView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        //final EditText insertUrl = findViewById(R.id.InsertUrl);
        //Button button = findViewById(R.id.uploadUrl);
        //Button botonCerrar = findViewById(R.id.botonSalir);
        Button botonComunidad = findViewById(R.id.botonComunidad);
        toolbar = findViewById(R.id.home_activity_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_View);
        View navHeader = navigationView.inflateHeaderView(R.layout.menu_header);
        actionBarDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.items_noticias);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new adapterNoticias(Stack);
        recyclerView.setAdapter(adapter);


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