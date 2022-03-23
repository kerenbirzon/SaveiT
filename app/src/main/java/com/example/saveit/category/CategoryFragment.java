package com.example.saveit.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saveit.MainActivity;
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
    Button addDocumentBtn;//deleteCategoryBtn;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);

        categoryTitle = CategoryFragmentArgs.fromBundle(getArguments()).getCategory().getTitle();

        CategoryModel.instance.getCategoryByTitle(categoryTitle, new CategoryModel.GetCategoryByTitle() {
            @Override
            public void OnComplete(Category category) {
                titleTxt.setText(categoryTitle);
            }
        });

        navController = NavHostFragment.findNavController(this);
        titleTxt = view.findViewById(R.id.tv_category_title);
        docImg = view.findViewById(R.id.iv_doc_img);
        noDocTxt = view.findViewById(R.id.tv_no_docs);

        addDocumentBtn = view.findViewById(R.id.btn_add_doc);
        addDocumentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigation.createNavigateOnClickListener(R.id.action_category_to_document);
            }
        });
        //documentList = DocumentModel.instance.getDocuments(categoryTitle);

//        deleteCategoryBtn = view.findViewById(R.id.btn_delete_category);
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

//        setDeleteButtonOnClickListener();
        setHasOptionsMenu(true);

        return view;
    }

//    private void setDeleteButtonOnClickListener() {
//        deleteCategoryBtn.setOnClickListener(view -> {
//            addDocumentBtn.setEnabled(false);
//            deleteCategoryBtn.setEnabled(false);
//            new Thread(() -> {
//                Category category = CategoryFragmentArgs.fromBundle(getArguments()).getCategory();
//                Log.d("TAG","TAMIRTAMIRTAMIR - " + category.getTitle());
//                AppLocalDb.db.categoryDao().delete(category);
//                CategoryModel.instance.deleteCategory(category, () -> Navigation.findNavController(view).navigateUp());
//            }).start();
//        });
//    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.category_list_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.category_delete:
                addDocumentBtn.setEnabled(false);
                new Thread(() -> {
                    Category category = CategoryFragmentArgs.fromBundle(getArguments()).getCategory();
                    Log.d("TAG","TAMIRTAMIRTAMIR - " + category.getTitle());
                    AppLocalDb.db.categoryDao().delete(category);
                    CategoryModel.instance.deleteCategory(category, () -> navController.navigateUp());
                }).start();
                break;
            case R.id.category_edit:
                navController.navigate(R.id.action_categoryFragment_to_editCategoryFragment);
                break;
        }
        return true;
    }
}