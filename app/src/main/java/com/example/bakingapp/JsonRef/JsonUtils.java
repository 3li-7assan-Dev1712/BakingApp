package com.example.bakingapp.JsonRef;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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


}
