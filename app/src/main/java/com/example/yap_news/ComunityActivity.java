package com.example.yap_news;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Map;
import java.util.Random;

public class ComunityActivity extends AppCompatActivity {

    //Usuario para id de publicaciones
    FirebaseUser usuarioActual;

    //popup para publicaciones de usuarios
    Dialog publicacion;
    EditText tituloPublicacion;
    EditText txtContenido;
    ImageView img;
    ImageView botonAnclar;
    Button botonPublicar;

    //Elementos en lista de publicaciones
    TextView tituloPublicado;
    TextView txtContenidoPublicado;
    ImageView imgPublicada;

    //Uri de imagen subida en firebase
    Uri path;

    //Clases firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private StorageReference mstorage;
    private ListView listView;
    private Adaptador adaptador;
    pila publicacionesComunidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunity);

        mstorage = FirebaseStorage.getInstance().getReference();
        usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        publicacion = new Dialog(this);

        botonAnclar = findViewById(R.id.botonAnclar);
        publicacionesComunidad = new pila();

        iniciarFirebase();
        cargarPublicaciones();

        //En listview
        tituloPublicado = findViewById(R.id.tituloPublicado);
        txtContenidoPublicado = findViewById(R.id.txtContenidoPublicado);
        imgPublicada = findViewById(R.id.imgPublicada);

    }

    private void iniciarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void cargarPublicaciones(){
        mstorage = FirebaseStorage.getInstance().getReference();

        databaseReference.child(usuarioActual.getUid().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> publicaciones = dataSnapshot.getChildren();
                            for(DataSnapshot p: publicaciones){
                                String contenido = p.child("contenido").getValue().toString();
                                if(contenido.length() > 130) {
                                    publicacionesComunidad.push(contenido.substring(0, 130) + "...");
                                }
                                else{
                                    publicacionesComunidad.push(contenido);
                                }
                                publicacionesComunidad.push(p.child("titulo").getValue().toString());
                                publicacionesComunidad.push(p.child("imgId").getValue().toString());
                                publicacionesComunidad.push(p.getKey());
                            }
                            listView = (ListView) findViewById(R.id.noticiasComunidad);
                            adaptador = new Adaptador(ComunityActivity.this, publicacionesComunidad);
                            listView.setAdapter(adaptador);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ComunityActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                });
    Log.w("Estado", "YES");

        /*
        databaseReference.child(usuarioActual.getUid().toString()).child("1")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String titulo = dataSnapshot.child("titulo").getValue().toString();
                    String contenido = dataSnapshot.child("contenido").getValue().toString();
                    tituloPublicado.setText(titulo);
                    txtContenidoPublicado.setText(contenido);
                    cargarPublicaciones();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ComunityActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
            }
        });

        StorageReference islandRef = mstorage.child("Fotos Publicaciones").child("17913");
        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imgPublicada.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        */
    }

    public void mostrarVentana(View v){
        TextView cerrar;

        //popup
        publicacion.setContentView(R.layout.popup_subir_comunidad);
        cerrar = (TextView) publicacion.findViewById(R.id.txtCerrar);
        botonPublicar = publicacion.findViewById(R.id.botonPublicar);
        tituloPublicacion = publicacion.findViewById(R.id.tituloPublicacion);
        txtContenido = publicacion.findViewById(R.id.txtContenido);
        img = publicacion.findViewById(R.id.img);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publicacion.dismiss();
            }
        });

        publicacion.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        publicacion.show();
    }

    @SuppressLint("IntentReset")
    public void cargarImagen(View view) {
        @SuppressLint("IntentReset") Intent cargar = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cargar.setType("image/");
        startActivityForResult(Intent.createChooser(cargar,"Seleccionar aplicacion"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){

            path = data.getData();
            img.setImageURI(path);
        }
    }

    public void publicar(View view) {
        String contenido = txtContenido.getText().toString();
        String titulo = tituloPublicacion.getText().toString();
        publicacionComunidad pc = new publicacionComunidad();
        pc.setId(String.valueOf(codigoPublicaciones()));
        pc.setTitulo(titulo);
        pc.setContenido(contenido);

        StorageReference filePath = mstorage.child(pc.getId()).child(path.getLastPathSegment());
        pc.setImgId(filePath.getName());

        databaseReference.child(usuarioActual.getUid().toString()).child(pc.getId()).setValue(pc);
        Log.w("hola", filePath.getName());

        filePath.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ComunityActivity.this,"Foto subida",Toast.LENGTH_SHORT).show();
            }
        });

        publicacion.dismiss();
    }

    public String codigoPublicaciones(){

        Random aleatorio = new Random();
        String alfa = "ABCDEFGHIJKLMNOPQRSTVWXYZ";
        String cadena = "";
        int numero;
        int forma;

        forma=(int)(aleatorio.nextDouble() * alfa.length()-1+0);
        numero=(int)(aleatorio.nextDouble() * 99+100);
        cadena=cadena+alfa.charAt(forma)+numero;

        return cadena;
    }
}