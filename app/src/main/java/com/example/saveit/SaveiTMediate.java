package com.example.saveit;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class SaveiTMediate extends Application {
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeData(getApplicationContext());
    }

    public void initializeData(Context context) {
        Log.d("TAG", "started initialize");
        appContext = context;
    }

}
