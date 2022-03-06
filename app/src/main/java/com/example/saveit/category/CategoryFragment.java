package com.example.saveit.category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saveit.R;
import com.example.saveit.main.CategoryListFragmentDirections;
import com.example.saveit.model.Category;
import com.example.saveit.model.CategoryModel;
import com.example.saveit.model.Document;
import com.example.saveit.model.DocumentModel;

import java.util.List;

public class CategoryFragment extends Fragment {

    private List<Document> documentList;
    private ImageView docImg;
    private TextView noDocTxt, titleTxt;
    private String categoryTitle;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);

        categoryTitle = CategoryFragmentArgs.fromBundle(getArguments()).getTitle();

        CategoryModel.instance.getCategoryByTitle(categoryTitle, new CategoryModel.GetCategoryByTitle() {
            @Override
            public void OnComplete(Category category) {
                titleTxt.setText(categoryTitle);
            }
        });

        titleTxt = view.findViewById(R.id.tv_category_title);
        docImg = view.findViewById(R.id.iv_doc_img);
        noDocTxt = view.findViewById(R.id.tv_no_docs);



        Button addDocumentBtn = view.findViewById(R.id.btn_add_doc);
        addDocumentBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_category_to_document));
        documentList = DocumentModel.instance.getDocuments(categoryTitle);

        RecyclerView recyclerView = view.findViewById(R.id.document_recycler);
        recyclerView.hasFixedSize();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DocumentAdapter documentAdapter = new DocumentAdapter(documentList);
        recyclerView.setAdapter(documentAdapter);

        documentAdapter.setDocumentLongClickListener(new DocumentLongClickListener() {
            @Override
            public void onDocumentLongClicked(int position) {
                Log.d("document long clicked", "document was long clicked");
            }
        });

        documentAdapter.setDocumentClickListener(new DocumentClickListener() {
            @Override
            public void onDocumentClicked(int position) {
                Log.d("document clicked", "document was clicked");
            }
        });


        return view;
    }
}