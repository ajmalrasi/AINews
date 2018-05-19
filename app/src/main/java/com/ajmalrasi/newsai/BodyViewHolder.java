package com.ajmalrasi.newsai;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rasi on 19-05-2018.
 */
public class BodyViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView titleView;
    private TextView topicView;
    private TextView descView;
    private TextView dateView;

    public BodyViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        titleView = itemView.findViewById(R.id.titleView);
        topicView = itemView.findViewById(R.id.topicView);
        descView = itemView.findViewById(R.id.descView);
    }

    public void populate(Context context, ArrayList<News> list) {

    }
}
