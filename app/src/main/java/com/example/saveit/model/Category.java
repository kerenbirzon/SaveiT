package com.example.saveit.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Category {
    @PrimaryKey
    @NonNull
    private String title;
    private int image;
    //public ArrayList<Document> docsList;

    public Category() {
    }

    public Category(String title) {
        this.title = title;
    }

    public Category(String title, int image) {
        this.title = title;
        this.image = image;
    }

    //getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    //public ArrayList<Document> getDocsList() {
    //    return docsList;
    //}

    //public void setDocsList(ArrayList<Document> docsList) {
    //    this.docsList = docsList;
    //}

}
