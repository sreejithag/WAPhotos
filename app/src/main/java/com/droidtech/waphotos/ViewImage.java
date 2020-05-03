package com.droidtech.waphotos;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

public class ViewImage extends AppCompatActivity {

    private ZoomageView imageZoomageView;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        Intent intent = getIntent();
        String file  = intent.getStringExtra("img");
         imageZoomageView =  findViewById(R.id.imageView);
        Glide.with(this)
                .load(file)
                .into(imageZoomageView);


    }


}
