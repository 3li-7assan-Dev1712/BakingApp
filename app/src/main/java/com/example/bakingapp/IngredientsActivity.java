package com.example.bakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.bakingapp.Adapters.IngredientsAdapter;
import com.example.bakingapp.Entries.IngredientEntry;
import com.example.bakingapp.Entries.StepsEntry;
import com.example.bakingapp.JsonRef.JsonUtils;
import com.example.bakingapp.ViewModels.LoadIngredientsViewModel;
import com.example.bakingapp.ViewModels.LoadIngredientsViewModelFactory;
import com.example.bakingapp.ViewModels.LoadStepsViewModel;
import com.example.bakingapp.ViewModels.LoadStepsViewModelFactory;
import com.example.bakingapp.database.BakingDatabase;

import java.util.List;

public class IngredientsActivity extends AppCompatActivity {

    private IngredientsAdapter ingredientsAdapter;
    private List<IngredientEntry> ingredientEntries;
    private int recipeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        RecyclerView ingredientsRecycler = findViewById(R.id.ingredientsRecycler);
        Intent intent  = getIntent();
        if (intent != null){
            recipeId = intent.getIntExtra(getString(R.string.recipeKey), 0);
        }
        ingredientsAdapter = new IngredientsAdapter(this, ingredientEntries);
        ingredientsRecycler.setAdapter(ingredientsAdapter);
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(this));
        BakingDatabase mDb = BakingDatabase.getsInstance(this);
        LoadIngredientsViewModelFactory factory = new LoadIngredientsViewModelFactory(recipeId, mDb);
        LoadIngredientsViewModel viewModel = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) factory.create(LoadIngredientsViewModel.class);
            }
        }.create(LoadIngredientsViewModel.class);
       viewModel.getIngredientEntriesLiveData().observe(this, ingredientEntries -> {
           /*
           ingredientEntries -> is java 8 syntax you can use new Observer<List<IngredientEntry>>()
           for older version in java *_-
            */
           ingredientsAdapter.setIngredientsList(ingredientEntries);
       });
    }
}