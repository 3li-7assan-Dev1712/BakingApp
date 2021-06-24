package com.example.bakingapp.Entries;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class IngredientEntry {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String measure;
    private String ingredient;
    private int quantity;
    @ColumnInfo (name = "recipe_id")
    private int recipeId;

    public IngredientEntry(int id, String measure, String ingredient, int quantity, int recipeId) {
        this.id = id;
        this.measure = measure;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.recipeId = recipeId;
    }

    @Ignore
    public IngredientEntry(String measure, String ingredient, int quantity, int recipeId) {
        this.measure = measure;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.recipeId = recipeId;
    }

    public int getId() {
        return id;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getRecipeId() {
        return recipeId;
    }
}
