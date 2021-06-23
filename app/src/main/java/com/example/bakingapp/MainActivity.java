package com.example.bakingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bakingapp.InternetUtils.NetworkUtils;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private final int RECIPE_LOADER_ID = 10;
    private TextView responseTextView;
    private ProgressBar waitTillResponseProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseTextView = findViewById(R.id.tv);
        waitTillResponseProgressBar = findViewById(R.id.pb);


        LoaderManager.getInstance(this).initLoader(RECIPE_LOADER_ID, null, this).forceLoad();
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
                        String response = "";
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
                        String response = "";
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

        waitTillResponseProgressBar.setVisibility(View.INVISIBLE);
        responseTextView.setText(data);
        Toast.makeText(this, "Loader finished", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

}