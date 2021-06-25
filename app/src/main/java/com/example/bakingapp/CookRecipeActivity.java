package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bakingapp.Adapters.StepsAdapter;
import com.example.bakingapp.JsonRef.JsonUtils;

import java.util.List;

public class CookRecipeActivity extends AppCompatActivity implements StepsAdapter.ViewStepInterface {

    private static String jsonRes;
    private StepsAdapter stepsAdapter;
    private List<String> steps;
    private int recipeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_recipe);
        // RecyclerView Implementation
        RecyclerView stepsRecycler = findViewById(R.id.stepsRecycler);
        stepsRecycler.setLayoutManager(new LinearLayoutManager(this));
        stepsRecycler.setHasFixedSize(true);
        Intent intent = getIntent();
        ;
        if (intent != null && intent.hasExtra(getString(R.string.open_cook_activity_key)))
            recipeId = intent.getIntExtra(getString(R.string.open_cook_activity_key), 0);
        try {
            steps= JsonUtils.getRecipeSteps(jsonRes, recipeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        stepsAdapter = new StepsAdapter(this, this, steps);
        stepsRecycler.setAdapter(stepsAdapter);

        // See Ingredients Button Implementation
        Button seeIngredientsBtn = findViewById(R.id.seeIngredientsButton);
        seeIngredientsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openIngredientsActivity = new Intent(CookRecipeActivity.this, IngredientsActivity.class);
                openIngredientsActivity.putExtra(getString(R.string.recipeKey), recipeId);
                openIngredientsActivity.putExtra(getString(R.string.jsonResKey), jsonRes);
                startActivity(openIngredientsActivity);
            }
        });
    }

    public static void setJsonRes(String data){
        jsonRes = data;
    }
    @Override
    public void onClickStep(int id) {
        Toast.makeText(this, "clicked " + id, Toast.LENGTH_SHORT).show();
        Intent goToInstructions = new Intent(CookRecipeActivity.this, InstructionsActivity.class);
        goToInstructions.putExtra(getString(R.string.ingredientIdKey), id);
        goToInstructions.putExtra(getString(R.string.recipeKey), recipeId);
        goToInstructions.putExtra(getString(R.string.jsonResKey), jsonRes);
        startActivity(goToInstructions);
    }
}