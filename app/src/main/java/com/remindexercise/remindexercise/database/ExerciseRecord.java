package com.remindexercise.remindexercise.database;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class ExerciseRecords extends LitePalSupport {
    private int id;
    private Date date;
    private String time;
    private byte[] records;

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public byte[] getRecords() {
        return records;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setRecords(byte[] records) {
        this.records = records;
    }
}
