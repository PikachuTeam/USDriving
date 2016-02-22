package com.essential.usdriving.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;



import tatteam.com.app_common.sqlite.BaseDataSource;


public class DataSource  extends BaseDataSource{
    private static DataSource instance;
    private Context context;
    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }
    public void init(Context context) {
        this.context = context;
    }
    public static int countExams() {
        //open connection
        SQLiteDatabase sqLite = openConnection();

        Cursor cursor = sqLite.rawQuery("select count(1) from Exams", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        //close cursor
        cursor.close();

        //close connection
        closeConnection();

        return count;
    }
}