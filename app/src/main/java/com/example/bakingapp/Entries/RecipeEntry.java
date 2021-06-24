package com.example.bakingapp.Entries;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class RecipeEntry {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo (name = "recipe_name")
    private String recipeName;
    @ColumnInfo (name = "recipe_id")
    private int recipeId;

    public RecipeEntry(int id, String recipeName, int recipeId){
        this.id = id;
        this.recipeName = recipeName;
        this.recipeId = recipeId;
    }

    @Ignore
    public RecipeEntry(String recipeName, int recipeId){
        this.recipeName = recipeName;
        this.recipeId = recipeId;
    }
    public int getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public int getRecipeId() {
        return recipeId;
    }
}
