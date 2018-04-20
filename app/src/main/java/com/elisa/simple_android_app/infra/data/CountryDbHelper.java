package com.elisa.simple_android_app.infra.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import com.elisa.simple_android_app.infra.data.CountryContract.CountryEntry;

/**
 * Created by nSafari on 1/10/2017.
 */

public class CountryDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = CountryDbHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "country.db";

    public static SQLiteDatabase.CursorFactory cursorFactory = new SQLiteDatabase.CursorFactory() {
        @Override
        public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
            Log.d(LOG_TAG, query.toString());

            return new SQLiteCursor(masterQuery, editTable, query);
        }
    };


    public CountryDbHelper(Context context) {
        super(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create country table
        final String SQL_CREATE_COUNTRY_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS " + CountryEntry.TABLE_NAME + " (" +
                CountryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CountryEntry.COLUMN_ISO + " TEXT, " +
                CountryEntry.COLUMN_NAME + " TEXT, " +
                CountryEntry.COLUMN_PHONE + " INTEGER" +
                "); ";


        db.execSQL(SQL_CREATE_COUNTRY_TABLE_IF_NOT_EXISTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CountryEntry.TABLE_NAME);

        onCreate(db);
    }

}
