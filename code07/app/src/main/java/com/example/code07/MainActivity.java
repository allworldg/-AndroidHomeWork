package com.example.code07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private MySQLiteOpenLite mySQLiteOpenLite;
    private SQLiteDatabase db;
    private List<News> newsList = new ArrayList<>();
    private List<Map<String,String>> datalist= new ArrayList<>();
    private String[] titles = null;
    private String[] authors = null;

    private NewsAdapter newsAdapter = null;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySQLiteOpenLite = new MySQLiteOpenLite(MainActivity.this);
        db = mySQLiteOpenLite.getReadableDatabase();
        Cursor cursor = db.query(NewsContract.NewsEntity.TABLE_NAME,null,null,
                null,null,null,null);
        List<News> newsList = new ArrayList<>();

        int titleIndex = cursor.getColumnIndex(
                NewsContract.NewsEntity.COLUMN_NAME_TITLE
        );
        int authorIndex = cursor.getColumnIndex(
                NewsContract.NewsEntity.COLUMN_NAME_AUTHOR
        );
        int imageIndex = cursor.getColumnIndex(
                NewsContract.NewsEntity.COLUMN_NAME_IMAGE
        );

        while(cursor.moveToNext()){
            News news = new News();

            String title = cursor.getString(titleIndex);
            String author = cursor.getString(authorIndex);
            String image = cursor.getString(imageIndex);

            Bitmap bitmap = BitmapFactory.decodeStream(
                    getClass().getResourceAsStream("/"+image)
            );

            news.setmTitle(title);
            news.setmAuthor(author);
            news.setBitmap(bitmap);
            newsList.add(news);
        }
        recyclerView = findViewById(R.id.lv_news_list);


        NewsAdapter newsAdapter=new NewsAdapter(MainActivity.this,
                R.layout.list_item,
                newsList);
        LinearLayoutManager llm =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(newsAdapter);

    }
}
