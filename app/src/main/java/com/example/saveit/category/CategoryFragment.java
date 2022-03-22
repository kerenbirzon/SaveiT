package com.example.saveit.category;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.saveit.document.DocumentActivity;
import com.example.saveit.model.AppLocalDb;
import com.example.saveit.model.Category;
import com.example.saveit.model.CategoryModel;
import com.example.saveit.model.Document;
//import com.example.saveit.model.Document;

import java.util.List;

public class CategoryFragment extends Fragment {

    private List<Document> documentList;
    private ImageView docImg;
    private TextView noDocTxt, titleTxt;
    private String categoryTitle;
    Button addDocumentBtn,deleteCategoryBtn;
    public static final int NEW_DOCUMENT = 111;


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
        if (documentList.size() > 0) {
            docImg.setVisibility(View.GONE);
            noDocTxt.setVisibility(View.GONE);
        }

        addDocumentBtn = view.findViewById(R.id.btn_add_doc);
        addDocumentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryFragment.this, DocumentActivity.class);
                intent.putExtra("call_reason", "new_document");
                intent.putExtra("category_title", categoryTitle);
                startActivityForResult(intent, NEW_DOCUMENT);
            }
        });
        //documentList = DocumentModel.instance.getDocuments(categoryTitle);


        deleteCategoryBtn = view.findViewById(R.id.btn_delete_category);
        deleteCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addDocumentBtn.setEnabled(false);
//                deleteCategoryBtn.setEnabled(false);
//                new Thread(() -> {
//                    Category category = CategoryFragmentArgs.fromBundle(getArguments()).getCategory();
//                    AppLocalDb.db.CategoryDao.delete(category);
//                    CategoryModel.instance.deleteCategory(dress, () -> navController.navigateUp());
//                }).start();
                Navigation.findNavController(view).navigateUp();
            }

        });
        RecyclerView recyclerView = view.findViewById(R.id.document_recycler);
        recyclerView.hasFixedSize();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //DocumentAdapter documentAdapter = new DocumentAdapter(documentList);
        //recyclerView.setAdapter(documentAdapter);

//        documentAdapter.setDocumentClickListener(new DocumentClickListener() {
//            @Override
//            public void onDocumentClicked(int position) {
//                Log.d("document clicked", "document was clicked");
//            }
//        });


        return view;
    }
}