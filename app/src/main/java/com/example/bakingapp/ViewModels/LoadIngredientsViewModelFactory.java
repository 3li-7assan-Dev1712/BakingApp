package com.example.bakingapp.ViewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bakingapp.database.BakingDatabase;

public class LoadIngredientsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private int recipeId;
    private BakingDatabase bakingDatabase;

    public LoadIngredientsViewModelFactory(int recipeId, BakingDatabase bakingDatabase) {
        this.recipeId = recipeId;
        this.bakingDatabase = bakingDatabase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoadIngredientsViewModel(recipeId, bakingDatabase);
    }
}
