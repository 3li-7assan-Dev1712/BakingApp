package com.example.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bakingapp.Adapters.StepsAdapter;
import com.example.bakingapp.ViewModels.LoadStepsViewModel;
import com.example.bakingapp.ViewModels.LoadStepsViewModelFactory;
import com.example.bakingapp.database.BakingDatabase;

public class CookRecipeActivity extends AppCompatActivity implements StepsAdapter.ViewStepInterface {
    private StepsAdapter stepsAdapter;
    private int recipeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_recipe);
        // RecyclerView Implementation
        RecyclerView stepsRecycler = findViewById(R.id.stepsRecycler);
        stepsRecycler.setLayoutManager(new LinearLayoutManager(this));
        stepsRecycler.setHasFixedSize(true);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(getString(R.string.open_cook_activity_key)))
            recipeId = intent.getIntExtra(getString(R.string.open_cook_activity_key), 0) +1; /*id in the database start from 1*/
        stepsAdapter = new StepsAdapter(this, this);
        stepsRecycler.setAdapter(stepsAdapter);
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
            Toast.makeText(CookRecipeActivity.this, "setAdapter and entries number is: " + entries.size(), Toast.LENGTH_SHORT).show();
            Log.d("any", "setAdapter woks");
            stepsAdapter.setStepsList(entries);
        });
        // See Ingredients Button Implementation
        Button seeIngredientsBtn = findViewById(R.id.seeIngredientsButton);
        seeIngredientsBtn.setOnClickListener(v -> {
            Intent openIngredientsActivity = new Intent(CookRecipeActivity.this, IngredientsActivity.class);
            openIngredientsActivity.putExtra(getString(R.string.recipeKey), recipeId);
            startActivity(openIngredientsActivity);
        });
    }

    @Override
    public void onClickStep(int id) {
        Toast.makeText(this, "clicked " + id, Toast.LENGTH_SHORT).show();
        Intent goToInstructions = new Intent(CookRecipeActivity.this, InstructionsActivity.class);
        goToInstructions.putExtra(getString(R.string.ingredientIdKey), id);
        goToInstructions.putExtra(getString(R.string.recipeKey), recipeId);
        startActivity(goToInstructions);
    }
}