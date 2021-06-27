package com.example.bakingapp.ViewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bakingapp.database.BakingDatabase;

public class LoadStepsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    int recipeId;
    BakingDatabase bakingDatabase;

    public LoadStepsViewModelFactory(int recipeId, BakingDatabase bakingDatabase) {
        this.recipeId = recipeId;
        this.bakingDatabase = bakingDatabase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoadStepsViewModel(recipeId, bakingDatabase);
    }
}
