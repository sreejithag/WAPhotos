package com.droidtech.waphotos.services;

import android.app.Activity;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class FileServices {

    private PermissionManager permissionManager;

    public FileServices(Activity activity){
        permissionManager = new PermissionManager(activity);
    }

    public ArrayList<File> getImageFiles(String path){
        File root = new File(path);
        ArrayList<File> list = new ArrayList<>();
        File[] files = root.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                list.addAll(getImageFiles(file.getPath()));
            } else {
                if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png") || file.getName().endsWith(".gif") || file.getName().endsWith(".mp4")) {
                    list.add(file);
                }
            }
        }
        return list;
    }

    //method for checking if WA Saved folder present or not else creates WA Saved folder in storage
    public boolean createSaveFolder(){
        if(permissionManager.checkStorageWritePermission()){

            File storageFolder = new File(Environment.getExternalStorageDirectory()+"/WA Saved");
            if (!storageFolder.exists() || !storageFolder.isDirectory()) {

                return storageFolder.mkdirs();
            }
            return true;

        }else{


            permissionManager.requestStorageWritePermission();
            return false;
        }
    }

    //method for copying file to WA Saved folder
    public boolean copyFile(String filePath,String fileName){

        if(createSaveFolder()){

            try{
                if(permissionManager.checkStorageWritePermission()){
                    File sourceLocation = new File(filePath);
                    File targetLocation = new File(Environment.getExternalStorageDirectory()+"/WA Saved/"+fileName);

                    InputStream in = new FileInputStream(sourceLocation);
                    OutputStream out = new FileOutputStream(targetLocation);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                    return true;
                }
                else{
                    permissionManager.requestStorageWritePermission();
                    return false;
                }
            }
            catch (IOException io){
                io.printStackTrace();
                return false;
            }

        }
        else{

            return false;
        }
    }

    //check if file is saved or not
    public boolean isSaved(String fileName){
        File saved = new File(Environment.getExternalStorageDirectory()+"/WA Saved/"+fileName);
        return saved.exists();

    }

    //method to delete a file
    public boolean deleteFile(String path){
        if(permissionManager.checkStorageWritePermission()){

            try{
                File fileToDelete = new File(path);
                return fileToDelete.delete();
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        else{
            permissionManager.requestStorageWritePermission();
            return false;
        }
    }

    //method to undo accidently saved file
    public boolean undoSave(String fileName){

        if(permissionManager.checkStorageWritePermission()){


            if(isSaved(fileName)){
                File saved = new File(Environment.getExternalStorageDirectory()+"/WA Saved/"+fileName);
                return deleteFile(saved.toString());
            }
            else{
                return false;
            }

        }
        else{
            permissionManager.requestStorageWritePermission();
            return false;
        }


    }
}
