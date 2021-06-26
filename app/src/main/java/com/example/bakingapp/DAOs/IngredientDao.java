package com.example.bakingapp.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bakingapp.Entries.IngredientEntry;

import java.util.List;
@Dao
public interface IngredientDao {
    @Query("SELECT * FROM IngredientEntry ORDER BY id")
    LiveData<List<IngredientEntry>> loadAllIngredients();
    @Insert()
    void addAllIngredients(List<IngredientEntry> entries);
    @Query("SELECT * FROM IngredientEntry WHERE id = :id")
    LiveData<IngredientEntry> loadIngredientById(int id);
}
