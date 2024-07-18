package com.mcpcustomer_post_new_ps_n.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mcpcustomer_post_new_ps_n.android.ui.adapter.ProjectDetailsAdapter;

public class VideosActivity extends AppCompatActivity {

   /* VideoView vw,vw2,vw3,vw4;
    ArrayList<Integer> videolist = new ArrayList<>();
    int currvideo = 0;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        AppCompatImageView imageView = findViewById(R.id.backBtn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideosActivity.this, ProjectDetailsAdapter.class);
                finish();
            }
        });

       /* vw = findViewById(R.id.videosVV);
        vw.setMediaController(new MediaController(this));
        vw.setOnCompletionListener(this);

        vw2 = findViewById(R.id.videosVV2);
        vw2.setMediaController(new MediaController(this));
        vw2.setOnCompletionListener(this);

        vw3 = findViewById(R.id.videosVV3);
        vw3.setMediaController(new MediaController(this));
        vw3.setOnCompletionListener(this);

        vw4 = findViewById(R.id.videosVV4);
        vw4.setMediaController(new MediaController(this));
        vw4.setOnCompletionListener(this);

        videolist.add(R.raw.floor_plan);
        setVideo(videolist.get(0));

        videolist.add(R.raw.floor_plans2);
        setVideo2(videolist.get(0));

        videolist.add(R.raw.floor_plan);
        setVideo3(videolist.get(0));

        videolist.add(R.raw.floor_plans2);
        setVideo4(videolist.get(0));*/

    }
/*
    private void setVideo4(Integer id) {

        String uriPath
                = "android.resource://"
                + getPackageName() + "/" + id;
        Uri uri = Uri.parse(uriPath);
        vw4.setVideoURI(uri);
        vw4.start();
    }*/

   /* private void setVideo3(Integer id) {

        String uriPath
                = "android.resource://"
                + getPackageName() + "/" + id;
        Uri uri = Uri.parse(uriPath);
        vw3.setVideoURI(uri);
        vw3.start();

    }

    private void setVideo2(Integer id) {

        String uriPath
                = "android.resource://"
                + getPackageName() + "/" + id;
        Uri uri = Uri.parse(uriPath);
        vw2.setVideoURI(uri);
        vw2.start();
    }

    private void setVideo(int id) {

        String uriPath
                = "android.resource://"
                + getPackageName() + "/" + id;
        Uri uri = Uri.parse(uriPath);
        vw.setVideoURI(uri);
        vw.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        *//*AlertDialog.Builder obj = new AlertDialog.Builder(this);
        obj.setTitle("Playback Finished!");
        //obj.setIcon(R.mipmap.ic_launcher);
        MyListener m = new MyListener();
        obj.setPositiveButton("Replay",m);
        obj.setMessage("Want to replay or play next video?");
        obj.show();*//*

    }*/

/*
    private class MyListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == -1) {
                vw.seekTo(0);
                vw.start();

                vw2.seekTo(0);
                vw2.start();

                vw3.seekTo(0);
                vw3.start();

                vw4.seekTo(0);
                vw4.start();
            }
            else {
                ++currvideo;
                if (currvideo == videolist.size())
                    currvideo = 0;
                setVideo(videolist.get(currvideo));
            }
        }
    }
*/
}