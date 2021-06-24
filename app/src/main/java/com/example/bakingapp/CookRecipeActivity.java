package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CookRecipeActivity extends AppCompatActivity {

    private StepsAdapter stepsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_recipe);
        // RecyclerView Implementation
        RecyclerView stepsRecycler = findViewById(R.id.stepsRecycler);
        stepsRecycler.setLayoutManager(new LinearLayoutManager(this));
        stepsRecycler.setHasFixedSize(true);
        stepsAdapter = new StepsAdapter(this);
        stepsRecycler.setAdapter(stepsAdapter);

        // See Ingredients Button Implementation
        Button seeIngredientsBtn = findViewById(R.id.seeIngredientsButton);
        seeIngredientsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openIngredientsActivity = new Intent(CookRecipeActivity.this, IngredientsActivity.class);
                startActivity(openIngredientsActivity);
            }
        });
    }
}