package com.example.yap_news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText correo;
    private EditText contraseña;
    private Button botonRegistrar;
    private Button botonIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();
        correo = findViewById(R.id.txtCorreo);
        contraseña = findViewById(R.id.txtContraseña);
        botonIniciar = findViewById(R.id.botonIniciar);
        botonRegistrar = findViewById(R.id.botonRegistro);

        FirebaseUser usuarioActual = mAuth.getCurrentUser();
        if(usuarioActual != null){
            Intent intent = new Intent(com.example.yap_news.AuthActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario(correo, contraseña);
            }
        });

        botonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarUsuario(correo, contraseña);
            }
        });
    }

    private  void registrarUsuario(EditText correo, EditText contraseña){
        Intent intent = new Intent(com.example.yap_news.AuthActivity.this, RegActivity.class);
        startActivity(intent);
    }

    private void iniciarUsuario(EditText correo, EditText contraseña){
        String correoE = correo.getText().toString().trim();
        String pass = contraseña.getText().toString().trim();

        if(correoE.equals("") || pass.equals("")){
            Toast.makeText(AuthActivity.this, "Los campos deben ser llenados", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(correoE, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("Estado:", "Iniciando");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(com.example.yap_news.AuthActivity.this, HomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(com.example.yap_news.AuthActivity.this, "has Iniciado sesion!!", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.w("Estado", "Hubo un fallo", task.getException());
                                Toast.makeText(AuthActivity.this, "Usuario o contraseña no validos", Toast.LENGTH_SHORT).show();                            }
                        }
                    });
        }
    }
}