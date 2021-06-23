package com.example.bakingapp.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bakingapp.Entries.StepsEntry;

import java.util.List;
@Dao
public interface StepsDao {
    @Query("SELECT * FROM StepsEntry ORDER BY id")
    LiveData<List<StepsEntry>> loadAllSteps();
    @Insert()
    void addRecipe(StepsEntry entry);
    @Query("SELECT * FROM RecipeEntry WHERE id = :id")
    LiveData<StepsEntry> loadStepById(int id);
}
