package com.example.saveit.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("select * from Category")
    List<Category> getAllCategories();

    @Query("select * from Category where id = :id")
    Category getCategoryById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Category... categories);

    @Query("UPDATE Category SET categoryTitle=:categoryTitle , documentTitle=:documentTitle ,documentType=:documentType,documentComments=:documentComments,imageUrl=:imageUrl WHERE id = :id")
    void update(String categoryTitle, String documentTitle, String documentType, String documentComments, String imageUrl, String id);

    @Delete
    void delete(Category category);
}
