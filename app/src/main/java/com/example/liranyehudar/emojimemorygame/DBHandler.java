package com.example.liranyehudar.emojimemorygame;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    //database name
    private static final String DB_NAME = "memoryGame";
    //table name
    private static final String TABLE_PLAYER = "playerDetails";
    //contact table name
    private static final String KEY_ID = "id";
    private static final String KEY_AGE = "age";
    private static final String KEY_NAME = "name";
    private static final String KEY_RESULT = "result";
    // location will add later.

    public DBHandler(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create player table
        String CREATE_PLAYER_DETAILS_TABLE = "CREATE TABLE " + TABLE_PLAYER + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " VARCHAR(255),"
                + KEY_AGE + " INTEGER,"
                + KEY_RESULT + " INTEGER " + ")";

        db.execSQL(CREATE_PLAYER_DETAILS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop older table if existed
        String drop = "DROP TABLE IF EXISTS "+TABLE_PLAYER;
        db.execSQL(drop);

        onCreate(db);
    }

    public boolean insertData(String name,int age, int result){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues  values =  new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_AGE,age);
        values.put(KEY_RESULT,result);
        long res = db.insert(TABLE_PLAYER, null, values);
        return (res!= -1);
    }

    public void updateData(String id,String name,int age, int result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_AGE,age);
        values.put(KEY_RESULT,result);
        db.update(TABLE_PLAYER, values, KEY_ID+" = ?",new String[]{id});
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.rawQuery("SELECT * FROM "+ TABLE_PLAYER, null);
    }

    public Cursor findMinByResult() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT "+KEY_ID+", "+KEY_RESULT+" "
                + "FROM "+ TABLE_PLAYER+" ORDER BY "+KEY_RESULT
                + " LIMIT 1;", null);
    }

}
