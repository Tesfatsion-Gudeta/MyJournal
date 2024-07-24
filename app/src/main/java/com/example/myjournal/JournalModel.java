package com.example.myjournal;

public class JournalModel {
    private String date,title,note;

    public JournalModel(String date, String title, String note) {
        this.date = date;
        this.title = title;
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "JournalModel{" +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}