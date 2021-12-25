package com.example.saveit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * a class for the welcome activity - activity before the app starts
 */
public class WelcomeActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 3000;
    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "WelcomeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**FacebookSdk.sdkInitialize(this); */
        setContentView(R.layout.activity_welcome);

    }
}