package com.droidtech.waphotos.ui.whatsapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.droidtech.waphotos.adaptor.GridAdapter;
import com.droidtech.waphotos.services.PermissionManager;
import com.droidtech.waphotos.R;
import com.droidtech.waphotos.services.FileServices;
import com.droidtech.waphotos.MediaViewer;
import java.io.File;
import java.util.ArrayList;

public class WhatsappFragment extends Fragment {


    GridView gridView;
    ArrayList<File> list;
    Context context;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_whatsapp, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = this.getContext();
        PermissionManager permissionManager = new PermissionManager(this.getActivity());

        if(permissionManager.checkStorageReadPermission()){

            gridView = view.findViewById(R.id.gridViewWhatsapp);

            if(isWhatsappInstalled()){
                FileServices fileServices = new FileServices(this.getActivity());
                list = fileServices.getImageFiles(Environment.getExternalStorageDirectory().toString()+"/Whatsapp/Media/.Statuses");

                if(!list.isEmpty()){
                    gridView.setAdapter(new GridAdapter(list,this.getActivity()));

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            startActivity(new Intent(context, MediaViewer.class).putExtra("img", list.get(i).toString()));
                        }
                    });
                }
                else {
                    TextView noFileText = view.findViewById(R.id.whatsappNoFiles);
                    noFileText.setVisibility(View.VISIBLE);
                    gridView.setVisibility(View.GONE);
                }
            }
            else{

                TextView whatsappNotInstalledText = view.findViewById(R.id.whatsappNotAvailableText);
                gridView.setVisibility(View.GONE);
                whatsappNotInstalledText.setVisibility(View.VISIBLE);
            }

        }
        else{
            permissionManager.requestStorageReadPermission();
        }



    }

    private boolean isWhatsappInstalled(){
        PackageManager packageManager = getActivity().getPackageManager();

        try {
            packageManager.getPackageInfo("com.whatsapp", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}