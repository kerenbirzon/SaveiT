package com.example.saveit.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DocumentDao {
    @Query("select * from Document where title like :t ")
    List<Document> getAllDocuments(String t);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Document... documents);

    @Delete
    void delete(Document document);
}