package com.example.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.bakingapp.ViewModels.LoadStepsViewModel;
import com.example.bakingapp.ViewModels.LoadStepsViewModelFactory;
import com.example.bakingapp.database.BakingDatabase;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

public class InstructionsActivity extends AppCompatActivity {

    private int ingredientId;
    private int stepsNumber;
    private SimpleExoPlayer mSimpleExoPlayer;
    private int recipeId;
    private ImageView noVideoImage;
    private TextView noVideoTextView;
    private TextView instructionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        instructionText = findViewById(R.id.instructionsTextView);
        noVideoTextView = findViewById(R.id.noVideoText);
        noVideoImage = findViewById(R.id.noVideoImage);
        Intent intent = getIntent();
        if (intent != null){
            ingredientId = intent.getIntExtra(getString(R.string.ingredientIdKey), 0);
            recipeId = intent.getIntExtra(getString(R.string.recipeKey), 0);
        }
        BakingDatabase mDb = BakingDatabase.getsInstance(this);
        LoadStepsViewModelFactory factory = new LoadStepsViewModelFactory(recipeId, mDb);
        LoadStepsViewModel viewModel = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) factory.create(LoadStepsViewModel.class);
            }
        }.create(LoadStepsViewModel.class);

        viewModel.getListStepsLiveData().observe(this, entries -> {
            Log.d("any", "setAdapter woks");
            String videoUrl = entries.get(ingredientId).getVideoUrl();
            String description = entries.get(ingredientId).getDescription();
            stepsNumber = entries.size();

            populateUi(videoUrl, description);
        });
        ImageView backArrow = findViewById(R.id.goToPrevious);
        ImageView nextArrow = findViewById(R.id.goToNext);
        Intent openTheSameActivity = new Intent(InstructionsActivity.this, InstructionsActivity.class);
        backArrow.setOnClickListener(v -> {
           ingredientId-=1;
           if (ingredientId < 0){
               Toast.makeText(this, "There's no previous steps", Toast.LENGTH_SHORT).show();
               ingredientId+=1;
           }else {
               openTheSameActivity.putExtra(getString(R.string.ingredientIdKey), ingredientId);
               openTheSameActivity.putExtra(getString(R.string.recipeKey), recipeId);
               // kill the current Activity before navigating to second one for two reason:
                   // 1- It's no longer need and the user will not expect they need to return to it (will use the navigation arrows)
                  //  2- free up the memory for smooth user experience.
               finish();
               startActivity(openTheSameActivity);
           }
       });
        nextArrow.setOnClickListener(v -> {
            if (stepsNumber == 0) return;
            ingredientId+=1;
            if (ingredientId >= stepsNumber){
                Toast.makeText(this, "There's no next steps to view, this is the last step", Toast.LENGTH_SHORT).show();
                ingredientId-=1;
            }else {
                openTheSameActivity.putExtra(getString(R.string.ingredientIdKey), ingredientId);
                openTheSameActivity.putExtra(getString(R.string.recipeKey), recipeId);
                finish();
                startActivity(openTheSameActivity);
            }
        });

    }

    private void populateUi(String videoUrl, String description) {
        instructionText.setText(description);
        // ExoPlayer implementation
        PlayerView mPlayerView = findViewById(R.id.playerView);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT < 23)
            mSimpleExoPlayer.release();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23)
            mSimpleExoPlayer.release();
    }
}