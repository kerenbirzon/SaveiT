package com.example.saveit.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saveit.R;
import com.example.saveit.model.CategoryModel;
import com.example.saveit.model.Document;
import com.example.saveit.model.DocumentModel;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    private ArrayList<Document> documentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);
        Button addDocumentBtn = view.findViewById(R.id.btn_add_doc);
        addDocumentBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_category_to_document));
        documentList = DocumentModel.instance.getDocuments();

        RecyclerView recyclerView = view.findViewById(R.id.document_recycler);
        recyclerView.hasFixedSize();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DocumentAdapter documentAdapter = new DocumentAdapter(documentList);
        recyclerView.setAdapter(documentAdapter);




        return view;
    }
}