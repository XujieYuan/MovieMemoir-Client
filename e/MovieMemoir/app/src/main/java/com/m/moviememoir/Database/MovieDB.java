package com.m.moviememoir.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.m.moviememoir.Bean.Watch;

import java.util.ArrayList;
import java.util.List;

public class MovieDB extends SQLiteOpenHelper {
    public static final String TABLE_MOVIE = "tbMovie";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_IMGURL = "img_url";
    public static final String COLUMN_ADD = "add_time";

    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_MOVIE + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text, "
            + COLUMN_TIME + " text, "
            + COLUMN_IMGURL + " text, "
            + COLUMN_ADD + " text"
            + ");";

    public MovieDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MovieDB.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }

    public synchronized void insertValue(Watch watch) {
        Cursor res = getReadableDatabase().rawQuery("SELECT " + COLUMN_ID + " FROM " + TABLE_MOVIE + " WHERE " + COLUMN_TITLE + "=?", new String[]{watch.getTitle()});
        if (!res.moveToNext()) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_TITLE, watch.getTitle());
            cv.put(COLUMN_TIME, watch.getReleaseTime());
            cv.put(COLUMN_ADD, watch.getAddTime());
            cv.put(COLUMN_IMGURL, watch.getImgUrl());
            getWritableDatabase().insert(TABLE_MOVIE, null, cv);
        }
        res.close();
    }

    public synchronized List<Watch> getValue() {
        List<Watch> watchArrayList = new ArrayList<>();
        Cursor res = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_MOVIE, null);
        while (res.moveToNext()) {
            Watch watch = new Watch();
            watch.setAddTime(res.getString(res.getColumnIndex(COLUMN_ADD)));
            watch.setImgUrl(res.getString(res.getColumnIndex(COLUMN_IMGURL)));
            watch.setReleaseTime(res.getString(res.getColumnIndex(COLUMN_TIME)));
            watch.setTitle(res.getString(res.getColumnIndex(COLUMN_TITLE)));
            watchArrayList.add(watch);
        }
        res.close();
        return watchArrayList;
    }

    public synchronized boolean isMark(String title) {
        Cursor res = getReadableDatabase().rawQuery("SELECT " + COLUMN_ID + " FROM " + TABLE_MOVIE + " WHERE " + COLUMN_TITLE + "=?", new String[]{title});
        Boolean isMark = res.moveToNext();
        res.close();
        return isMark;
    }

    public synchronized void deleteValue(String title) {
        getWritableDatabase().execSQL("DELETE FROM " + TABLE_MOVIE + " WHERE " + COLUMN_TITLE + "=?", new String[]{title});
        getWritableDatabase().close();
    }
}
