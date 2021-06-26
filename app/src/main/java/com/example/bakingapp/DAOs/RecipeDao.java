package com.example.bakingapp.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bakingapp.Entries.RecipeEntry;

import java.util.List;
@Dao
public interface RecipeDao {
    @Query("SELECT * FROM RecipeEntry ORDER BY id")
    LiveData<List<RecipeEntry>> loadAllRecipes();
    @Insert()
    void addAllRecipe(List<RecipeEntry> entries);
    @Query("SELECT * FROM RecipeEntry WHERE id = :id")
    LiveData<RecipeEntry> loadRecipeById(int id);
}
