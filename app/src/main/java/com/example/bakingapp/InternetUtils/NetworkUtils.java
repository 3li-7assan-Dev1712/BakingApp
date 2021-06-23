package com.example.bakingapp.InternetUtils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String RECIPES_URL_STRING ="https://go.udacity.com/android-baking-app-json";
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static URL getRecipeUrl(){

        Uri recipeUri = Uri.parse(RECIPES_URL_STRING).buildUpon().build();
        Log.d(TAG, recipeUri.toString());
        try {
            URL recipeUrl = new URL(recipeUri.toString());
            Log.v(TAG, "URL: " + recipeUrl);
            return recipeUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static final URL RECIPE_URL = getRecipeUrl();
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }
}
