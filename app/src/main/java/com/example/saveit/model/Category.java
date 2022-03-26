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
    String id;
    String categoryTitle;
    String documentTitle;
    String imageUrl;
    String documentType;
    String documentComments;
    Long updateDate = new Long(0);
    boolean deleted;

    public Category(String id,String categoryTitle, String documentTitle, String imageUrl, String documentType, String documentComments, boolean deleted) {
        this.id = id;
        this.categoryTitle = categoryTitle;
        this.documentTitle = documentTitle;
        this.imageUrl = imageUrl;
        this.documentType = documentType;
        this.documentComments = documentComments;
        this.updateDate = System.currentTimeMillis();
        this.deleted = deleted;
    }

    public Category(String categoryTitle, String documentTitle, String imageUrl, String documentType, String documentComments) {
        this.id = UUID.randomUUID().toString();
        this.categoryTitle = categoryTitle;
        this.documentTitle = documentTitle;
        this.imageUrl = imageUrl;
        this.documentType = documentType;
        this.documentComments = documentComments;
        this.updateDate = System.currentTimeMillis();
    }

    public Category() {
        this.id = UUID.randomUUID().toString();
        this.categoryTitle = "";
        this.documentTitle = "";
        this.imageUrl = "";
        this.documentType = "";
        this.documentComments = "";
    }


    protected Category(Parcel in) {
        id = in.readString();
        categoryTitle = in.readString();
        documentTitle = in.readString();
        imageUrl = in.readString();
        documentType = in.readString();
        documentComments = in.readString();
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
        String id = (String) json.get("id");
        String categoryTitle = (String) json.get("categoryTitle");
        String documentTitle = (String) json.get("documentTitle");
        String documentType = (String) json.get("documentType");
        String documentComments = (String) json.get("documentComments");
        String imageUrl = (String) json.get("imageUrl");
        boolean deleted = (boolean) json.get("deleted");
        Timestamp ts = (Timestamp)json.get("updateDate");
        Long updateDate = ts.getSeconds();

        Category category = new Category(id,categoryTitle,documentTitle,documentType,documentComments,imageUrl,deleted);
        category.setUpdateDate(updateDate);
        return category;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("id",id);
        json.put("categoryTitle",categoryTitle);
        json.put("documentTitle",documentTitle);
        json.put("documentType",documentType);
        json.put("documentComments",documentComments);
        json.put("imageUrl",imageUrl);
        json.put("updateDate", FieldValue.serverTimestamp());
        json.put("deleted",deleted);
        return json;
    }

    public Map<String, Object> toEditJson() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("categoryTitle",categoryTitle);
        json.put("documentTitle",documentTitle);
        json.put("documentType",documentType);
        json.put("documentComments",documentComments);
        json.put("imageUrl",imageUrl);
        json.put("updateDate", FieldValue.serverTimestamp());
        return json;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getCategoryTitle() {
        return categoryTitle;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getDocumentComments() {
        return documentComments;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setCategoryTitle(@NonNull String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public void setDocumentComments(String documentComments) {
        this.documentComments = documentComments;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(categoryTitle);
        parcel.writeString(documentTitle);
        parcel.writeString(documentComments);
        parcel.writeString(documentType);
        parcel.writeString(imageUrl);
        if (updateDate == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(updateDate);
        }
        parcel.writeByte((byte) (deleted ? 1 : 0));
    }
}
