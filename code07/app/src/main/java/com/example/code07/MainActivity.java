package com.example.code07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
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
    private List<News> newsList = new ArrayList<>();
    private List<Map<String,String>> datalist= new ArrayList<>();
    private String[] titles = null;
    private String[] authors = null;

    private NewsAdapter newsAdapter = null;
    private RecyclerView recyclerView;

    private void initData() {

        int length;
        titles = getResources().getStringArray(R.array.titles);
        authors = getResources().getStringArray(R.array.authors);
        TypedArray images= getResources().obtainTypedArray(R.array.images);
        if (titles.length > authors.length) {
            length = titles.length;
        } else {
            length = titles.length;
        }

        for(int i=0;i<length;i++){
            News news=new News();
            news.setmTitle(titles[i]);
            news.setmAuthor(authors[i]);
            news.setmImageId(images.getResourceId(i,0));
            newsList.add(news);
        }

//        for (int i = 0; i < length; i++) {
//            Map map = new HashMap();
//            map.put(NEWS_TITLE, titles[i]);
//            map.put(NEWS_AUTHOR, authors[i]);
//            datalist.add(map);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.lv_news_list);
        initData();

        NewsAdapter newsAdapter=new NewsAdapter(MainActivity.this,
                R.layout.list_item,
                newsList);
        LinearLayoutManager llm =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(newsAdapter);
//        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, datalist, android.R.layout.simple_list_item_2,
//                new String[]{NEWS_TITLE, NEWS_AUTHOR}, new int[]{android.R.id.text1, android.R.id.text2});
//
//        ListView lvNewsList = findViewById(R.id.lv_news_list);
//        lvNewsList.setAdapter(simpleAdapter);
//        authors=getResources().getStringArray(R.array.authors);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                MainActivity.this,android.R.layout.simple_list_item_1,titles
//        );
//
//        ListView lvNewList = findViewById(R.id.lv_news_list);
//        lvNewList.setAdapter(adapter);
    }
}
