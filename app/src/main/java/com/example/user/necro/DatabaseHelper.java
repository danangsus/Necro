package com.example.user.necro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PublicKey;

/**
 * Created by user on 19-Jan-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Necro.db";
    public static final String TABLE_NAME_A = "master_resource";
//    public static final String TABLE_NAME_B = "master_category";
//    public static final String TABLE_NAME_C = "master_pm";

    public static final String COL_1A = "ID";
    public static final String COL_2A = "NAME";
    public static final String COL_3A = "MARK";
    public static final String COL_4A = "LOAD";

/*    public static final String COL_1B = "ID";
    public static final String COL_2B = "CATEGORY";
    public static final String COL_3B = "MARK";
    public static final String COL_4B = "LOAD";

    public static final String COL_1C = "ID";
    public static final String COL_2C = "NAME";
    public static final String COL_3C = "MARK";
    public static final String COL_4C = "LOAD";*/

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME_A + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, MARK TEXT, LOAD INTEGER)");
        //db.execSQL("CREATE TABLE "+TABLE_NAME_B+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORY TEXT, MARK TEXT, LOAD INTEGER)");
        //db.execSQL("CREATE TABLE "+TABLE_NAME_C+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, MARK TEXT, LOAD INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME_A);
        onCreate(db);
    }

    //AWAL BLOK MASTER_RESOURCE
    public boolean insertData(String name, String mark, String load) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2A, name);
        contentValues.put(COL_3A, mark);
        contentValues.put(COL_4A, load);
        long result = db.insert(TABLE_NAME_A, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_A, null);
        return res;

    }

    public Cursor getAllResGrid(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COL_1A,COL_2A,COL_3A,COL_4A};
        return db.query(TABLE_NAME_A,columns,null,null,null,null,null);
    }

    public Cursor getIDResGrid(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor idRes = db.rawQuery("select ID from"+ TABLE_NAME_A,null);
        return  idRes;
    }
/*
    public boolean updateData(String id,String name,String mark, String load){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1A,id);
        contentValues.put(COL_2A,name);
        contentValues.put(COL_3A,mark);
        contentValues.put(COL_4A,load);
        db.update(TABLE_NAME_A,contentValues,"ID = ?",new String[]{ id });
        return true;

    }*/

    public Integer deleteData (String name){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_A,"NAME = ?",new String[]{name});

    }
    //AKHIR BLOK MASTER_RESOURCE
}
