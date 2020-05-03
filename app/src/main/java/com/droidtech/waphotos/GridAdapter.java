package com.droidtech.waphotos;

import android.app.Activity;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private ArrayList<File> list;
    private Activity activity;

    public GridAdapter(ArrayList<File> list,Activity activity){
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.grid_item, viewGroup, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);


        if(getItem(i).toString().endsWith(".mp4")){
            ImageView videoIcon = (ImageView) view.findViewById(R.id.videoIcon);
            videoIcon.setVisibility(View.VISIBLE);
        }
        Glide.with(activity)
                .load(getItem(i).toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .useAnimationPool(true)
                .into(imageView);

        return view;
    }
}
