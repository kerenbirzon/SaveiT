package com.example.saveit.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CategoryModel {

    public final static CategoryModel instance = new CategoryModel();
    Executor executor = Executors.newFixedThreadPool(1);
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());
    List<Category> categories = new LinkedList<Category>();

    private CategoryModel(){
        Category category = new Category();
        category.setTitle("Car");
        categories.add(category);
    }

    public interface GetAllCategoriesListener{
        void onComplete(List<Category> list);
    }

    public void getCategories(GetAllCategoriesListener listener) {
        executor.execute(()->{
            List<Category> categoriesList = AppLocalDb.db.categoryDao().getAllCategories();
            mainThread.post(()->{
                listener.onComplete(categoriesList);
            });
        });
    }

    public void addCategory(Category category){
        AppLocalDb.db.categoryDao().insertAll(category);
    }


    // todo
    public Category getCategoryByTitle(String title) {
        for(Category c:categories){
            if(c.getTitle().equals(title))
                return c;
        }
        return null;
    }
}
