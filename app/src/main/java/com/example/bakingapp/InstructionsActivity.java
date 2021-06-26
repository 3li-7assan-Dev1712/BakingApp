package com.example.bakingapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bakingapp.JsonRef.JsonUtils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;

public class InstructionsActivity extends AppCompatActivity {

    private String videoUrl;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        TextView instructionText = findViewById(R.id.instructionsTextView);
        TextView noVideoTextView = findViewById(R.id.noVideoText);
        ImageView noVideoImage = findViewById(R.id.noVideoImage);
        Intent intent = getIntent();
        if (intent != null){
            int ingredientId = intent.getIntExtra(getString(R.string.ingredientIdKey), 0);
            int recipeId = intent.getIntExtra(getString(R.string.recipeKey), 0);
            String jsonRes = intent.getStringExtra(getString(R.string.jsonResKey));
            try {
                String description = JsonUtils.getRecipeDescription(jsonRes, recipeId, ingredientId);
                instructionText.setText(description);
                videoUrl = JsonUtils.getRecipeVideoUrl(jsonRes, recipeId, ingredientId);
                Log.d("any", videoUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // ExoPlayer implementation
        PlayerView mPlayerView = findViewById(R.id.playerView);
        SimpleExoPlayer mSimpleExoPlayer = new SimpleExoPlayer.Builder(this)
                .setTrackSelector(new DefaultTrackSelector(this))
                .setLoadControl(new DefaultLoadControl()).build();
        // check if you have a video for our step or not
        if (videoUrl.equals("")){
            mPlayerView.setVisibility(View.INVISIBLE);
            noVideoImage.setVisibility(View.VISIBLE);
            noVideoTextView.setVisibility(View.VISIBLE);
        }

        else {
            // if there's a video for the step we set the the image and text to gone, so the user can interact with the player
            noVideoImage.setVisibility(View.GONE);
            noVideoTextView.setVisibility(View.GONE);
            MediaItem mediaItem = MediaItem.fromUri(videoUrl);
            mSimpleExoPlayer.setMediaItem(mediaItem);
            mSimpleExoPlayer.prepare();
            mSimpleExoPlayer.setPlayWhenReady(true);
            mSimpleExoPlayer.play();
        }
        mPlayerView.setPlayer(mSimpleExoPlayer);
        ImageView backArrow = findViewById(R.id.goToPrevious);
        backArrow.setOnClickListener(v -> Toast.makeText(InstructionsActivity.this, "Go to previous step", Toast.LENGTH_SHORT).show());
        ImageView nextArrow = findViewById(R.id.goToNext);
        nextArrow.setOnClickListener(v -> Toast.makeText(InstructionsActivity.this, "Go to next step", Toast.LENGTH_SHORT).show());
    }


}