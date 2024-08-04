package com.example.myjournal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Journal.class},version = 1,exportSchema = false)
public abstract class JournalDB extends RoomDatabase {

    private static JournalDB journalDB;
    private static String databaseName="MyJournal";


    public synchronized static JournalDB getJournalDB(Context context){

        if(journalDB==null){
            journalDB= Room.databaseBuilder(context.getApplicationContext(),
                    JournalDB.class,databaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return journalDB;
    }

    public abstract JournalDAO getJournalDAO();
}
