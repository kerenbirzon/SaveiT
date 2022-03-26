package com.example.saveit.category;

import static android.app.Activity.RESULT_OK;

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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saveit.R;
import com.example.saveit.model.AppLocalDb;
import com.example.saveit.model.Category;
import com.example.saveit.model.CategoryModel;
import com.example.saveit.model.ModelFirebase;
//import com.example.saveit.model.Document;

import java.util.List;

public class CategoryFragment extends Fragment {

    private ImageView docImg;
    private TextView noDocTxt, titleTxt;
    private String categoryTitle;
    Button addDocumentBtn;
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

        RecyclerView recyclerView = view.findViewById(R.id.document_recycler);
        recyclerView.hasFixedSize();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.category_list_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.category_delete:
                new Thread(() -> {
                    Category category = CategoryFragmentArgs.fromBundle(getArguments()).getCategory();
                    AppLocalDb.db.categoryDao().delete(category);
                    CategoryModel.instance.deleteCategory(category, () -> navController.navigateUp());
                }).start();
                break;
            case R.id.category_edit:
                //navController.navigate(R.id.action_categoryFragment_to_editCategoryFragment);
                //navController.navigate(CategoryFragmentDirections.actionCategoryFragmentToEditCategoryFragment(CategoryFragmentArgs.fromBundle(getArguments()).getCategory()));
                break;
        }
        return true;
    }
}