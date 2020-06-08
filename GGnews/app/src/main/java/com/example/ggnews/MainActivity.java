package com.example.ggnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ggnews.Bean.News;
import com.example.ggnews.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    ActivityMainBinding binding;
    private NewsAdapter adapter;
    private List<News> newsData;
    private int page = 1;
    private int mCurrentColIndex = 0;
    private int[] mCols = new int[]{Constants.NEWS_COL5,Constants.NEWS_COL7,
            Constants.NEWS_COL8,Constants.NEWS_COL10,Constants.NEWS_COL11};

    private Callback callback = new Callback() {
        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            Log.d(TAG,"Fail to connect server");
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            if(response.isSuccessful()){
                Log.d(TAG,"is success");
                final String body = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        Type jsonType =
                                new TypeToken<BaseResponse<List<News>>>(){}.getType();
                        BaseResponse<List<News>> newsListResponse = gson.fromJson(body,jsonType);
                        for (News news:newsListResponse.getData()){
                            adapter.add(news);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            } else {

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initData();
    }

    public void initView(){
        binding.lvNewsList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent(MainActivity.this,
//                                DetailAcitivity.class);
                        Intent intent = new Intent();
                        News news = adapter.getItem(position);
                        intent.putExtra(Constants.NEWS_DETAIL_URL_KEY,news.getUrl());
                        startActivity(intent);
                    }
                }
        );
    }

    private void initData(){
        newsData = new ArrayList<>();
        adapter = new NewsAdapter(MainActivity.this,R.layout.list_item,newsData);
        binding.lvNewsList.setAdapter(adapter);
        refreshData(1);
    }

    private void refreshData(final int page){
        new Thread(new Runnable() {
            @Override
            public void run() {
                NewsRequest requestObj = new NewsRequest();
                requestObj.setCol(mCols[mCurrentColIndex]);
                requestObj.setNum(Constants.NEWS_NUM);
                requestObj.setPage(page);
                String urlParams = requestObj.toString();

                Request request = new Request.Builder()
                        .url(Constants.SERVER_URL+Constants.NEWS_AREA_URL+urlParams).get().build();
                Log.d("request",request.toString());

                try{
                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(callback);
                }catch (NetworkOnMainThreadException ex){
                    ex.printStackTrace();
                }
            }

        }).start();
    }
}