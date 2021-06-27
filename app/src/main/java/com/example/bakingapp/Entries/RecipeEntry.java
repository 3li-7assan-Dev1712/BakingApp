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

    public RecipeEntry(int id, String recipeName){
        this.id = id;
        this.recipeName = recipeName;
    }

    @Ignore
    public RecipeEntry(String recipeName){
        this.recipeName = recipeName;
    }
    public int getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

}
