package com.example.saveit.model;

import android.content.Context;
import android.graphics.Bitmap;
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
                .whereEqualTo("deleted", false)
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

    public void updateCategory(Category category, CategoryModel.UpdateCategoryListener lis) {
        Map<String, Object> jsonReview = category.toJson();
        db.collection("categories")
                .document(category.getTitle())
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


    /**
     * Storage
     */
//    public static void uploadImageToFirebaseStorageDB(Bitmap bitmap, Context context, String categoryTitle, String documentTitle, String imageType) {
//        // Get the data from an ImageView as bytes
//        StorageReference ref = storageReference.child("Files").
//                child(MyPreferences.getUserDocumentPathFromPreferences(context)).child(categoryTitle).child(documentTitle).child("image");
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
//        byte[] data = baos.toByteArray();
//
//        UploadTask uploadTask = ref.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Log.e(TAG, "filed to upload the document image to the storage DB");
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Log.d(TAG, "successfully uploaded the document image to the storage DB");
//            }
//        });
//    }

}
