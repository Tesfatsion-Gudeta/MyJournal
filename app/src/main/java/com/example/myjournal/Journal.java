package com.example.myjournal;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "journal")
public class Journal implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int id=0;
    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "note")
    String note;
    @ColumnInfo(name = "date")
    String date;
    @ColumnInfo(name = "pinned")
    boolean pinned=false;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }



}
