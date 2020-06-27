package com.droidtech.waphotos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.droidtech.waphotos.services.FileServices;
import com.google.android.material.snackbar.Snackbar;
import com.jsibbold.zoomage.ZoomageView;

import java.io.File;

public class MediaViewer extends AppCompatActivity {

    private FileServices fileServices;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_viewer);

        fileServices = new FileServices(this);
        Intent intent = getIntent();
        final String filePath  = intent.getStringExtra("img");


        if(filePath.endsWith(".mp4")){
            VideoView videoView = findViewById(R.id.videoView);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath(filePath);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.start();


        }else{
            ZoomageView imageZoomageView = findViewById(R.id.imageView);
            imageZoomageView.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(filePath)
                    .into(imageZoomageView);

        }

        final ImageButton downloadButton = findViewById(R.id.download_button);

        // check if the file is already saved or not if saved then disables the save button
        if(fileServices.isSaved(filePath.substring(filePath.lastIndexOf("/")+1))){
            downloadButton.setVisibility(View.INVISIBLE);
        }

        //enable action for save/download button
        downloadButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                    final String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
                    if(downloadClick(filePath,fileName)){
                        downloadButton.setVisibility(View.INVISIBLE);
                        Snackbar snackbar = Snackbar .make(v,"Saved successfully",Snackbar.LENGTH_LONG)
                                //set option to undo mistakenly saved files
                                .setAction("undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(fileServices.undoSave(fileName)){
                                            Snackbar snackbar = Snackbar .make(v,"Not saved",Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                            downloadButton.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });
                        snackbar.show();
                    }
                    else{
                        Snackbar snackbar = Snackbar .make(v,"Error can't save",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

            }
        });

        ImageButton deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                deleteClickConfirmation(filePath);
            }
        });

        ImageButton shareButton = findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(filePath);
            }
        });
    }


    //method for copying the file from whatsapp status folder to WA Saved folder
    private boolean downloadClick(String filePath,String fileName)  {

        return fileServices.copyFile(filePath,fileName);
    }


    private void deleteClickConfirmation(final String file){
        AlertDialog confirmDelete = new AlertDialog.Builder(this)
                .setMessage("Do you want to delete")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        delete(file);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .create();
        confirmDelete.show();
    }

    private void delete(String file){

           if( fileServices.deleteFile(file)){
               Snackbar snackbar = Snackbar .make(getWindow().getDecorView(),"Deleted",Snackbar.LENGTH_LONG);
               snackbar.show();
               startActivity(new Intent(this,Home.class));
               this.finish();
           }
           else{
               Snackbar snackbar = Snackbar .make(getWindow().getDecorView(),"Can't Delete",Snackbar.LENGTH_LONG);
               snackbar.show();
           }
    }

    private void share(String file){

        Uri uri = FileProvider.getUriForFile(MediaViewer.this, BuildConfig.APPLICATION_ID + ".provider", new File(file));


        if(file.substring(file.lastIndexOf(".")+1).equals("jpg")){
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
            startActivity(Intent.createChooser(shareIntent,"Share Image using"));

        }
        else{

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("video/*");
            startActivity(Intent.createChooser(shareIntent,"Share Video using"));

        }


    }

}
