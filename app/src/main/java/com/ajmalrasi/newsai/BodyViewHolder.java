package com.ajmalrasi.newsai;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rasi on 19-05-2018.
 */
public class BodyViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView titleView;
    private TextView topicView;
    private TextView descView;
    public static final String TAG = "BodyViewHolder";
    private TextView dateView;
    private TextView categoryView;

    public BodyViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        titleView = itemView.findViewById(R.id.titleView);
        topicView = itemView.findViewById(R.id.topicView);
        descView = itemView.findViewById(R.id.descView);
        categoryView = itemView.findViewById(R.id.categoryView);

    }

    public void populate(Context context, final ArrayList<News> list, final int position) {

        topicView.setText(list.get(position).getTopic());
        titleView.setText(list.get(position).getTitle());
        list.get(position).getLink();
        Picasso.with(context)
                .load(list.get(position).getThumbnails())
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Log.e(TAG, "onError: Error loading Thumbnail from url : "
                                + list.get(position).getThumbnails());
                    }
                });
        descView.setText(list.get(position).getDescription());
        categoryView.setText(list.get(position).getCategory());

    }
}
