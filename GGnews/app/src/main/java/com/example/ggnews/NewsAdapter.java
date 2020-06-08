package com.example.ggnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.ggnews.Bean.News;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    private List<News> mNewsData;
    private Context context;
    private int resourceId;

    public NewsAdapter(Context context,int resourceId , List<News> data){
        super(context,resourceId,data);
        this.context = context;
        this.resourceId = resourceId;
        this.mNewsData = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News news = getItem(position);
        View view;

        final ViewHolder vh;

        if(convertView == null){
            view = LayoutInflater.from(getContext())
                    .inflate(resourceId,parent,false);

            vh = new ViewHolder();
            vh.tvTitle = view.findViewById(R.id.tv_title);
            vh.tvSource = view.findViewById(R.id.tv_subtitle);
            vh.ivImage = view.findViewById(R.id.iv_image);
            vh.ivDelete = view.findViewById(R.id.iv_delete);
            vh.tvPublishTime = view.findViewById(R.id.tv_publish_time);

            view.setTag(vh);
        } else {
            view = convertView;
            vh = (ViewHolder) view.getTag();
        }

        vh.tvTitle.setText(news.getTitle());
        vh.tvSource.setText(news.getSource());
        vh.tvPublishTime.setText(news.getCtime());
        vh.ivDelete.setTag(position);

        Glide.with(context).load(news.getPicurl())
                .into(vh.ivImage);
        return view;

    }

    class ViewHolder{
        TextView tvTitle;
        TextView tvSource;
        ImageView ivImage;
        TextView tvPublishTime;

        ImageView ivDelete;
    }
}
