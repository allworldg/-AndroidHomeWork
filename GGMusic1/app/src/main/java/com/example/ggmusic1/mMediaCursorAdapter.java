package com.example.ggmusic1;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class mMediaCursorAdapter extends CursorAdapter {

    private Context mcontext;
    private LayoutInflater mlayoutInflator;

    public mMediaCursorAdapter(Context context) {
        super(context, null, 0);
        mcontext = context;

        mlayoutInflator = LayoutInflater.from(mcontext);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup ViewGroup) {
        View itemview = mlayoutInflator.inflate(R.layout.list_item, ViewGroup, false);

        if (itemview != null) {
            ViewHolder vh = new ViewHolder();
            vh.tvTitle = itemview.findViewById(R.id.tv_title);
            vh.tvArtist = itemview.findViewById(R.id.tv_artist);
            vh.tvOrder = itemview.findViewById(R.id.tv_order);
            vh.divider = itemview.findViewById(R.id.divider);
            itemview.setTag(vh);

            return itemview;
        }
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder vh = (ViewHolder) view.getTag();

        int titleIndex = cursor.getColumnIndex(
                MediaStore.Audio.Media.TITLE
        );
        int artistIndex = cursor.getColumnIndex(
                MediaStore.Audio.Media.ARTIST
        );

        String title = cursor.getString(titleIndex);
        String artist = cursor.getString(artistIndex);

        int position = cursor.getPosition();

        if (vh != null) {
            vh.tvTitle.setText(title);
            vh.tvArtist.setText(artist);
            vh.tvOrder.setText(Integer.toString(position + 1));
        }
    }

    public class ViewHolder {
        TextView tvTitle;
        TextView tvArtist;
        TextView tvOrder;
        View divider;
    }
}
