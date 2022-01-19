package com.example.saveit.model;

import java.util.ArrayList;

public class CategoryModel {

    public final static CategoryModel instance = new CategoryModel();

    private CategoryModel(){
        Category category = new Category();
        category.setTitle("Car");
        categories.add(category);
    }

    ArrayList<Category> categories = new ArrayList<Category>();

    public ArrayList<Category> getCategories() {
        categories = AppLocalDb.db.categoryDao().getAllCategories();
        return categories;
    }

    public void addCategory(Category category){
        AppLocalDb.db.categoryDao().insertAll();
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
