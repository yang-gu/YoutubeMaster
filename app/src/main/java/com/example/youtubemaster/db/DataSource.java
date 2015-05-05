package com.example.youtubemaster.db;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.example.youtubemaster.Item;
public abstract class DataSource {
    protected SQLiteDatabase mDatabase;
    public DataSource(SQLiteDatabase database) {
        mDatabase = database;
    }
    public abstract boolean insert(Item item);
    public abstract boolean delete(Item item);
    public abstract boolean update(Item item);
    public abstract List read();
    
    public abstract List read(String selection, String[] selectionArgs,
            String groupBy, String having, String orderBy);
}
