package com.example.ggmusic1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private ContentResolver mContentResolver;//通过contentresolver来查询多媒体
    private ListView mPlaylist;
    private Cursor cursor;
    private mMediaCursorAdapter mMediaCursorAdapter;
    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private BottomNavigationView navigation;//底部播放组件对象
    private TextView tvBottomTitle;
    private TextView tvBottomArtist;
    private ImageView ivThumbnail;//专辑封面图
    private ImageView ivplay;//播放按钮图

    private final String SELECTION =
            MediaStore.Audio.Media.IS_MUSIC + " = ? " + " AND " +
                    MediaStore.Audio.Media.MIME_TYPE + " Like ? ";
    private final String[] SELECTION_ARGS = {
            Integer.toString(1), "audio/mpeg"
    };

    private MediaPlayer mediaPlayer = null;//音乐播放器定义

    private ListView.OnItemClickListener itemClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Cursor cursor = mMediaCursorAdapter.getCursor();
            Toast.makeText(MainActivity.this,"u click"+i+""+l,Toast.LENGTH_SHORT).show();
            if (cursor != null && cursor.moveToPosition(i)) {
                int titleIndex = cursor.getColumnIndex(MediaStore.
                        Audio.Media.TITLE);
                int artistIndex = cursor.getColumnIndex(MediaStore.Audio.
                        Media.ARTIST);
                int albumIndex = cursor.getColumnIndex(MediaStore.Audio.
                        Media.ALBUM_ID);
                int dataIndex = cursor.getColumnIndex(MediaStore.Audio.
                        Media.DATA);
                navigation.setVisibility(View.VISIBLE);
                String title = cursor.getString(titleIndex);
                String artist = cursor.getString(artistIndex);
                long albumId = cursor.getLong(albumIndex);
                String data = cursor.getString(dataIndex);
                if (tvBottomTitle != null) {
                    tvBottomTitle.setText(title);//添加title名字
                }
                if (tvBottomArtist != null) {
                    tvBottomArtist.setText(artist);//显示作者名字
                }

                Uri albumUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumId
                );

                Cursor albumcursor = mContentResolver.query(albumUri, null, null,
                        null, null);

                if (albumcursor != null && albumcursor.getCount() > 0){
                    albumcursor.moveToFirst();
                    int albumArtIndex = albumcursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                    String albumArt = cursor.getString(albumArtIndex);
                    Glide.with(MainActivity.this).load(albumArt).into(ivThumbnail);
                    albumcursor.close();
                }

                    Uri dataUri = Uri.parse(data);
                if (mediaPlayer != null) {
                    try {
                        mediaPlayer.reset();//重启
                        mediaPlayer.setDataSource(MainActivity.this, dataUri);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentResolver = getContentResolver();
        mMediaCursorAdapter = new mMediaCursorAdapter(MainActivity.this);
        mPlaylist = findViewById(R.id.lv_playlist);
        mPlaylist.setAdapter(mMediaCursorAdapter);

        mPlaylist.setOnItemClickListener(itemClickListener);
        //加载bottom media toolbar布局
        navigation = findViewById(R.id.navigation);
        LayoutInflater.from(MainActivity.this).inflate(R.layout.bottom_media_toolbar,
                navigation,
                true);
        ivplay = navigation.findViewById(R.id.iv_play);
        tvBottomArtist = navigation.findViewById(R.id.tv_bottom_artist);
        tvBottomTitle = navigation.findViewById(R.id.tv_bottom_title);
        ivThumbnail = navigation.findViewById(R.id.iv_thumbnail);

        if (ivplay != null) {
            ivplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        navigation.setVisibility(View.GONE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {//checkSelfPermission用于指定权限是否已经获得权限,如果申请失败
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {//如果app请求权限被用户拒绝，则返回true
            } else {
                requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);//如果用户选择dont ask again
            }
        } else {
            initPlaylist();//查询多媒体文件并且显示
        }


    }



    @Override
    protected void onStart() {
        super.onStart();
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
    }

    @Override
    protected void onStop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            Log.d("tesetstop", "onstop invoked");
        }
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initPlaylist();
                }
                break;
            default:
                break;
        }
    }

    public void verifyPermission(Context context) {
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    private void initPlaylist() {
        cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                SELECTION,
                SELECTION_ARGS,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        mMediaCursorAdapter.swapCursor(cursor);
        mMediaCursorAdapter.notifyDataSetChanged();
    }
}
