package com.example.saveit.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.saveit.model.Category;
import com.example.saveit.model.CategoryModel;

import java.util.List;

public class CategoryListViewModel extends ViewModel {
    LiveData<List<Category>> categories;

    public CategoryListViewModel(){
        categories = CategoryModel.instance.getAllCategories();
    }
    public LiveData<List<Category>> getCategories() {
        return categories;
    }
}
