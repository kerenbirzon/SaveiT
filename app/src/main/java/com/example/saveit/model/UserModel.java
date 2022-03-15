package com.example.saveit.model;

import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.saveit.User.User;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserModel {
    public static final UserModel instance = new UserModel();
    Executor executor = Executors.newFixedThreadPool(1);
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    public enum UserListLoadingState{
        loading,
        loaded
    }

    MutableLiveData<UserListLoadingState> userListLoadingState = new MutableLiveData<UserListLoadingState>();
    public LiveData<UserListLoadingState> getUserListLoadingState() {
        return userListLoadingState;
    }

    private UserModel(){
        userListLoadingState.setValue(UserListLoadingState.loaded);
    }

    public interface AddUserListener{
        void onComplete();
    }

    static ModelFirebase modelFirebase = new ModelFirebase();

    public void createUser(User user, AddUserListener listener){
        modelFirebase.createUser(user,listener);
    }

    public interface GetUserByPhone{
        void onComplete(User user);
    }

    public User getUserByPhone(String userPhoneNumber, GetUserByPhone listener) {
        modelFirebase.getUserByPhone(userPhoneNumber, listener);
        return null;
    }
}
