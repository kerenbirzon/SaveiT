package com.example.saveit.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference categoriesRef;
    private static final String TAG = "ModelFirebase";
    private static StorageReference storageReference;
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private static DocumentReference userDocumentRef;
    private static CollectionReference usersCollectionRef;
    private static Context appContext;
    private static ArrayList<Category> categories = new ArrayList<>();



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

    public interface GetAllCategoriesListener{
        void onComplete(List<Category> list);
    }

    public void getCategories(Long lastUpdateDate, GetAllCategoriesListener listener) {
        db.collection(Category.COLLECTION_NAME)
                .whereGreaterThanOrEqualTo("updateDate", new Timestamp(lastUpdateDate, 0))
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
        db.collection(Category.COLLECTION_NAME)
                .document(category.getId())
                .set(json)
                .addOnSuccessListener(unused -> listener.OnComplete())
                .addOnFailureListener(e -> listener.OnComplete());
    }

    public void editCategory(Category category, CategoryModel.AddCategoryListener listener) {
        db.collection(Category.COLLECTION_NAME)
                .document(category.getId())
                .update(category.toEditJson()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                new Thread(() -> AppLocalDb.db.categoryDao().update(category.getCategoryTitle(),category.getDocumentTitle(),category.getDocumentType(),category.getDocumentComments() ,category.getImageUrl(), category.getId())).start();
                listener.OnComplete();
            }
        });
    }

    public void saveImage(Bitmap imageBitmap, String imageName, CategoryModel.SaveImageListener listener) {
        StorageReference storageRef = storage.getReference();
        StorageReference imgRef = storageRef.child("user_avatars/" + imageName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imgRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> listener.OnComplete(null))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imgRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            Uri downloadUrl = uri;
                            listener.OnComplete(downloadUrl.toString());
                        });
                    }
                });
    }

    public void getCategoryById(String id, CategoryModel.GetCategoryById listener) {
        db.collection(Category.COLLECTION_NAME)
                .document(id)
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

    public void updateCategory(Category category, CategoryModel.UpdateCategoryListener lis) {
        Map<String, Object> jsonReview = category.toJson();
        db.collection(Category.COLLECTION_NAME)
                .document(category.getId())
                .update(jsonReview)
                .addOnSuccessListener(unused -> lis.OnComplete())
                .addOnFailureListener(e -> lis.OnComplete());
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
