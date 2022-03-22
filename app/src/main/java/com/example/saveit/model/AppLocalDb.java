package com.example.saveit.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.saveit.SaveiTMediate;

public abstract class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(SaveiTMediate.getAppContext(),
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}
