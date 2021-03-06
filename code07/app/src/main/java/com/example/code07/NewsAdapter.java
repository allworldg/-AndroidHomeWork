package com.example.code07;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<News> mNewsData;
    private Context mContext;
    private int resourceId;



    public NewsAdapter(Context context, int resourceId, List<News> data) {
        this.mContext = context;
        this.mNewsData = data;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(resourceId, parent, false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = mNewsData.get(position);
        holder.tvTitle.setText(news.getmTitle());
        holder.tvAuthor.setText(news.getmAuthor());

        if(news.getmImageId()!= -1){
            holder.ivImage.setImageResource(news.getmImageId());
            holder.ivImage.setImageBitmap(news.getBitmap());
        }
    }

    @Override
    public int getItemCount() {
        return mNewsData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvAuthor;
        ImageView ivImage;

        public ViewHolder(View view){
            super(view);
            tvTitle =  view.findViewById(R.id.tv_title);
            tvAuthor = view.findViewById(R.id.tv_subtitle);
            ivImage = view.findViewById(R.id.iv_image);
        }
    }
}
