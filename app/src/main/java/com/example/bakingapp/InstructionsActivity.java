package com.example.bakingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;

import java.io.IOException;

public class InstructionsActivity extends AppCompatActivity {

    // All these classes are class memeber attribute
    private MediaPlayer mMediaPlayer;
    private SimpleExoPlayer mSimpleExoPlayer;
    private PlayerView mPlayerView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        TextView instructionText = findViewById(R.id.instructionsTextView);
        instructionText.setText("6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!");
        // ExoPlayer
        mPlayerView = findViewById(R.id.playerView);
        mSimpleExoPlayer =
                new SimpleExoPlayer.Builder(this).setTrackSelector(new DefaultTrackSelector(this)).setLoadControl(new DefaultLoadControl()).build();
        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4";
        MediaItem mediaItem = MediaItem.fromUri(url);
        mSimpleExoPlayer.setMediaItem(mediaItem);
        mSimpleExoPlayer.prepare();
        mSimpleExoPlayer.setPlayWhenReady(true);
        mSimpleExoPlayer.play();
        mPlayerView.setPlayer(mSimpleExoPlayer);
        ImageView backArrow = findViewById(R.id.goToPrevious);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InstructionsActivity.this, "Go to previous step", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView nextArrow = findViewById(R.id.goToNext);
        nextArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InstructionsActivity.this, "Go to previous step", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void playVideo (){
        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4";
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "IOException has happened", Toast.LENGTH_SHORT).show();
        }
    }
}