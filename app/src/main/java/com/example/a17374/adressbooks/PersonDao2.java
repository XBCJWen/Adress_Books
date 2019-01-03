package com.example.a17374.adressbooks;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;

public class PersonDao2 {
    private PersonSQLiteOpenHelper helper;

    public PersonDao2(MainActivity context) {
        helper = new PersonSQLiteOpenHelper(context);
    }

    public long add(String name, String number, int money) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("number", number);
        long id = db.insert("person", null, values);
        db.close();
        return id;
    }
}
