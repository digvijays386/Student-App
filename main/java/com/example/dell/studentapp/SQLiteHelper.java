package com.example.dell.studentapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by dell on 1/16/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public  void queryData(String sql){
        SQLiteDatabase db= getWritableDatabase();
        db.execSQL(sql);
    }
    public void insertForSignUp(String email, String password) {

        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO ACCOUNT VALUES(NULL,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,email);
        statement.bindString(2,password);
        statement.executeInsert();
    }

    public String searchPassForEmail(String email) {

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT email,password FROM ACCOUNT";
        Cursor cursor = db.rawQuery(query,null);
        String e,p="Not Found";
        if (cursor.moveToFirst()){
            do {
                e = cursor.getString(0);
                if (e.equals(email)){
                    p=cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToLast());
        }
        return  p;
    }

    public void insertData(String name, String phone, String address, byte[] image,String pincode,String videoid){
        SQLiteDatabase db = getWritableDatabase();
        String sql="INSERT INTO STUDENT VALUES(NULL,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindString(2,phone);
        statement.bindString(3,address);
        statement.bindBlob(4,image);
        statement.bindString(5,pincode);
        statement.bindString(6,videoid);
        statement.executeInsert();
    }
    public Cursor getData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
