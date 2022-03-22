package com.example.saveit;

import android.app.Application;
import android.content.Context;
import android.util.Log;


import java.util.ArrayList;

public class SaveiTMediate extends Application {
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        SaveiTMediate.appContext = getApplicationContext();
    }

    public static Context getAppContext() { return appContext; }

}
