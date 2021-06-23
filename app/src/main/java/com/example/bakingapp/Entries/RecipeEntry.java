package com.example.bakingapp.Entries;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RecipeEntry {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo (name = "recipe_name")
    private String recipeName;
    @ColumnInfo (name = "recipe_id")
    private int recipeId;
}
