package com.example.saveit.model;

import androidx.annotation.NonNull;

import com.example.saveit.main.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ModelFirebase(){
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }

    public void getCategories(Long lastUpdateDate, CategoryModel.GetAllCategoriesListener listener) {
        db.collection(Category.COLLECTION_NAME)
                .get()
                .addOnCompleteListener(task -> {
                    List<Category> list = new LinkedList<Category>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()){
                            Category category = Category.create(doc.getData());
                            if (category != null){
                                list.add(category);
                            }
                        }
                    }
                    listener.onComplete(list);
                });

    }

    public void addCategory(Category category, CategoryModel.AddCategoryListener listener) {
        Map<String, Object> json = category.toJson();

        // Add a new document with a generated ID
        db.collection(Category.COLLECTION_NAME)
                .document(category.getTitle())
                .set(json)
                .addOnSuccessListener(unused -> listener.OnComplete())
                .addOnFailureListener(e -> listener.OnComplete());
    }

    public void getCategoryByTitle(String title, CategoryModel.GetCategoryByTitle listener) {
        db.collection(Category.COLLECTION_NAME)
                .document(title)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Category category = null;
                        if (task.isSuccessful() & task.getResult()!= null){
                            category = Category.create(task.getResult().getData());
                        }
                        listener.OnComplete(category);
                    }
                });

    }

    /**
     * Authentication
     */

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public boolean isSignedIn(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return (currentUser != null);
    }
}
