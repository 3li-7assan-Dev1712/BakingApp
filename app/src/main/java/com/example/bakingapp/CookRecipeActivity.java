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

public class CookRecipeActivity extends AppCompatActivity implements StepsAdapter.ViewStepInterface {

    private StepsAdapter stepsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_recipe);
        // RecyclerView Implementation
        RecyclerView stepsRecycler = findViewById(R.id.stepsRecycler);
        stepsRecycler.setLayoutManager(new LinearLayoutManager(this));
        stepsRecycler.setHasFixedSize(true);
        stepsAdapter = new StepsAdapter(this, this);
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

    @Override
    public void onClickStep(int id) {
        Toast.makeText(this, "clicked " + id, Toast.LENGTH_SHORT).show();
    }
}