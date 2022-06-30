package com.example.news_detection;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper  {


    private static final String DB_NAME ="detection_historyDB";
    private static final int DB_VERSION = 1;
    private static final String ID_COL = "ID";
    private static final String url = "url";
    private static final String res = "result";
    private static final String date = "date";
    private static final String incidence = "incidence";



    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + "url_info" + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + url + " TEXT,"
                + res+ " TEXT,"
                + date+ " TEXT,"
                + incidence + " INTEGER )";


        sqLiteDatabase.execSQL(query);
    }

    public void add_url(url_result cur_url) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        int inc=Check_url_incidence("url_info",url, cur_url.url);


        if(inc==-1) {

            values.put(url, cur_url.url);
            values.put(res, cur_url.res);
            values.put(date, cur_url.date);
            values.put(incidence, cur_url.incidence);
            db.insert("url_info", null, values);


            db.close();
        }
        else {



            values.put(url, cur_url.url);
            values.put(res, cur_url.res);
            values.put(date, cur_url.date);
            values.put(incidence,++inc);
            db.update("url_info", values, "url=?", new String[]{cur_url.url});
            db.close();



        }
    }

    public int Check_url_incidence(String TableName,
                                   String dbfield, String fieldValue) {

        int inc=0;
        SQLiteDatabase sqldb = this.getReadableDatabase();

        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return -1;
        }
        else {
            if (cursor.moveToFirst()) {
                inc=cursor.getInt(4);

            }
        }
        cursor.close();
        return inc;
    }

    public ArrayList<url_result> get_urls_res(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + "url_info", null);

        ArrayList<url_result> url_ArrayList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                url_ArrayList.add(new url_result( cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)));
            } while (cursor.moveToNext());

        }

        cursor.close();
        return url_ArrayList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "url_info");
        onCreate(sqLiteDatabase);
    }
}
