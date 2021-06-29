package com.example.bakingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Adapters.StepsAdapter;
import com.example.bakingapp.Entries.StepsEntry;
import com.example.bakingapp.IngredientsActivity;
import com.example.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsFragment extends Fragment {
    private List<StepsEntry> stepsEntries;
    private int recipeId;

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setStepsEntries(List<StepsEntry> stepsEntries) {
        this.stepsEntries = stepsEntries;
    }

    public RecipeStepsFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.recipe_steps_fragment, container, false);
        if (savedInstanceState != null){
            stepsEntries= savedInstanceState.getParcelableArrayList("ali");
            recipeId = savedInstanceState.getInt(getString(R.string.recipeKey));
        }
        RecyclerView recyclerView = view.findViewById(R.id.stepsRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        StepsAdapter stepsAdapter = new StepsAdapter(getContext(), (StepsAdapter.ViewStepInterface) getContext());
        stepsAdapter.setStepsList(stepsEntries);
        recyclerView.setAdapter(stepsAdapter);

        Button btn = view.findViewById(R.id.seeIngredientsButton);
        btn.setOnClickListener(v -> {
            if (getContext() != null) {
                Intent openIngredientsIntent = new Intent(getContext(), IngredientsActivity.class);
                openIngredientsIntent.putExtra(getString(R.string.recipeKey), recipeId);
                getContext().startActivity(openIngredientsIntent);
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("ali", (ArrayList<? extends Parcelable>) stepsEntries);
        outState.putInt(getString(R.string.recipeKey), recipeId);
        super.onSaveInstanceState(outState);
    }
}
