package com.example.bakingapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        TextView instructionText = findViewById(R.id.instructionsTextView);
        Intent intent = getIntent();
        if (intent != null){
            int ingredientId = intent.getIntExtra(getString(R.string.ingredientIdKey), 0);
            int recipeId = intent.getIntExtra(getString(R.string.recipeKey), 0);
            String jsonRes = intent.getStringExtra(getString(R.string.jsonResKey));
            try {
                String description = JsonUtils.getRecipeDescription(jsonRes, recipeId, ingredientId);
                instructionText.setText(description);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // ExoPlayer
        PlayerView mPlayerView = findViewById(R.id.playerView);
        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4";
        // All these classes are class memeber attribute
        SimpleExoPlayer mSimpleExoPlayer = new SimpleExoPlayer.Builder(this)
                .setTrackSelector(new DefaultTrackSelector(this))
                .setLoadControl(new DefaultLoadControl()).build();
        MediaItem mediaItem = MediaItem.fromUri(url);
        mSimpleExoPlayer.setMediaItem(mediaItem);
        mSimpleExoPlayer.prepare();
        mSimpleExoPlayer.setPlayWhenReady(true);
        mSimpleExoPlayer.play();
        mPlayerView.setPlayer(mSimpleExoPlayer);
        ImageView backArrow = findViewById(R.id.goToPrevious);
        backArrow.setOnClickListener(v -> Toast.makeText(InstructionsActivity.this, "Go to previous step", Toast.LENGTH_SHORT).show());
        ImageView nextArrow = findViewById(R.id.goToNext);
        nextArrow.setOnClickListener(v -> Toast.makeText(InstructionsActivity.this, "Go to next step", Toast.LENGTH_SHORT).show());
    }


}