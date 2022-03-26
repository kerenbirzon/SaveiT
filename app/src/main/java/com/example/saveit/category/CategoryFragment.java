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
import com.squareup.picasso.Picasso;
//import com.example.saveit.model.Document;

import java.util.List;

public class CategoryFragment extends Fragment {

    private TextView titleTxt, documentName, documentType, documentComments;
    private String categoryTitle;
    ImageView categoryImage;
    NavController navController;
    Category category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);

        categoryTitle = CategoryFragmentArgs.fromBundle(getArguments()).getCategory().getCategoryTitle();


        navController = NavHostFragment.findNavController(this);
        titleTxt = view.findViewById(R.id.tv_category_title);
        titleTxt.setText(categoryTitle);

        category = CategoryFragmentArgs.fromBundle(getArguments()).getCategory();

        documentName = view.findViewById(R.id.document_name_et);
        documentName.setText(category.getDocumentTitle());
        documentType = view.findViewById(R.id.document_type_et);
        documentType.setText(category.getDocumentType());
        documentComments = view.findViewById(R.id.document_comment_et);
        documentComments.setText(category.getDocumentComments());
        categoryImage = view.findViewById(R.id.categoryItemImage);
        if (category.getImageUrl() != null && !category.getImageUrl().isEmpty()) {
            Picasso.get()
                    .load(category.getImageUrl())
                    .into(categoryImage);
        }

        documentComments.setEnabled(false);
        documentType.setEnabled(false);
        documentName.setEnabled(false);

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
                Category category = CategoryFragmentArgs.fromBundle(getArguments()).getCategory();
                CategoryModel.instance.deleteCategory(category, () -> navController.navigateUp());
                break;
            case R.id.category_edit:
                Category editCategory = CategoryFragmentArgs.fromBundle(getArguments()).getCategory();
                navController.navigate(CategoryFragmentDirections.actionCategoryFragmentToAddCategoryFragment(editCategory));
                break;
        }
        return true;
    }
}