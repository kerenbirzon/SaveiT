package com.example.saveit.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saveit.R;
import com.example.saveit.model.Category;
import com.example.saveit.model.CategoryModel;

import java.util.List;

public class CategoryListFragment extends Fragment {
    List<Category> categories;
    private int lastCategoryPosition;
    CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        Button addCategoryBtn = view.findViewById(R.id.btn_add_category);
        addCategoryBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_categoryList_to_addCategory));

        //categories = CategoryModel.instance.getCategories();

        RecyclerView recyclerView = view.findViewById(R.id.category_recycler);
        recyclerView.hasFixedSize();

        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),2));

        categoryAdapter = new CategoryAdapter(categories);
        recyclerView.setAdapter(categoryAdapter);

        categoryAdapter.setCategoryClickListener(new CategoryClickListener() {
            @Override
            public void onCategoryClicked(int position) {
                Log.d("category clicked", "category was clicked");
                lastCategoryPosition = position;
                Category category = categories.get(position);
                Navigation.findNavController(view).navigate(CategoryListFragmentDirections.actionCategoryListToCategory(category.getTitle()));
            }
        });

        categoryAdapter.setCategoryLongClickListener(new CategoryLongClickListener() {
            @Override
            public void onCategoryLongClicked(int position) {
                Log.d("category long clicked", "category was long clicked");
            }
        });
        refresh();
        return view;
    }

    private void refresh() {
        CategoryModel.instance.getCategories((list)->{
            categories = list;
            categoryAdapter.notifyDataSetChanged();
        });
    }
}