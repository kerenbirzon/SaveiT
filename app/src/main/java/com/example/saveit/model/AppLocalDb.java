package com.example.saveit.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.saveit.SaveiTMediate;
import com.example.saveit.category.Document;
import com.example.saveit.main.Category;

@Database(entities = {Category.class, Document.class}, version = 17)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract DocumentDao documentDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(SaveiTMediate.appContext,
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}
