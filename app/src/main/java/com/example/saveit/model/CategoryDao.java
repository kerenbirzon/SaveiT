package com.example.saveit.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.saveit.main.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("select * from Category")
    List<Category> getAllCategories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);
}
