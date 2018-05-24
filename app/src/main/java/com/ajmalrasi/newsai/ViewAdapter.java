package com.ajmalrasi.newsai;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Rasi on 19-05-2018.
 */
public class ViewAdapter extends RecyclerView.Adapter<BodyViewHolder> {


    private static String TAG = "ViewAdapter";
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private Context context;
    private ClickListener clickListener;
    private ArrayList<News> list = new ArrayList<>();

    public ViewAdapter(Context context, ClickListener clickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.volleySingleton = VolleySingleton.getInstance();
        this.imageLoader = volleySingleton.getImageLoader();
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setViewList(ArrayList<News> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BodyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.view_item, parent, false);
        return new BodyViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BodyViewHolder holder, int position) {
        holder.populate(context, list, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
