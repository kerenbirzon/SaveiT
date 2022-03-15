package com.example.saveit.User;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class User {
    @PrimaryKey
    private String phoneNumber;
    private String userName;
    private String password;
    Long updateDate = 0L;


    public User(String userName, String password, String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("userName", this.userName);
        json.put("password",this.password);
        json.put("phoneNumber", this.phoneNumber);
        json.put("updateDate", FieldValue.serverTimestamp());
        return json;
    }

    public static User create (Map<String, Object> json){
        String userName = (String) json.get("userName");
        String password = (String) json.get("password");
        String phoneNumber = (String) json.get("phoneNumber");
        Timestamp timestamp = (Timestamp) json.get("updateDate");
        Long updateDate = timestamp.getSeconds();

        User user = new User(userName,password,phoneNumber);
        user.setUpdateDate(updateDate);
        return user;
    }

}
