package com.example.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bakingapp.Adapters.StepsAdapter;
import com.example.bakingapp.Entries.StepsEntry;
import com.example.bakingapp.Fragments.DescriptionFragment;
import com.example.bakingapp.Fragments.RecipeStepsFragment;
import com.example.bakingapp.Fragments.StepVideoFragment;
import com.example.bakingapp.ViewModels.LoadStepsViewModel;
import com.example.bakingapp.ViewModels.LoadStepsViewModelFactory;
import com.example.bakingapp.database.BakingDatabase;

import java.util.ArrayList;
import java.util.List;

public class CookRecipeActivity extends AppCompatActivity implements StepsAdapter.ViewStepInterface {
    private int recipeId;
    private boolean mTowPane;
    private List<StepsEntry>  stepsEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_recipe);
        mTowPane = findViewById(R.id.separaterView) != null;
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(getString(R.string.open_cook_activity_key)))
            recipeId = intent.getIntExtra(getString(R.string.open_cook_activity_key), 0) +1; /*id in the database start from 1*/
        Log.d("CookReceipe.class", "recipeId = " + recipeId);
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
            this.stepsEntries = entries;
            if (savedInstanceState == null) {
                RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
                recipeStepsFragment.setStepsEntries(entries);
                getSupportFragmentManager().beginTransaction().add(R.id.recipeStepsFrag, recipeStepsFragment).commit();
                Log.d("any", "setAdapter woks");
            }
            /*Any process interacts with the UI should be in the live data thread*/
            if (mTowPane){
                /*Description text implementation*/
                DescriptionFragment descriptionFragment = new DescriptionFragment();
                descriptionFragment.setDescription(entries.get(0).getDescription());
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().add(R.id.descriptionFragment, descriptionFragment).commit();
                /*ExoPlayer implementation*/
                StepVideoFragment stepVideoFragment= new StepVideoFragment();
                String videoUrl = entries.get(0).getVideoUrl();
                stepVideoFragment.setVideoUrl(videoUrl);
                manager.beginTransaction().add(R.id.video_step_fragment, stepVideoFragment).commit();
            }
        });
    }
    @Override
    public void onClickStep(int id) {
        if (mTowPane){
            /*will be implemented in the next commit*/

        }else {
            Toast.makeText(this, "clicked " + id, Toast.LENGTH_SHORT).show();
            Intent goToInstructions = new Intent(CookRecipeActivity.this, InstructionsActivity.class);
            goToInstructions.putExtra(getString(R.string.ingredientIdKey), id);
            goToInstructions.putParcelableArrayListExtra("ali", (ArrayList<? extends Parcelable>) stepsEntries);
            startActivity(goToInstructions);
        }
    }
}