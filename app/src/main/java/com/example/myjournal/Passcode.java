package com.example.myjournal;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "passcode")
public class Passcode implements Serializable {


    @PrimaryKey(autoGenerate = true)
    int id=0;
    @ColumnInfo(name = "passcode_number")
    String passcode="1234";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    @Override
    public String toString() {
        return "Passcode{" +
                "id=" + id +
                ", passcode='" + passcode + '\'' +
                '}';
    }
}
