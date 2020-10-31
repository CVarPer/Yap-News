package com.example.yap_news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText correo;
    private EditText contraseña;
    private Button botonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        mAuth = FirebaseAuth.getInstance();
        correo = findViewById(R.id.txt_correo);
        contraseña = findViewById(R.id.txt_contraseña);
        botonRegistrar = findViewById(R.id.botonRegistro);

        Intent intent = getIntent();

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario(correo, contraseña);
            }
        });
    }

    private  void registrarUsuario(EditText correo, EditText contraseña){
        String correoE = correo.getText().toString().trim();
        String pass = contraseña.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(correoE, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("Estado", "Usuario creado");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(com.example.yap_news.RegActivity.this, HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(com.example.yap_news.RegActivity.this, "Te has registrado!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.w("Estado","Hubo un fallo", task.getException());
                            Toast.makeText(com.example.yap_news.RegActivity.this, "Este usuario ya està registrado", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}