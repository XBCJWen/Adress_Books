package com.example.a17374.adressbooks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersonSQLiteOpenHelper extends SQLiteOpenHelper {
        Context context1=putContext.getContext();
    public PersonSQLiteOpenHelper(Context context) {
        super(context,"person.db",null,3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table person (id integer primary key autoincrement,name varchar(20),number varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
