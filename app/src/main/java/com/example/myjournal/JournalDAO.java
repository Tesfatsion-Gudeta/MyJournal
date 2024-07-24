package com.example.myjournal;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JournalDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Journal journal);

    @Query("SELECT * FROM journal")
    List<Journal> getAllNotes();

//    @Query("UPDATE journal SET title = :title,note = :note WHERE id")
//    void update(int id,String title,String note);

    @Delete
    void delete(Journal journal);

}
