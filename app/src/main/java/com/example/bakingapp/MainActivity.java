package com.example.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Adapters.RecipeAdapter;
import com.example.bakingapp.Entries.RecipeEntry;
import com.example.bakingapp.InternetUtils.NetworkUtils;
import com.example.bakingapp.JsonRef.JsonUtils;
import com.example.bakingapp.ViewModels.RecipesViewModel;
import com.example.bakingapp.database.AppExcecuters;
import com.example.bakingapp.database.BakingDatabase;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, RecipeAdapter.ChooseRecipeInterface{

    private static final String TAG = MainActivity.class.getSimpleName();
    private final int RECIPE_LOADER_ID = 10;
    private RecipeAdapter recipeAdapter;
    private ProgressBar waitTillResponseProgressBar;
    private BakingDatabase mBakingDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // construct the mBakingDatabase
        mBakingDatabase = BakingDatabase.getsInstance(this);
        waitTillResponseProgressBar = findViewById(R.id.pb);
        RecyclerView recipesNameRecycler = findViewById(R.id.recipeNameRecyclerView);
        /*
        the recipes data are fixed and so recycler view will have fixed size all the time
        so for performance I going to setHasFixedSize() method to true.
         */
        recipesNameRecycler.setHasFixedSize(true);
        recipesNameRecycler.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(this, this);
        recipesNameRecycler.setAdapter(recipeAdapter);

        /*
        using the loader to get the recipes data from the internet
        in a background thread
         */
        fillDatabaseIfEmpty();
        LoaderManager.getInstance(this).initLoader(RECIPE_LOADER_ID, null, this).forceLoad();
    }

    private void fillDatabaseIfEmpty() {
        // add them to the build.gradle file
        // //                lifecycle - view model
        //    implementation "androidx.lifecycle:lifecycle-runtime:$lifecycle_version"
        //    implementation "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {

        switch (id){
            case RECIPE_LOADER_ID:
                return new AsyncTaskLoader<String>(this) {
                    @Override
                    protected void onStartLoading() {
                        waitTillResponseProgressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Starting loader", Toast.LENGTH_SHORT).show();
                    }

                    @Nullable
                    @Override
                    public String loadInBackground() {
                        String response;
                        try{
                            assert NetworkUtils.RECIPE_URL != null;
                            response = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.RECIPE_URL);
                            Log.d("loadInBack", "load in background works correctly");
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                        return response;
                    }

                    @Nullable
                    @Override
                    protected String onLoadInBackground() {
                        String response;
                        try{
                            assert NetworkUtils.RECIPE_URL != null;
                            response = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.RECIPE_URL);
                            Log.d("loadInBack", "load in background works correctly");
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                        return response;
                    }
                };
            default:
                throw new RuntimeException("Loader Not Implemented: " + RECIPE_LOADER_ID);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

        Log.d(TAG, "loader has finished");
        waitTillResponseProgressBar.setVisibility(View.INVISIBLE);
        CookRecipeActivity.setJsonRes(data);
        try {
            List<RecipeEntry> entries = JsonUtils.getRecipeEntries(data);
            // set the data to the adapter
            AppExcecuters.getsInstance().recipeIO().execute(() -> {

                // fill the database
                mBakingDatabase.recipeDao().addAllRecipe(entries);
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    public void onChooseRecipe(int id) {
        Intent openCookRecipeActivity = new Intent(MainActivity.this, CookRecipeActivity.class);
        openCookRecipeActivity.putExtra(getString(R.string.open_cook_activity_key), id);
        startActivity(openCookRecipeActivity);
    }
}