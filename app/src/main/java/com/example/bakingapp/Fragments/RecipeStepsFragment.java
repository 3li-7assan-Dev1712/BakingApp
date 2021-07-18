package com.example.bakingapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.transition.Slide;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Adapters.StepsAdapter;
import com.example.bakingapp.Entries.StepsEntry;
import com.example.bakingapp.IngredientsActivity;
import com.example.bakingapp.R;
import com.google.android.exoplayer2.util.Util;

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
        Display display =  ((WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rot = display.getRotation();
        switch (rot){
            case Surface.ROTATION_0:
                stepsAdapter.setmLandscapeModed(false);
                break;
            case Surface.ROTATION_90:
                stepsAdapter.setmLandscapeModed(true);
                break;
        }
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
