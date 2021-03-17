package com.example.schoolapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.schoolapp.Global.Constant;
import com.example.schoolapp.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class videoplay extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;
    String keyvalue;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_videoplay);

        pd = new ProgressDialog (this);
        pd.setMessage ("loading");
        pd.show ();
        keyvalue = getIntent ().getStringExtra ("keyvalue");

        youTubePlayerView = findViewById (R.id.youtubeplay);
        youTubePlayerView.initialize ("AIzaSyD6Z81-nVQZizYXcy_WIkhZZNs5iP5kB-k", new YouTubePlayer.OnInitializedListener () {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo (keyvalue);
                pd.dismiss ();
                pd.cancel ();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText (videoplay.this, "Youtuber error", Toast.LENGTH_SHORT).show ();
                pd.dismiss ();
                pd.cancel ();
            }
        });
    }
}