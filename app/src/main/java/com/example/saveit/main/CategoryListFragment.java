package com.example.saveit.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.saveit.R;
import com.example.saveit.model.Category;
import com.example.saveit.model.CategoryModel;

import java.util.List;

public class CategoryListFragment extends Fragment {
    CategoryListViewModel viewModel;
    private int lastCategoryPosition;
    CategoryAdapter categoryAdapter;
    SwipeRefreshLayout swipeRefresh;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(CategoryListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        Button addCategoryBtn = view.findViewById(R.id.btn_add_category);
        addCategoryBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_categoryList_to_addCategory));


        //categories = CategoryModel.instance.getCategories();

        swipeRefresh = view.findViewById(R.id.category_swipeRefresh);
        swipeRefresh.setOnRefreshListener(() -> CategoryModel.instance.refreshCategoryList());

        RecyclerView recyclerView = view.findViewById(R.id.category_recycler);
        recyclerView.hasFixedSize();

        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),2));

        categoryAdapter = new CategoryAdapter(viewModel.getCategories().getValue());
        recyclerView.setAdapter(categoryAdapter);

        categoryAdapter.setCategoryClickListener(new CategoryClickListener() {
            @Override
            public void onCategoryClicked(int position) {
                Log.d("category clicked", "category was clicked");
                lastCategoryPosition = position;
                Category category = viewModel.getCategories().getValue().get(position);
                Navigation.findNavController(view).navigate(CategoryListFragmentDirections.actionCategoryListToCategory(category.getTitle()));
            }
        });

        categoryAdapter.setCategoryLongClickListener(new CategoryLongClickListener() {
            @Override
            public void onCategoryLongClicked(int position) {
                Log.d("category long clicked", "category was long clicked");
            }
        });

        viewModel.getCategories().observe(getViewLifecycleOwner(), categories -> refresh());
        swipeRefresh.setRefreshing(CategoryModel.instance.getCategoryListLoadingState().getValue() == CategoryModel.CategoryListLoadingState.loading);
        CategoryModel.instance.getCategoryListLoadingState().observe(getViewLifecycleOwner(), categoryListLoadingState -> {
            if (categoryListLoadingState == CategoryModel.CategoryListLoadingState.loading){
                swipeRefresh.setRefreshing(true);
            }else{
                swipeRefresh.setRefreshing(false);
            }
        });
        return view;
    }

    private void refresh() {
            categoryAdapter.notifyDataSetChanged();
            swipeRefresh.setRefreshing(false);
    }
}