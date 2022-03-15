package com.example.saveit.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.example.saveit.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private ArrayList<Document> documentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent mainIntent = getIntent();
        Gson gson = new Gson();
        String json = mainIntent.getStringExtra("docList");
        Type type = new TypeToken<ArrayList<Document>>() {
        }.getType();
        documentList = gson.fromJson(json, type);
        if (documentList == null) {
            documentList = new ArrayList<>();
        }

        // initializes the recycler view and the adapter
        initializeRecyclerView();

        // when a document is clicked
        initializeDocumentClickListener();

        // when a document is long clicked
        initializeDocumentLongClickListener();


    }

    private void initializeRecyclerView() {
    }
    private void initializeDocumentClickListener() {
    }
    private void initializeDocumentLongClickListener() {
    }
}