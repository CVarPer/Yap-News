package com.example.yap_news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adapterNoticias extends RecyclerView.Adapter<adapterNoticias.MyViewHolder> {

    List<Noticias> mStack = new ArrayList<>();
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nombreMedio, autor, fecha, hora;
        public TextView richLinkViewTelegram;
        public MyViewHolder(View v) {
            super(v);
            nombreMedio = v.findViewById(R.id.nombre_medio);
            autor = v.findViewById(R.id.autor);
            fecha = v.findViewById(R.id.fecha);
            hora = v.findViewById(R.id.hora);
            richLinkViewTelegram = v.findViewById(R.id.preview_Noticia);
        }
    }

    public adapterNoticias(List<Noticias> mStack){
        this.mStack = mStack;
    }
    public void addNoticia(Noticias noticias){
        mStack.add(noticias);
        notifyItemInserted(mStack.size());
        notifyDataSetChanged();
    }
    public void removeUrl(){
        mStack.remove(mStack.get(mStack.size()));
        notifyItemRemoved(mStack.size());
    }

    //https://stackoverflow.com/questions/28573685/add-items-to-a-recycler-view
    @NonNull
    @Override
    public adapterNoticias.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticias, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull adapterNoticias.MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        Noticias noticias = mStack.get(position);
        // - replace the contents of the view with that element
        holder.nombreMedio.setText(noticias.getNombre());
        holder.autor.setText(noticias.getAutor());
        holder.fecha.setText(noticias.getFecha());
        holder.hora.setText(noticias.getHora());
        holder.richLinkViewTelegram.setText(noticias.getNewsUrl());
    }
    @Override
    public int getItemCount() {
        return mStack.size();
    }

}
//https://developer.android.com/guide/topics/ui/layout/recyclerview#add-support-library

/*public adapterNoticias(@NonNull Context context, int resourceLayout, List<modeloNoticias> mList){
        super(context, resourceLayout, mList);
        this.mContext = context;
        this.mList = mList;
        this.resourceLayout = resourceLayout;
    }*/

    /*@NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
           view = LayoutInflater.from(mContext).inflate(resourceLayout, null);
        modeloNoticias modeloNoticias = mList.get(position);

        WebView webView = view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(modeloNoticias.getNewsUrl().toString());
        return view;
    }*/