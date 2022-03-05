package com.example.saveit.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void getCategories(CategoryModel.GetAllCategoriesListener listener) {

    }

    public void addCategory(Category category, CategoryModel.AddCategoryListener listener) {
        Map<String, Object> json = category.toJson();

        // Add a new document with a generated ID
        db.collection("categories")
                .document(category.getTitle())
                .set(json)
                .addOnSuccessListener(unused -> listener.OnComplete())
                .addOnFailureListener(e -> listener.OnComplete());
    }

//    public void addCategory(Category category, CategoryModel.addCategoryListener listener) {
//
//    }
//
//    public void getCategories(CategoryModel.getCategoryListener listener) {
//
//    }
//
//    public void getCategoryByTitle(String title) {
//
//    }
}
