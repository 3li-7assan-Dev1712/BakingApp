package com.example.bakingapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bakingapp.Entries.StepsEntry;
import com.example.bakingapp.database.BakingDatabase;

import java.util.List;

public class LoadStepsViewModel extends ViewModel {

    private LiveData<List<StepsEntry>> listStepsLiveData;

    public LoadStepsViewModel(int recipeId, BakingDatabase bakingDatabase) {
        listStepsLiveData= bakingDatabase.stepsDao().loadAllSteps(recipeId);
    }

    public LiveData<List<StepsEntry>> getListStepsLiveData() {
        return listStepsLiveData;
    }
}
