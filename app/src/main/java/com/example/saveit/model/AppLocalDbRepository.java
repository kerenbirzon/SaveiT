package com.example.saveit.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.saveit.SaveiTMediate;

@Database(entities = {Category.class}, version = 100)
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract CategoryDao categoryDao();
}

