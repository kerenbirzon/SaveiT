package com.example.saveit.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

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
    Long updateDate = new Long(0);
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
        Timestamp ts = (Timestamp)json.get("updateDate");
        Long updateDate = ts.getSeconds();

        Category category = new Category(title,image);
        category.setUpdateDate(updateDate);
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
        json.put("updateDate", FieldValue.serverTimestamp());
        return json;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    //public ArrayList<Document> getDocsList() {
    //    return docsList;
    //}

    //public void setDocsList(ArrayList<Document> docsList) {
    //    this.docsList = docsList;
    //}

}
