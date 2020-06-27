package com.droidtech.waphotos.services;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;

public class PermissionManager {

    private static final int PERMISSION_READ_STORAGE=1;
    private static final int PERMISSION_WRITE_STORAGE=1;

    private Activity activity;

    public PermissionManager(Activity activity){
        this.activity = activity;
    }
    private boolean isReadFirstTime(){

        return  activity.getSharedPreferences("firstReqRead",Context.MODE_PRIVATE).getBoolean("storage",true);

    }

    private void firstReadCall(){
        SharedPreferences sharedPreferences = activity.getSharedPreferences("firstReqRead",Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("storage",false).apply();
    }


    public boolean checkStorageReadPermission(){

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){

            return true;
        }
        else{
            return (ActivityCompat.checkSelfPermission(activity,Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED);
        }

    }

    public boolean checkStorageWritePermission(){

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){

            return true;
        }
        else{
            return (ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED);
        }

    }

    public void requestStorageReadPermission(){


        if(ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(activity)
                    .setMessage("Storage permission is necessary for this application")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_READ_STORAGE);
                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();
                            System.exit(0);
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();
        }else if(isReadFirstTime()){
            firstReadCall();
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_READ_STORAGE);
        }else{

            new AlertDialog.Builder(activity)
                .setMessage("Storage permission is required please enable storage permission")
                .setCancelable(false)
                .setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + "com.droidtech.waphotos"));
                            activity.startActivity(intent);
                            activity.finish();
                            System.exit(0);
                        }
                    })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();
                            System.exit(0);
                        }
                    })
                .create()
                .show();

        }
    }

    public void requestStorageWritePermission() {


        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_STORAGE);
    }
}
