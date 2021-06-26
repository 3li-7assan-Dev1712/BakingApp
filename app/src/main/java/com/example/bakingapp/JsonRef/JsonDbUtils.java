package com.example.bakingapp.JsonRef;

import com.example.bakingapp.Entries.IngredientEntry;
import com.example.bakingapp.Entries.RecipeEntry;
import com.example.bakingapp.Entries.StepsEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonDbUtils {

    public static List<RecipeEntry> getRecipeEntries(String jsonResponse) throws JSONException {
        List<RecipeEntry> recipeEntries = new ArrayList<>();
        JSONArray recipesArray = new JSONArray(jsonResponse);
        for (int i = 0; i < recipesArray.length(); i ++){
            JSONObject recipeObject = recipesArray.getJSONObject(i);
            String recipeName = recipeObject.getString(JsonConstants.RECIPE_NAME);
            recipeEntries.add(new RecipeEntry(recipeName));
        }
        if (recipeEntries.size() != 0)
            return recipeEntries;
        else
            throw new JSONException("Couldn't read from json");
    }

    public static List<StepsEntry> getAllSteps(String jsonResponse) throws JSONException {
        List<StepsEntry> stepsEntries = new ArrayList<>();
        JSONArray recipesArray = new JSONArray(jsonResponse);
        for (int i = 0; i < recipesArray.length(); i ++){
            JSONObject recipeObject = recipesArray.getJSONObject(i);
            int recipeId = recipeObject.getInt(JsonConstants.RECIPE_ID);
            JSONArray stepsArray = recipeObject.getJSONArray(JsonConstants.RECIPE_STEPS);
            for (int a = 0; a < stepsArray.length(); a++){
                JSONObject stepObj = stepsArray.getJSONObject(a);
                String shortDescription = stepObj.getString(JsonConstants.SHORT_DESCRIPTION);
                String description = stepObj.getString(JsonConstants.DESCRIPTION);
                String videoUrl = stepObj.getString(JsonConstants.VIDEO_URL);
                String imageThumbnail = stepObj.getString(JsonConstants.THUMBNAIL_URL);
                //String shortDescription, String description, String videoUrl, String thumbnailUrl, int recipeId)
                StepsEntry entry = new StepsEntry(shortDescription, description, videoUrl, imageThumbnail, recipeId);
                stepsEntries.add(entry);
            }
        }
        if (stepsEntries.size() != 0)
            return stepsEntries;
        else
            throw new JSONException("Couldn't read from json");
    }
    public static List<IngredientEntry> getAllIngredients(String jsonResponse) throws JSONException {
        List<IngredientEntry> ingredientEntries = new ArrayList<>();
        JSONArray recipesArray = new JSONArray(jsonResponse);
        for (int i = 0; i < recipesArray.length(); i ++){
            JSONObject recipeObject = recipesArray.getJSONObject(i);
            int recipeId = recipeObject.getInt(JsonConstants.RECIPE_ID);
            JSONArray ingredientsArray = recipeObject.getJSONArray(JsonConstants.RECIPE_INGREDIENT);
            for (int a = 0; a < ingredientsArray.length(); a++){
                JSONObject ingredientsObj = ingredientsArray.getJSONObject(a);
                String ingredient = ingredientsObj.getString(JsonConstants.INGREDIENT);
                String measure = ingredientsObj.getString(JsonConstants.MEASURE);
                float quantity = (float) ingredientsObj.getDouble(JsonConstants.QUANTITY);
                IngredientEntry entry = new IngredientEntry(measure, ingredient, quantity, recipeId);
                ingredientEntries.add(entry);
            }
        }
        if (ingredientEntries.size() != 0)
            return ingredientEntries;
        else
            throw new JSONException("Couldn't read from json");
    }
}
