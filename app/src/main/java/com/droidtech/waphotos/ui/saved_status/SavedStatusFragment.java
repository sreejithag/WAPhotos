package com.droidtech.waphotos.ui.saved_status;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.droidtech.waphotos.MediaViewer;
import com.droidtech.waphotos.R;
import com.droidtech.waphotos.adaptor.GridAdapter;
import com.droidtech.waphotos.services.FileServices;
import com.droidtech.waphotos.services.PermissionManager;

import java.io.File;
import java.util.ArrayList;

public class SavedStatusFragment extends Fragment {

    GridView gridView;
    ArrayList<File> list;
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_saved_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.context = this.getContext();
        PermissionManager permissionManager = new PermissionManager(this.getActivity());
        if(permissionManager.checkStorageReadPermission()){

            FileServices fileServices = new FileServices(this.getActivity());
            list = fileServices.getImageFiles(Environment.getExternalStorageDirectory().toString()+"/WA Saved");

            if(!list.isEmpty()){
                gridView = view.findViewById(R.id.gridViewSavedStatus);
                gridView.setAdapter(new GridAdapter(list,this.getActivity()));

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        startActivity(new Intent(context, MediaViewer.class).putExtra("img", list.get(i).toString()));
                    }
                });
            }
            else{
                TextView nothingSaved = view.findViewById(R.id.noSavedStatus);
                nothingSaved.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.GONE);
            }

        }
        else{
            permissionManager.requestStorageReadPermission();
        }


    }
}