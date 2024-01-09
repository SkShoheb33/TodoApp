package com.shoheb.todoapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private  static  final  String DATABASE_NAME = "TODO_DATABASE";
    private  static  final  String TABLE_NAME = "TODO_TABLE";
    private  static  final  String COL_1 = "ID";
    private  static  final  String COL_2 = "NAME";
    private  static  final  String COL_3 = "DESCRIPTION";
    private  static  final  String COL_4 = "CATEGORY";
    private  static  final  String COL_5 = "PRIORITY";
    private  static  final  String COL_6 = "STATUS";
    private  static  final  String COL_7 = "DUE_DATE";
    private  static  final  String COL_8 = "CREATED_DATE";
    private SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DESCRIPTION TEXT,CATEGORY TEXT,PRIORITY TEXT, STATUS TEXT,DUE_DATE TEXT, CREATED_DATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void insertTask(Item item){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,item.getItem_name());
        contentValues.put(COL_3,item.getItem_desc());
        contentValues.put(COL_4,item.getItem_cat());
        contentValues.put(COL_5,item.getItem_priority());
        contentValues.put(COL_6,item.getItem_status());
        contentValues.put(COL_7,item.getDue_date());
        contentValues.put(COL_8,item.getCreated_date());
        db.insert(TABLE_NAME,null,contentValues);
    }
    public void updateTask(int id,Item item){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,item.getItem_name());
        contentValues.put(COL_3,item.getItem_desc());
        contentValues.put(COL_4,item.getItem_cat());
        contentValues.put(COL_5,item.getItem_priority());
        contentValues.put(COL_6,item.getItem_status());
        contentValues.put(COL_7,item.getDue_date());
        contentValues.put(COL_8,item.getCreated_date());
        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{String.valueOf(id)});
    }
    public void DeleteTask(int id){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"ID=?",new String[]{String.valueOf(id)});
    }
    @SuppressLint("Range")
    public ArrayList<Item> getAllList(){
        db = this.getReadableDatabase();
        Cursor cursor = null;
        ArrayList<Item> items = new ArrayList<>();
        db.beginTransaction();
        try{
            cursor = db.query(TABLE_NAME,null,null,null,null,null,null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do {
                        Item item = new Item();
                        item.setItem_name(cursor.getString(cursor.getColumnIndex(COL_2)));
                        item.setItem_desc(cursor.getString(cursor.getColumnIndex(COL_3)));
                        item.setItem_cat(cursor.getString(cursor.getColumnIndex(COL_4)));
                        item.setItem_priority(cursor.getString(cursor.getColumnIndex(COL_5)));
                        item.setItem_status(cursor.getString(cursor.getColumnIndex(COL_6)));
                        item.setDue_date(cursor.getString(cursor.getColumnIndex(COL_7)));
                        item.setCreated_date(cursor.getString(cursor.getColumnIndex(COL_8)));
                        items.add(item);
                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return items;
    }
}
