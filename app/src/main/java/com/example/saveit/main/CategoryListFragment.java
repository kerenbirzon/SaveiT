package com.example.saveit.main;

import android.content.Context;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.saveit.R;
import com.example.saveit.login.LoginActivity;
import com.example.saveit.model.Category;
import com.example.saveit.model.CategoryModel;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class CategoryListFragment extends Fragment {
    CategoryListViewModel viewModel;
    private int lastCategoryPosition;
    CategoryAdapter categoryAdapter;
    SwipeRefreshLayout swipeRefresh;
    FirebaseAuth categoryListmAuth;
    Button addCategoryBtn;

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
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(CategoryListFragmentDirections.actionCategoryListToAddCategory());
            }
        });
        addCategoryBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_categoryList_to_addCategory));

        categoryListmAuth = FirebaseAuth.getInstance();

        swipeRefresh = view.findViewById(R.id.category_swipeRefresh);
        swipeRefresh.setOnRefreshListener(() -> CategoryModel.instance.refreshCategoryList());

        RecyclerView recyclerView = view.findViewById(R.id.category_recycler);
        recyclerView.hasFixedSize();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        categoryAdapter = new CategoryAdapter();
        recyclerView.setAdapter(categoryAdapter);


        categoryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v,int position) {
                Log.d("category clicked", "category was clicked");
                Category category = viewModel.getCategories().getValue().get(position);
                Navigation.findNavController(v).navigate(CategoryListFragmentDirections.actionCategoryListToCategory(category));
            }
        });

        setHasOptionsMenu(true);

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
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTitle;
        ImageView image;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.category_title);
            image = itemView.findViewById(R.id.category_img);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(v,pos);
                }
            });
        }

        void bind(Category category){
            categoryTitle.setText(category.getCategoryTitle());
            image.setImageResource(R.drawable.save_it);
            if (category.getImageUrl() != null && !category.getImageUrl().isEmpty()) {
                Picasso.get()
                        .load(category.getImageUrl())
                        .into(image);
            }
        }
    }

    interface OnItemClickListener{
        void onItemClick(View v,int position);
    }
    class CategoryAdapter extends RecyclerView.Adapter<MyViewHolder>{

        OnItemClickListener listener;
        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.category_item,parent,false);
            MyViewHolder holder = new MyViewHolder(view,listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Category category = viewModel.getCategories().getValue().get(position);
            holder.bind(category);
        }

        @Override
        public int getItemCount() {
            if(viewModel.getCategories().getValue() == null){
                return 0;
            }
            return viewModel.getCategories().getValue().size();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.category_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.my_categories:
                break;
            case R.id.category_logout:
                if (categoryListmAuth != null){
                    categoryListmAuth.signOut();
                    startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                }
                break;
        }
        return true;
    }

}