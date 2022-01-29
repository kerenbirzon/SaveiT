package com.example.saveit.model;

import com.google.firebase.firestore.FirebaseFirestore;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void addCategory(Category category, CategoryModel.addCategoryListener listener) {

    }

    public void getCategories(CategoryModel.getCategoryListener listener) {

    }

    public void getCategoryByTitle(String title) {

    }
}
