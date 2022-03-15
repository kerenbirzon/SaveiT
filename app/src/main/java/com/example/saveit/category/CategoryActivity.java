package com.example.saveit.category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saveit.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private ArrayList<Document> documentList;
    private String categoryTitle;
    private ImageView docImg;
    private TextView noDocTxt;
    private RecyclerView recyclerView;
    private DocumentAdapter documentAdapter;

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

        categoryTitle = mainIntent.getStringExtra("category_name");
        TextView titleTxt = findViewById(R.id.tv_category_title);
        titleTxt.setText(categoryTitle);
        docImg = findViewById(R.id.iv_doc_img);
        noDocTxt = findViewById(R.id.tv_no_docs);
        if (documentList.size() > 0) {
            docImg.setVisibility(View.GONE);
            noDocTxt.setVisibility(View.GONE);
        }


    }

    private void initializeRecyclerView() {
        //set recycler view and document adapter
        recyclerView = findViewById(R.id.document_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        documentAdapter = new DocumentAdapter(documentList);
        recyclerView.setAdapter(documentAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                ExtendedFloatingActionButton categoryFab = findViewById(R.id.btn_add_doc);
                if (dy > 0) {
                    categoryFab.shrink();
                } else if (dy < 0) {
                    categoryFab.extend();
                }
            }
        });
    }
    private void initializeDocumentClickListener() {
    }
    private void initializeDocumentLongClickListener() {
    }
}