package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.bakingapp.Adapters.IngredientsAdapter;
import com.example.bakingapp.Entries.IngredientEntry;
import com.example.bakingapp.JsonRef.JsonUtils;

import java.util.List;

public class IngredientsActivity extends AppCompatActivity {

    private IngredientsAdapter ingredientsAdapter;
    private List<IngredientEntry> ingredientEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        RecyclerView ingredientsRecycler = findViewById(R.id.ingredientsRecycler);
        Intent intent  = getIntent();
        if (intent != null){
            String jsonRes = intent.getStringExtra(getString(R.string.jsonResKey));
            int recipeId = intent.getIntExtra(getString(R.string.recipeKey), 0);
            try {
                ingredientEntries = JsonUtils.getRecipeIngredients(jsonRes, recipeId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ingredientsAdapter = new IngredientsAdapter(this, ingredientEntries);
        ingredientsRecycler.setAdapter(ingredientsAdapter);
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
}