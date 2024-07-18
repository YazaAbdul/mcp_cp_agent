package com.mcpcustomer_post_new_ps_n.android.ui.activities.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.mcpcustomer_post_new_ps_n.android.R;

import java.util.ArrayList;

public class VideosNextScreen extends AppCompatActivity  implements MediaPlayer.OnCompletionListener{

    VideoView vw;
    ArrayList<Integer> videolist = new ArrayList<>();
    int currvideo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_next_screen);

        vw = (VideoView)findViewById(R.id.VideoID);
        vw.setMediaController(new MediaController(this));
        vw.setOnCompletionListener(this);

        //videolist.add(R.raw.floor_plan);
        setVideo(videolist.get(0));
    }

    public void setVideo(int id)
    {
        String uriPath
                = "android.resource://"
                + getPackageName() + "/" + id;
        Uri uri = Uri.parse(uriPath);
        vw.setVideoURI(uri);
        vw.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        AlertDialog.Builder obj = new AlertDialog.Builder(this);
        obj.setTitle("Playback Finished!");
        obj.setIcon(R.mipmap.ic_launcher);
        MyListener m = new MyListener();
        obj.setPositiveButton("Replay", m);
        //obj.setNegativeButton("Next", m);
        obj.setMessage("Want to replay?");
        obj.show();

    }

    class MyListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which)
        {
            if (which == -1) {
                vw.seekTo(0);
                vw.start();
            }
            else {
                ++currvideo;
                if (currvideo == videolist.size())
                    currvideo = 0;
                setVideo(videolist.get(currvideo));
            }
        }
    }
}