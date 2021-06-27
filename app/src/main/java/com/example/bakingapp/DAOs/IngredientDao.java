package com.example.bakingapp.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bakingapp.Entries.IngredientEntry;

import java.util.List;
@Dao
public interface IngredientDao {
    @Query("SELECT * FROM IngredientEntry WHERE recipe_id= :recipeId ORDER BY id")
    LiveData<List<IngredientEntry>> loadAllIngredients(int recipeId);
    @Insert()
    void addAllIngredients(List<IngredientEntry> entries);
}
