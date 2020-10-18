package com.example.yap_news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.net.URL;

public class adapterNoticias extends RecyclerView.Adapter<adapterNoticias.MyViewHolder> {

    private StackList<String> stackNoticias;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public WebView webView;
        public MyViewHolder(View v) {
            super(v);
            webView = (WebView) v.findViewById(R.id.webView);
        }
    }

    public adapterNoticias(StackList<String> mStack){
        stackNoticias = mStack;
    }

    @NonNull
    @Override
    public adapterNoticias.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticias, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull adapterNoticias.MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.webView.loadUrl( (stackNoticias.getStackListArray())[position] );
    }
    @Override
    public int getItemCount() {
        return stackNoticias.getTop();
    }
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
}


//https://developer.android.com/guide/topics/ui/layout/recyclerview#add-support-library