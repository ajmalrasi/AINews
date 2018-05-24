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
public class BodyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imageView;
    private TextView titleView;
    private TextView topicView;
    private TextView descView;
    public static final String TAG = "BodyViewHolder";
    private TextView dateView;
    private TextView categoryView;
    private ClickListener clickListener;
    private ArrayList<News> list;

    public BodyViewHolder(View itemView, ClickListener clickListener) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        titleView = itemView.findViewById(R.id.titleView);
        topicView = itemView.findViewById(R.id.topicView);
        descView = itemView.findViewById(R.id.descView);
        categoryView = itemView.findViewById(R.id.categoryView);
        this.list = new ArrayList<>();
        this.clickListener = clickListener;
        itemView.setOnClickListener(this);

    }

    public void populate(Context context, final ArrayList<News> list, final int position) {
        this.list = list;
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

    @Override
    public void onClick(View v) {
        clickListener.ItemClicked(list.get(getAdapterPosition()));
    }
}
