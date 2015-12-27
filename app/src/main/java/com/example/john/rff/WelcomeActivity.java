package com.example.john.rff;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by John on 02/12/2015.
 */
public class WelcomeActivity extends Activity
{
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.rawtut));
        videoView.setMediaController(new MediaController(this));
        videoView.setZOrderMediaOverlay(true);
        videoView.setZOrderOnTop(true);
        videoView.requestFocus();
        videoView.start();

        Button profileButton = (Button) findViewById(R.id.profileButton);
        Button dataButton = (Button) findViewById(R.id.dataButton);

    }

    public void profileClicked(View view)
    {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }

    public void dataClicked(View view)
    {
        Intent intent = new Intent(getApplicationContext(), DataFlowActivity.class);
        startActivity(intent);
    }

}
