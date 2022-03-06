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
    List<Category> categories = new LinkedList<Category>();

    ModelFirebase modelFirebase = new ModelFirebase();
    private CategoryModel(){
        Category category = new Category();
        category.setTitle("Car");
        categories.add(category);
    }

    public interface GetAllCategoriesListener{
        void onComplete(List<Category> list);
    }

    public void getCategories(GetAllCategoriesListener listener) {
        modelFirebase.getCategories(listener);
//        executor.execute(()->{
//            List<Category> categoriesList = AppLocalDb.db.categoryDao().getAllCategories();
//            mainThread.post(()->{
//                listener.onComplete(categoriesList);
//            });
//        });
    }

//    public List<Category> getCategories() {
//        categories = AppLocalDb.db.categoryDao().getAllCategories();
//        return categories;
//    }

    public interface AddCategoryListener {
        void OnComplete();
    }
    public void addCategory(Category category, AddCategoryListener listener){
        modelFirebase.addCategory(category, listener);
//        executor.execute(()->{
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            AppLocalDb.db.categoryDao().insertAll(category);
//            mainThread.post(()->{
//                listener.OnComplete();
//            });
//        });
    }

//    public Category getCategoryByTitle(String title){
//        modelFirebase.getCategoryByTitle(title);
//        return null;
//    }

    public interface GetCategoryByTitle {
        void OnComplete(Category category);
    }

    public Category getCategoryByTitle(String title, GetCategoryByTitle listener) {
        modelFirebase.getCategoryByTitle(title,listener);

//        for(Category c:categories){
//            if(c.getTitle().equals(title))
//                return c;
//        }
        return null;
    }
}
