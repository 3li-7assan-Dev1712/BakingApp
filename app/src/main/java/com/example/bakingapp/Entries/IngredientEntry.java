package com.example.bakingapp.Entries;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
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

    /*
    -------------------------------------------------------------|
    |  id   |     measure| ingredient    | quantity | recipe_id  |                                                                              |
    |------------------------------------------------------------|
    |-------------------------------------------------------------|
    |   1 |      3 | water, beans  |      3   |    1 |                                                                              |
    |------------------------------------------------------------|
     */
}
