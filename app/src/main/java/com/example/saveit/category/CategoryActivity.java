package com.example.saveit.category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saveit.R;
import com.example.saveit.document.DocumentActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    public static final int NEW_DOCUMENT = 111;
    public static final int EDIT_DOCUMENT = 222;
    public static final int DEFAULT_POSITION_VALUE = -1;
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
        // click listener - to go inside a document
        documentAdapter.setDocumentClickListener(new DocumentClickListener() {
            @Override
            public void onDocumentClicked(int position) {
                Log.d("document clicked", "document was clicked");
                Document document = documentList.get(position);
                Intent intent = new Intent(CategoryActivity.this, DocumentActivity.class);
                intent.putExtra("call_reason", "edit_document");
                intent.putExtra("position", position);
                intent.putExtra("document_title", document.getTitle());
                intent.putExtra("category_title", categoryTitle);
                intent.putExtra("document_comment", document.getComment());
                intent.putExtra("document_expiration_date", document.getExpirationDate());
                intent.putExtra("document_reminder_time", document.getReminderTime());
                intent.putExtra("has_image", document.getHasImage());
                intent.putExtra("has_file", document.isHasFile());
                intent.putExtra("file_download_uri", document.getFileDownloadUri());
                intent.putExtra("has_alarm", document.getHasAlarm());
                intent.putExtra("is_add_event_to_phone_calender", document.getIsAddEventToPhoneCalender());
                startActivityForResult(intent, EDIT_DOCUMENT);
            }
        });
    }
    private void initializeDocumentLongClickListener() {
    }
}