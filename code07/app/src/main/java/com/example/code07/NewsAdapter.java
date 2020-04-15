package com.example.code07;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.code07.News;
import com.example.code07.R;

import org.w3c.dom.Text;

import java.util.List;

public class NewsAdapter extends ArrayAdapter {
    private List<News> mNewsData;
    private Context mContext;
    private int resourceId;

    public NewsAdapter(Context context, int resourceId, List<News> data) {
        super(context, resourceId, data);
        this.mContext = context;
        this.mNewsData = data;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news=(News)getItem(position);
        View view;

        view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView tvTitle =view.findViewById(R.id.tv_title);
        TextView tvAuthor=view.findViewById(R.id.tv_subtitle);
        ImageView imageView = view.findViewById(R.id.iv_image);

        tvTitle.setText(news.getmTitle());
        tvAuthor.setText(news.getmAuthor());
        imageView.setImageResource(news.getmImageId());
        return view;
    }
}
