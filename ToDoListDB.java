package com.applicationcommunity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoListDB extends SQLiteOpenHelper {
    static final private String DBNAME = "todolist.db";
    static final private int VERSION  = 1;

    public ToDoListDB(Context context){
            super(context,DBNAME,null,VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE todolist (" + " now TEXT PRIMARY KEY,title TEXT,date TEXT,time TEXT,detail TEXT)");
    }

    @Override
    public  void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS todolist");
        onCreate(db);
    }
}
