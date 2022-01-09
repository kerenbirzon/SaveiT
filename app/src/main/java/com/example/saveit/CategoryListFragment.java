package com.example.saveit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saveit.model.Category;
import com.example.saveit.model.Model;

import java.util.ArrayList;

public class CategoryListFragment extends Fragment {
    private ArrayList<Category> categories;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        Button addCategoryBtn = view.findViewById(R.id.btn_add_category);
        addCategoryBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_categoryList_to_addCategory));
        categories = Model.instance.getCategories();

        RecyclerView recyclerView = view.findViewById(R.id.category_recycler);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
        recyclerView.setAdapter(categoryAdapter);


        return view;
    }
}