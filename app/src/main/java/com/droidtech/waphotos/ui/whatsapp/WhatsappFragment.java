package com.droidtech.waphotos.ui.whatsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.droidtech.waphotos.GridAdapter;
import com.droidtech.waphotos.PermissionManager;
import com.droidtech.waphotos.R;
import com.droidtech.waphotos.Services;
import com.droidtech.waphotos.ViewImage;

import java.io.File;
import java.util.ArrayList;

public class WhatsappFragment extends Fragment {


    GridView gridView;
    ArrayList<File> list;
    Context context;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_whatsapp, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = this.getContext();
        PermissionManager permissionManager = new PermissionManager(this.getActivity());
        if(permissionManager.checkStoragePermission()){

            Services services = new Services();
            list = services.getImageFiles(Environment.getExternalStorageDirectory().toString()+"/Whatsapp/Media/.Statuses");
            gridView = view.findViewById(R.id.gridViewWhatsapp);
            gridView.setAdapter(new GridAdapter(list,this.getActivity()));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    startActivity(new Intent(context, ViewImage.class).putExtra("img", list.get(i).toString()));
                }
            });

        }
        else{
            permissionManager.requestStoragePermission();
        }



    }

}