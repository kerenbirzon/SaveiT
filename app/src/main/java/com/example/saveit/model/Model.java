package com.example.saveit.model;

import android.graphics.ColorSpace;
import android.view.Display;

import java.util.ArrayList;

public class Model {

    public final static Model instance = new Model();

    private Model(){
        Category category = new Category();
        category.setTitle("Car");
        categories.add(category);
    }

    ArrayList<Category> categories = new ArrayList<Category>();

    public ArrayList<Category> getCategories() {
        return categories;
    }
}
