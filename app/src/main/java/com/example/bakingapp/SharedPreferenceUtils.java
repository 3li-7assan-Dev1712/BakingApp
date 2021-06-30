package com.example.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {
    public   static final int defaultValue = -1;

    public static void setDefaultValue(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.baking_app_shared_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.recipeKey), defaultValue);
        editor.apply();
    }

    public static void updateFavoriteRecipe(Context context, int favoriteRecipeId){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.baking_app_shared_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.recipeKey), favoriteRecipeId);
        editor.apply();
    }

    public static int getFavoriteRecipe(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.baking_app_shared_key), Context.MODE_PRIVATE);
        return sharedPreferences.getInt(context.getString(R.string.recipeKey), defaultValue);
    }

}