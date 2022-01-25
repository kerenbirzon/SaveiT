package com.example.saveit.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CategoryModel {

    public final static CategoryModel instance = new CategoryModel();
    Executor executor = Executors.newFixedThreadPool(1);
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    ModelFirebase modelFirebase = new ModelFirebase();
    private CategoryModel(){
        Category category = new Category();
        category.setTitle("Car");
        categories.add(category);
    }

    List<Category> categories = new LinkedList<Category>();

    public interface getCategoryListener {
        void onComplete(List<Category> list);
    }

    public void getCategories(getCategoryListener listener){
        modelFirebase.getCategories(listener);
    }

//    public List<Category> getCategories() {
//        categories = AppLocalDb.db.categoryDao().getAllCategories();
//        return categories;
//    }

    public interface addCategoryListener {
        void onComplete();
    }

    public void addCategory(Category category, addCategoryListener listener){
        modelFirebase.addCategory(category,listener);
    }
//    public void addCategory(Category category){
//        //AppLocalDb.db.categoryDao().insertAll(category);
//
//    }

    public Category getCategoryByTitle(String title){
        modelFirebase.getCategoryByTitle(title);
        return null;
    }
//    // todo
//    public Category getCategoryByTitle(String title) {
//        for(Category c:categories){
//            if(c.getTitle().equals(title))
//                return c;
//        }
//        return null;
//    }
}
