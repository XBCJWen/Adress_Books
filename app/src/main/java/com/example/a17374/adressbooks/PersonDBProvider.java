package com.example.a17374.adressbooks;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import static android.app.PendingIntent.getActivity;

public class PersonDBProvider extends ContentProvider {
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int INSERT = 1;
    private static final int DELETE = 2;
    private static final int UPDATE = 3;
    private static final int QUERY = 4;
    private static final int QUERYONE = 5;

    private PersonSQLiteOpenHelper helper;

    static {
        matcher.addURI("cn.itcast.db.personprovider", "insert", INSERT);
        matcher.addURI("cn.itcast.db.personprovider", "delete", DELETE);
        matcher.addURI("cn.itcast.db.personprovider", "update", UPDATE);
        matcher.addURI("cn.itcast.db.personprovider", "query", QUERY);
        matcher.addURI("cn.itcast.db.personprovider", "query/#", QUERYONE);

    }

    public boolean onCreate() {
    helper = new PersonSQLiteOpenHelper(putContext.getContext());
        return false;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (matcher.match(uri) == QUERY) {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query("person", projection, selection, selectionArgs, null, null, null, sortOrder);
            return cursor;
        } else if (matcher.match(uri) == QUERYONE) {
            long id = ContentUris.parseId(uri);
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query("person", projection, "id=?", new String[]{id + ""}, null, null, null);
            return cursor;
        } else {
                throw new IllegalArgumentException("路径不匹配，不能执行插入操作");

        }

    }

    public String getType(Uri uri) {
        if (matcher.match(uri) == QUERY) {
            return "vnd.android.cursor.dir/person";
        } else if (matcher.match(uri) == QUERYONE) {
            return "vnd.android.cursor.itm/person";
        }
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        if (matcher.match(uri) == INSERT) {
            SQLiteDatabase db = helper.getWritableDatabase();
            db.insert("person", null, values);
        } else {
            throw new IllegalArgumentException("路径不匹配，不能执行插入操作");
        }
        return null;

    }


    public  int delete (Uri uri,String selection ,String [] selectionArgs){
        if(matcher.match(uri)==DELETE){
            SQLiteDatabase db=helper.getWritableDatabase();
            db.delete("person",selection,selectionArgs);

        }else{
            throw new IllegalArgumentException("路径不匹配，不能执行插入操作");
        }
        return 0;
    }



    public int  update  (Uri uri,ContentValues values,String selection,String []selectionAras ){
        if(matcher.match(uri)==UPDATE){
            SQLiteDatabase db=helper.getWritableDatabase();
            db.update("person",values,selection, selectionAras);
        }else {
            throw new IllegalArgumentException("路径不匹配，不能执行插入操作");
        }
        return  0;
    }

}