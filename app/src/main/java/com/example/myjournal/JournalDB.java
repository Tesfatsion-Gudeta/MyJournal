package com.example.myjournal;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Journal.class,version = 1,exportSchema = false)
public abstract class JournalDB extends RoomDatabase {

    public abstract JournalDAO getJournalDAO();
}
