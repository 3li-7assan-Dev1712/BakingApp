package com.example.bakingapp.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bakingapp.Entries.StepsEntry;

import java.util.List;
@Dao
public interface StepsDao {
    @Query("SELECT * FROM StepsEntry WHERE recipeId= :recipeId ORDER BY id")
    LiveData<List<StepsEntry>> loadAllSteps(int recipeId);
    @Insert()
    void addAllSteps(List<StepsEntry> entries);

}
