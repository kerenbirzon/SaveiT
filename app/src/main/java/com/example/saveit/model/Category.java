package com.example.saveit.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.saveit.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class Category implements Parcelable {
    final public static String COLLECTION_NAME = "categories";

    @PrimaryKey
    @NonNull
    String title;
    String image;
    Long updateDate = new Long(0);
    boolean deleted;

    public Category() {
        this.title = "car";
        this.image = String.valueOf(R.drawable.car);
        this.deleted = false;
    }

    public Category(String title) {
        this.title = title;
        this.image = String.valueOf(R.drawable.car);
    }

    public Category(String title, String image, boolean deleted) {
        this.deleted = false;
        this.title = title;
        this.image = image;
    }

    protected Category(Parcel in) {
        title = in.readString();
        image = in.readString();
        if (in.readByte() == 0) {
            updateDate = null;
        } else {
            updateDate = in.readLong();
        }
        deleted = in.readByte() != 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public static Category create(Map<String, Object> json) {
        String title = (String) json.get("title");
        String image = (String) json.get("image");
        boolean deleted = (boolean) json.get("deleted");
        Timestamp ts = (Timestamp)json.get("updateDate");
        Long updateDate = ts.getSeconds();

        Category category = new Category(title,image, deleted);
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("title",title);
        json.put("image",image);
        json.put("updateDate", FieldValue.serverTimestamp());
        json.put("deleted",deleted);
        return json;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(image);
        if (updateDate == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(updateDate);
        }
        parcel.writeByte((byte) (deleted ? 1 : 0));
    }
}
