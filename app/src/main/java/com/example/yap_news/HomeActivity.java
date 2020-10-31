package com.example.yap_news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private adapterNoticias adapter;
    private RecyclerView.LayoutManager layoutManager;
    private StackList<String> Stack = new StackList<>();
    private TextView textView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        final EditText insertUrl = findViewById(R.id.InsertUrl);
        Button button = findViewById(R.id.uploadUrl);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new adapterNoticias(Stack);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
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
                });
    }
}

  //Documentation: https://developer.android.com/guide/topics/ui/layout/recyclerview#add-support-library