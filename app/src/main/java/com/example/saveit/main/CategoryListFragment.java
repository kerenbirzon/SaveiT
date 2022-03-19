package com.example.saveit.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.saveit.MainActivity;
import com.example.saveit.R;
import com.example.saveit.login.LoginActivity;
import com.example.saveit.model.Category;
import com.example.saveit.model.CategoryModel;
import com.google.firebase.auth.FirebaseAuth;

public class CategoryListFragment extends Fragment {
    CategoryListViewModel viewModel;
    private int lastCategoryPosition;
    CategoryAdapter categoryAdapter;
    SwipeRefreshLayout swipeRefresh;
    FirebaseAuth categoryListmAuth;
    Button addCategoryBtn,signOutBtn;
    ImageView iconPrevView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(CategoryListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        addCategoryBtn = view.findViewById(R.id.btn_add_category);
        addCategoryBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_categoryList_to_addCategory));
        signOutBtn = view.findViewById(R.id.btn_sign_out);
        categoryListmAuth = FirebaseAuth.getInstance();
        iconPrevView = view.findViewById(R.id.iv_icon_img_prev);

        swipeRefresh = view.findViewById(R.id.category_swipeRefresh);
        swipeRefresh.setOnRefreshListener(() -> CategoryModel.instance.refreshCategoryList());

        RecyclerView recyclerView = view.findViewById(R.id.category_recycler);
        recyclerView.hasFixedSize();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        categoryAdapter = new CategoryAdapter(viewModel.getCategories().getValue());
        recyclerView.setAdapter(categoryAdapter);

        categoryAdapter.setCategoryClickListener(new CategoryClickListener() {
            @Override
            public void onCategoryClicked(View v,int position) {
                Log.d("category clicked", "category was clicked");
                lastCategoryPosition = position;
                Category category = viewModel.getCategories().getValue().get(position);
                //Navigation.findNavController(view).navigate(CategoryListFragmentDirections.actionCategoryListToCategory(category.getTitle()));
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

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categoryListmAuth != null){
                    categoryListmAuth.signOut();
                    startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                }
            }
        });
        return view;
    }

    private void refresh() {
        categoryAdapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }
}