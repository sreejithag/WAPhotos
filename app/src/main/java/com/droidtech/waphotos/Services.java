package com.droidtech.waphotos;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Services {

    public ArrayList<File> getImageFiles(String path){
        File root = new File(path);
        ArrayList<File> list = new ArrayList<File>();
        File files[] = root.listFiles();
        for(int i = 0; i < files.length; i++){
            if(files[i].isDirectory()){
                list.addAll(getImageFiles(files[i].getPath()));
            }else{
                if(files[i].getName().endsWith(".jpg") || files[i].getName().endsWith(".png") || files[i].getName().endsWith(".gif") || files[i].getName().endsWith(".mp4")){
                    list.add(files[i]);
                }
            }
        }
        return list;
    }


}
