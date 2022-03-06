package com.example.saveit.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Category {
    final public static String COLLECTION_NAME = "categories";
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

    public static Category create(Map<String, Object> json) {
        String title = (String) json.get("title");
        int image = (Integer) json.get("image");

        Category category = new Category(title,image);
        return category;
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

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("title",title);
        json.put("image",image);
        return json;
    }

    //public ArrayList<Document> getDocsList() {
    //    return docsList;
    //}

    //public void setDocsList(ArrayList<Document> docsList) {
    //    this.docsList = docsList;
    //}

}
