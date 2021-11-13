package com.example.todoappjava.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TodosDB";
    public static final String TABLE_NAME = "TodosTable";
    public static final String TODO_ID = "ID";
    public static final String TODO_TEXT = "TODO_TEXT";
    public static final int DB_VERSION = 1;

    private SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + TODO_TEXT + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String todoText){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TODO_TEXT,todoText);
        long result = db.insert(TABLE_NAME,null, values);
        db.close();
        return (result != -1);
    }

    public Cursor getAllData(){
        db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
    }


    public Integer deleteRecord(String id){
        db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, TODO_ID + "= ?", new String[] {id});
    }
}
