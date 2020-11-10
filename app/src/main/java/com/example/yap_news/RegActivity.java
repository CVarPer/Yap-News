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
    private EditText nombre;
    private EditText telefono;
    private EditText correo;
    private EditText contraseña;
    private EditText confirmar;
    private Button botonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        mAuth = FirebaseAuth.getInstance();
        nombre = findViewById(R.id.txt_nombre);
        telefono = findViewById(R.id.txt_telefono);
        correo = findViewById(R.id.txt_correo);
        contraseña = findViewById(R.id.txt_contraseña);
        confirmar = findViewById(R.id.txt_confirmar);
        botonRegistrar = findViewById(R.id.botonRegistro);

        Intent intent = getIntent();

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario(correo, contraseña);
            }
        });
    }


    private  void registrarUsuario(final EditText correo, final EditText contraseña){
        String nombreE = nombre.getText().toString().trim();
        String telefonoE = telefono.getText().toString().trim();
        String correoE = correo.getText().toString().trim();
        String pass = contraseña.getText().toString().trim();
        String confirmarE = confirmar.getText().toString().trim();

        if(nombreE.isEmpty() || telefonoE.isEmpty() || correoE.isEmpty() || pass.isEmpty()){
            Toast.makeText(RegActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
        }
        else if(pass.length() < 8){
            Toast.makeText(RegActivity.this, "La contraseña debe tener minimo 8 caracteres", Toast.LENGTH_SHORT).show();
        }
        else if(!pass.equals(confirmarE)){
            Toast.makeText(RegActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(correoE, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("Estado", "Usuario creado");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(com.example.yap_news.RegActivity.this, HomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(com.example.yap_news.RegActivity.this, "Te has registrado exitosamente", Toast.LENGTH_SHORT).show();
                                nombre.setText("");
                                telefono.setText("");
                                correo.setText("");
                                contraseña.setText("");
                                confirmar.setText("");
                            } else {
                                Log.w("Estado", "Ha ocurrido un error", task.getException());
                                Toast.makeText(com.example.yap_news.RegActivity.this, "Este usuario ya está registrado", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }
}