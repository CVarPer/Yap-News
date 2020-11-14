package com.example.yap_news;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Adaptador extends BaseAdapter {
    private final Context context;
    private final pila adaptadorPublicaciones;
    private StorageReference mstorage;

    FirebaseUser usuarioActual;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public Adaptador(Context context, pila adaptadorPublicaciones){
        this.context = context;
        this.adaptadorPublicaciones = adaptadorPublicaciones;
    }

    @Override
    public int getCount() {
        return (adaptadorPublicaciones.size()/4);
    }

    @Override
    public Object getItem(int position) {
        return adaptadorPublicaciones.pop();
    }

    @Override
    public long getItemId(int poition){
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        mstorage = FirebaseStorage.getInstance().getReference();
        convertView = LayoutInflater.from(context).inflate(R.layout.item_noticias_comunidad, null);

        final ImageView imgPublicacion = convertView.findViewById(R.id.imgPublicada);
        TextView tituloPublicacion = convertView.findViewById(R.id.tituloPublicado);
        TextView contenidoPublicacion = convertView.findViewById(R.id.txtContenidoPublicado);

        final String[] titulo = new String[1];
        final String[] contenido = new String[1];
        String ss;

        adaptadorPublicaciones.pop();
        adaptadorPublicaciones.pop();
        /*
        StorageReference islandRef = mstorage.child(adaptadorPublicaciones.pop()).child(adaptadorPublicaciones.pop());
        Log.w("lhlhlh",String.valueOf(islandRef.getDownloadUrl()));
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.w("ss",uri.toString());
                Picasso.get().load(uri).into(imgPublicacion);
            }
        });
        */
        tituloPublicacion.setText(adaptadorPublicaciones.pop());
        contenidoPublicacion.setText(adaptadorPublicaciones.pop());
        return convertView;
    }

}

