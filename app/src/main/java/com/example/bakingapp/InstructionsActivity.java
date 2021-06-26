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
    private int ingredientId;
    private int recipeId;
    private String jsonRes;
    private int stepsNumber;
    private SimpleExoPlayer mSimpleExoPlayer;
    private PlayerView mPlayerView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        TextView instructionText = findViewById(R.id.instructionsTextView);
        TextView noVideoTextView = findViewById(R.id.noVideoText);
        ImageView noVideoImage = findViewById(R.id.noVideoImage);
        jsonRes = MainActivity.getJsonRes();
        Intent intent = getIntent();
        if (intent != null){
            ingredientId = intent.getIntExtra(getString(R.string.ingredientIdKey), 0);
            recipeId = intent.getIntExtra(getString(R.string.recipeKey), 0);
            try {
                String description = JsonUtils.getRecipeDescription(jsonRes, recipeId, ingredientId);
                instructionText.setText(description);
                videoUrl = JsonUtils.getRecipeVideoUrl(jsonRes, recipeId, ingredientId);
                stepsNumber = JsonUtils.getMaxStepId(jsonRes, recipeId);
                Toast.makeText(this, "steps number is: " + stepsNumber, Toast.LENGTH_SHORT).show();
                Log.d("stepsNumber","stpes number is " + stepsNumber);
                Log.d("any", videoUrl);
            } catch (Exception e) {
                e.printStackTrace();
                ingredientId = 0;
                recipeId = 0;

            }

        }

        // ExoPlayer implementation
        mPlayerView = findViewById(R.id.playerView);
        mSimpleExoPlayer= new SimpleExoPlayer.Builder(this)
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
        Intent openTheSameActivity = new Intent(InstructionsActivity.this, InstructionsActivity.class);
       backArrow.setOnClickListener(v -> {
           ingredientId-=1;
           if (ingredientId < 0){
               Toast.makeText(this, "There's no previous steps", Toast.LENGTH_SHORT).show();
           }else {
               openTheSameActivity.putExtra(getString(R.string.ingredientIdKey), ingredientId);
               openTheSameActivity.putExtra(getString(R.string.recipeKey), recipeId);
               startActivity(openTheSameActivity);
           }
       });
        ImageView nextArrow = findViewById(R.id.goToNext);
        nextArrow.setOnClickListener(v -> {
            ingredientId+=1;
            if (ingredientId >= stepsNumber){
                Toast.makeText(this, "There's no next steps to view, this is the last step", Toast.LENGTH_SHORT).show();
            }else {
                openTheSameActivity.putExtra(getString(R.string.ingredientIdKey), ingredientId);
                openTheSameActivity.putExtra(getString(R.string.recipeKey), recipeId);
                startActivity(openTheSameActivity);
            }
        });

    }

}