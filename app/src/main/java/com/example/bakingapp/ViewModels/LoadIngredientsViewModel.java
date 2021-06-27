package com.example.bakingapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bakingapp.Entries.IngredientEntry;
import com.example.bakingapp.database.BakingDatabase;

import java.util.List;

public class LoadIngredientsViewModel extends ViewModel {


    private LiveData<List<IngredientEntry>> ingredientEntriesLiveData;
    public LoadIngredientsViewModel(int recipeId, BakingDatabase bakingDatabase) {
        ingredientEntriesLiveData = bakingDatabase.ingredientDao().loadAllIngredients(recipeId);
    }

    public LiveData<List<IngredientEntry>> getIngredientEntriesLiveData() {
        return ingredientEntriesLiveData;
    }
}
