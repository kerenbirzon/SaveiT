package com.example.saveit.model;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.saveit.User.User;
import com.example.saveit.main.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.StorageReference;

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

    /**
     *
     * Category
     *
     */

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
     * User
     */

    public void createUser(User user, UserModel.AddUserListener listener) {
        Map<String, Object> json = user.toJson();
        db.collection("User")
                .document(user.getPhoneNumber())
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

    public void addUser(User user,UserModel.AddUserListener listener){
        db.collection("User")
                .document(user.getPhoneNumber())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("tag","user added successfully");
                        listener.onComplete();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("tag","failed added user");
                listener.onComplete();
            }
        });
    }

    public void getUserByPhone(String userPhoneNumber, UserModel.GetUserByPhone listener) {
        db.collection("Designer")
                .document(userPhoneNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        User user = null;
                        if (task.isSuccessful() & task.getResult()!= null){
                            user = User.create(task.getResult().getData());
                        }
                        listener.onComplete(user);
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
