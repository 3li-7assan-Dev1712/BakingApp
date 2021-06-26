package com.example.bakingapp.JsonRef;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bakingapp.Entries.IngredientEntry;
import com.example.bakingapp.JsonRef.JsonConstants;

import java.util.ArrayList;
import java.util.List;

// this class is responsible for parsing json data received from the server se we can fill the database
public class JsonUtils {


    public static List<String> getRecipeNames(String jsonResponse) throws JSONException, Exception {
        List<String> recipesName = new ArrayList<>();
        JSONArray recipesArray = new JSONArray(jsonResponse);
        for (int i = 0; i < recipesArray.length(); i ++){
            JSONObject recipeObject = recipesArray.getJSONObject(i);
            String recipeName = recipeObject.getString(JsonConstants.RECIPE_NAME);
            recipesName.add(recipeName);
        }
       if (recipesName.size() != 0)
           return recipesName;
       else
           throw new Exception("Couldn't read from json");
    }

    public static List<String> getRecipeSteps(String jsonResponse, int recipeId) throws JSONException, Exception {
        List<String> steps = new ArrayList<>();
        JSONArray recipesArray = new JSONArray(jsonResponse);
        JSONObject recipeObject = recipesArray.getJSONObject(recipeId);
        JSONArray stepsArray = recipeObject.getJSONArray(JsonConstants.RECIPE_STEPS);
        for (int i = 0; i < stepsArray.length(); i ++){
            JSONObject stepObj = stepsArray.getJSONObject(i);
            String shortDescription = stepObj.getString(JsonConstants.SHORT_DESCRIPTION);
            steps.add(shortDescription);
        }
        if (steps.size() != 0)
            return steps;
        else
            throw new Exception("Couldn't read from json");
    }

    public static String getRecipeDescription(String jsonResponse, int recipeId, int stepId) throws JSONException, Exception {
        JSONArray recipesArray = new JSONArray(jsonResponse);
        JSONObject recipeObject = recipesArray.getJSONObject(recipeId);
        JSONArray stepsArray = recipeObject.getJSONArray(JsonConstants.RECIPE_STEPS);
        JSONObject stepObj = stepsArray.getJSONObject(stepId);
        String description = stepObj.getString(JsonConstants.DESCRIPTION);
         return description;
    }
    public static List<IngredientEntry> getRecipeIngredients(String jsonResponse, int recipeId) throws JSONException, Exception {
        List<IngredientEntry> ingredientEntries = new ArrayList<>();
        JSONArray recipesArray = new JSONArray(jsonResponse);
        JSONObject recipeObject = recipesArray.getJSONObject(recipeId);
        JSONArray stepsArray = recipeObject.getJSONArray(JsonConstants.RECIPE_INGREDIENT);
        for (int i = 0; i < stepsArray.length(); i ++){
            JSONObject stepObj = stepsArray.getJSONObject(i);
            String ingredient = stepObj.getString(JsonConstants.INGREDIENT);
            String measure = stepObj.getString(JsonConstants.MEASURE);
            double quantity = stepObj.getDouble(JsonConstants.QUANTITY);
            ingredientEntries.add(new IngredientEntry(measure, ingredient, (float) quantity, recipeId));
        }
        if (ingredientEntries.size() != 0)
            return ingredientEntries;
        else
            throw new Exception("Couldn't read from json");
    }
    public static String getRecipeVideoUrl(String jsonResponse, int recipeId, int stepId) throws JSONException, Exception {
        JSONArray recipesArray = new JSONArray(jsonResponse);
        JSONObject recipeObject = recipesArray.getJSONObject(recipeId);
        JSONArray stepsArray = recipeObject.getJSONArray(JsonConstants.RECIPE_STEPS);
        JSONObject stepObj = stepsArray.getJSONObject(stepId);
        return stepObj.getString(JsonConstants.VIDEO_URL);
    }
}
