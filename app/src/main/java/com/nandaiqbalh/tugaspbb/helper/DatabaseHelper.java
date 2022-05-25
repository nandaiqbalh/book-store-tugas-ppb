package com.nandaiqbalh.tugaspbb.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bookstore.db";
    private static  int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "create table cart(judulbuku text null, hargabuku text null, diskonbuku text null, authorbuku text null, kodebuku text null, qtybuku text null, halamanbuku text null, bahasabuku text null, gambarbuku text null);";
        Log.d("DATA: ", "onCreate: " + sql);
        db.execSQL(sql);

//        sql = "INSERT INTO cart (judulbuku, hargabuku, authorbuku, kodebuku, qtybuku, halamanbuku, bahasabuku) VALUES ('Judul1', '900000', 'Nanda', 'N-01','200', '200', 'Bahasa Indonesia');";
//        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
