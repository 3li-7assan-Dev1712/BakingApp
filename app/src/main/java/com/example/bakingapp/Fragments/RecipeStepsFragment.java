package com.example.bakingapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Adapters.StepsAdapter;
import com.example.bakingapp.CookRecipeActivity;
import com.example.bakingapp.Entries.StepsEntry;
import com.example.bakingapp.R;
import com.example.bakingapp.ViewModels.LoadStepsViewModel;
import com.example.bakingapp.ViewModels.LoadStepsViewModelFactory;
import com.example.bakingapp.database.BakingDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsFragment extends Fragment {
    private List<StepsEntry> stepsEntries;

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
        }
        RecyclerView recyclerView = view.findViewById(R.id.stepsRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        StepsAdapter stepsAdapter = new StepsAdapter(getContext(), (StepsAdapter.ViewStepInterface) getContext());
        stepsAdapter.setStepsList(stepsEntries);
        recyclerView.setAdapter(stepsAdapter);
//        BakingDatabase mDb = BakingDatabase.getsInstance(getContext());
//        LoadStepsViewModelFactory factory = new LoadStepsViewModelFactory(recipeId, mDb);
//        LoadStepsViewModel viewModel = new ViewModelProvider.Factory() {
//            @NonNull
//            @Override
//            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//                return (T) factory.create(LoadStepsViewModel.class);
//            }
//        }.create(LoadStepsViewModel.class);
//        viewModel.getListStepsLiveData().observe((LifecycleOwner) getContext(), stepsEntries -> {
//            RecyclerView recyclerView = view.findViewById(R.id.stepsRecycler);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//            StepsAdapter stepsAdapter = new StepsAdapter(getContext(), (StepsAdapter.ViewStepInterface) getContext());
//            stepsAdapter.setStepsList(stepsEntries);
//            recyclerView.setAdapter(stepsAdapter);
//        });

        Button btn = view.findViewById(R.id.seeIngredientsButton);
        btn.setOnClickListener(v ->
                Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show());
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("ali", (ArrayList<? extends Parcelable>) stepsEntries);
        super.onSaveInstanceState(outState);
    }
}
