package com.example.bakingapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bakingapp.Entries.RecipeEntry;
import com.example.bakingapp.database.BakingDatabase;

import java.util.List;

public class RecipesViewModel extends AndroidViewModel {


    private LiveData<List<RecipeEntry>> recipeEntryLiveData;
    public RecipesViewModel(@NonNull Application application) {
        super(application);
        BakingDatabase bakingDatabase = BakingDatabase.getsInstance(this.getApplication());
        recipeEntryLiveData = bakingDatabase.recipeDao().loadAllRecipes();

    }

    public LiveData<List<RecipeEntry>> getRecipeEntryLiveData() {
        return recipeEntryLiveData;
    }
}
