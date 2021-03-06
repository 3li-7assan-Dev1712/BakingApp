package com.example.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.bakingapp.Entries.StepsEntry;
import com.example.bakingapp.Fragments.DescriptionFragment;
import com.example.bakingapp.Fragments.StepVideoFragment;

import java.util.ArrayList;
import java.util.List;

public class InstructionsActivity extends AppCompatActivity{

    private int ingredientId;
    private int stepsNumber;
    private List<StepsEntry> stepsEntries;
    private boolean mLandscapeMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        mLandscapeMode = findViewById(R.id.navigationLinearLayout) == null;
        Intent intent = getIntent();
        if (intent != null){
            ingredientId = intent.getIntExtra(getString(R.string.ingredientIdKey), 0);
            this.stepsEntries = intent.getParcelableArrayListExtra("ali");
            assert stepsEntries != null;
            stepsNumber = stepsEntries.size();
        }
        String videoUrl = stepsEntries.get(ingredientId).getVideoUrl();
        String description = stepsEntries.get(ingredientId).getDescription();
        populateUi(videoUrl, description, savedInstanceState);
        FragmentManager manager = getSupportFragmentManager();
        /*if the orientation is not landscape we can create our navigation arrows*/
        if (!mLandscapeMode) {
            ImageView backArrow = findViewById(R.id.goToPrevious);
            ImageView nextArrow = findViewById(R.id.goToNext);
            Intent openTheSameActivity = new Intent(InstructionsActivity.this, InstructionsActivity.class);
            backArrow.setOnClickListener(v -> {
                ingredientId -= 1;
                if (ingredientId < 0) {
                    Toast.makeText(this, "There's no previous steps", Toast.LENGTH_SHORT).show();
                    ingredientId += 1;
                } else {
                    DescriptionFragment descriptionFragment = new DescriptionFragment();
                    descriptionFragment.setDescription(stepsEntries.get(ingredientId).getDescription());
                    StepVideoFragment stepVideoFragment = new StepVideoFragment();
                    stepVideoFragment.setVideoUrl(stepsEntries.get(ingredientId).getVideoUrl());
                    manager.beginTransaction().replace(R.id.frameLayout, stepVideoFragment)
                    .replace(R.id.descriptionFragment, descriptionFragment).commit();
                }
            });
            nextArrow.setOnClickListener(v -> {
                if (stepsNumber == 0) return;
                ingredientId += 1;
                if (ingredientId >= stepsNumber) {
                    Toast.makeText(this, "There's no next steps to view, this is the last step", Toast.LENGTH_SHORT).show();
                    ingredientId -= 1;
                } else {
                    DescriptionFragment descriptionFragment = new DescriptionFragment();
                    descriptionFragment.setDescription(stepsEntries.get(ingredientId).getDescription());
                    StepVideoFragment stepVideoFragment = new StepVideoFragment();
                    stepVideoFragment.setVideoUrl(stepsEntries.get(ingredientId).getVideoUrl());
                    manager.beginTransaction().replace(R.id.frameLayout, stepVideoFragment)
                            .replace(R.id.descriptionFragment, descriptionFragment).commit();
                }
            });
        }
    }

    private void populateUi(String videoUrl, String description, Bundle saveInstanceState) {
        FragmentManager manager = getSupportFragmentManager();
        /*in the landscape mode we don't have description, so we do so in the vertical mode*/
        if (!mLandscapeMode) {
            DescriptionFragment descriptionFragment = new DescriptionFragment();
            descriptionFragment.setDescription(description);
            manager.beginTransaction().add(R.id.descriptionFragment, descriptionFragment).commit();
        }
        // ExoPlayer implementation

        if (saveInstanceState == null) {
            StepVideoFragment stepVideoFragment = new StepVideoFragment();
            stepVideoFragment.setVideoUrl(videoUrl);
            manager.beginTransaction().add(R.id.frameLayout, stepVideoFragment).commit();
        }
    }

}