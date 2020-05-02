package com.droidtech.waphotos;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PermissionCheckerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        PermissionManger permissionManger = new PermissionManger(this);
        if(permissionManger.checkStoragePermission()){
            Intent intent = new Intent(this,home.class);
            startActivity(intent);
        }
        else{
            permissionManger.requestStoragePermission();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(this,home.class);
                startActivity(intent);
            }else{
                PermissionManger permissionManger = new PermissionManger(this);
                permissionManger.requestStoragePermission();

            }

        }
        else{
            PermissionManger permissionManger = new PermissionManger(this);
            permissionManger.requestStoragePermission();
        }
    }

}
