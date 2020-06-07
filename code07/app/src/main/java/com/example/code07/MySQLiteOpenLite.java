package com.example.code07;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenLite extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTITIES =
            "CREATE TABLE " + NewsContract.NewsEntity.TABLE_NAME + " ("
                    + NewsContract.NewsEntity._ID + " INTEGER PRIMARY KEY, " +
                    NewsContract.NewsEntity.COLUMN_NAME_TITLE + " VARCHAR(200), " +
                    NewsContract.NewsEntity.COLUMN_NAME_AUTHOR + " VARCHAR(200), " +
                    NewsContract.NewsEntity.COLUMN_NAME_CONTENT + " TEXT, " +
                    NewsContract.NewsEntity.COLUMN_NAME_IMAGE + " VARCHAR(200) " +
                    ")" ;
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NewsContract.NewsEntity.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "news.db";
    private Context mcontext;

    public MySQLiteOpenLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mcontext = context;
    }

    /**
     * 第一次创建数据库和数据库表
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTITIES);
        initDb(db);


    }

    private void initDb(SQLiteDatabase sqLiteDatabase) {
        Resources resources = mcontext.getResources();
        String[] titles = resources.getStringArray(R.array.titles);
        String[] authors = resources.getStringArray(R.array.authors);
        String[] contents = resources.getStringArray(R.array.contents);
        TypedArray images = resources.obtainTypedArray(R.array.images);

        int length = 0;
        length = Math.min(titles.length, authors.length);
        length = Math.min(length, contents.length);
        length = Math.min(length, images.length());

        for (int i = 0; i < length; i++) {
            ContentValues values = new ContentValues();
            values.put(NewsContract.NewsEntity.COLUMN_NAME_TITLE,titles[i]);
            values.put(NewsContract.NewsEntity.COLUMN_NAME_AUTHOR,authors[i]);
            values.put(NewsContract.NewsEntity.COLUMN_NAME_CONTENT,contents[i]);
            values.put(NewsContract.NewsEntity.COLUMN_NAME_IMAGE,images.getString(i));

            long r = sqLiteDatabase.insert(NewsContract.NewsEntity.TABLE_NAME,null,
                    values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
