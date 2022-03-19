package com.example.saveit.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.saveit.SaveiTMediate;

import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CategoryModel {

    public final static CategoryModel instance = new CategoryModel();
    public Executor executor = Executors.newFixedThreadPool(1);
    public Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());
    List<com.example.saveit.model.Category> categories = new LinkedList<com.example.saveit.model.Category>();

    public enum CategoryListLoadingState{
        loading,
        loaded
    }

    MutableLiveData<CategoryListLoadingState> categoryListLoadingState = new MutableLiveData<>();
    public LiveData<CategoryListLoadingState> getCategoryListLoadingState() {
        return categoryListLoadingState;
    }

    ModelFirebase modelFirebase = new ModelFirebase();
    private CategoryModel(){
        categoryListLoadingState.setValue(CategoryListLoadingState.loaded);
    }

    MutableLiveData<List<com.example.saveit.model.Category>> categoryList = new MutableLiveData<List<com.example.saveit.model.Category>>();
    public LiveData<List<com.example.saveit.model.Category>> getAllCategories(){
        if (categoryList.getValue() == null){
            refreshCategoryList();
        }
        return categoryList;
    }
    public void refreshCategoryList(){
        categoryListLoadingState.setValue(CategoryListLoadingState.loading);

        // get local update date
        Long lastUpdateDate = SaveiTMediate.getAppContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("CategoryLastUpdateDate",0);

        executor.execute(() -> {
            List<com.example.saveit.model.Category> catList = AppLocalDb.db.categoryDao().getAllCategories();
            categoryList.postValue(catList);
        });

        //firebase get all updates
        modelFirebase.getCategories(lastUpdateDate, new ModelFirebase.GetAllCategoriesListener() {
            @Override
            public void onComplete(List<com.example.saveit.model.Category> list){
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Long lastUpdateDate = new Long(0);
                        Log.d("TAG","fireBase returned: " + list.size());
                        for (com.example.saveit.model.Category category: list){
                            AppLocalDb.db.categoryDao().insertAll(category);

                            if (lastUpdateDate < category.getUpdateDate()){
                                lastUpdateDate = category.getUpdateDate();
                            }
                        }

                        //update last local update date
                        SaveiTMediate.getAppContext().getSharedPreferences("TAG",Context.MODE_PRIVATE)
                                .edit()
                                .putLong("CategoryLastUpdateDate",lastUpdateDate)
                                .commit();

                        //return all data to caller
                        List<com.example.saveit.model.Category> caList = AppLocalDb.db.categoryDao().getAllCategories();
                        categoryList.postValue(caList);
                        categoryListLoadingState.postValue(CategoryListLoadingState.loaded);
                    }
                });
            }
        });
    }


    public interface AddCategoryListener {
        void OnComplete();
    }
    public void addCategory(com.example.saveit.model.Category category, AddCategoryListener listener){
        modelFirebase.addCategory(category, () -> {
            listener.OnComplete();
            refreshCategoryList();
        });
    }

    public interface GetCategoryByTitle {
        void OnComplete(com.example.saveit.model.Category category);
    }

    public com.example.saveit.model.Category getCategoryByTitle(String title, GetCategoryByTitle listener) {
        modelFirebase.getCategoryByTitle(title,listener);
        return null;
    }

    /**
     * Authentication
     */

    public boolean isSignedIn() {
        return modelFirebase.isSignedIn();
    }
}
