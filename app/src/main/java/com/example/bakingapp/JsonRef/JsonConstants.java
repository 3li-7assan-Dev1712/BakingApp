package com.example.bakingapp.JsonRef;

import com.example.bakingapp.R;

public enum JsonConstants {;

    public static final String SHORT_DESCRIPTION = "shortDescription";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_ID ="id";
    public static final String RECIPE_INGREDIENT= "ingredients";
    public static final String RECIPE_STEPS="steps";
    public static final  String QUANTITY= "quantity";
    public static final String MEASURE = "measure";
    public static final String INGREDIENT= "ingredient";
    public static final String DESCRIPTION= "description";
    public static final String VIDEO_URL= "videoURL";
    public static final String THUMBNAIL_URL= "thumbnailURL";

    public static String [] units = {"CUP","TBLSP","TSP","G","K","OZ","UNIT"};
    public static String [] unitName = {"Cup","Tablespoon","Teaspoon","Gram","Kilogram","Ounce","Unit"};
    public static int [] unitIcons = {
            R.drawable.cup,
            R.drawable.tablespoon,
            R.drawable.teaspoon,
            R.drawable.g,
            R.drawable.kg,
            R.drawable.oz,
            R.drawable.unit
    };

    public static String[] recipesNames = {"Nutella Pie", "Brownies", "Yellow Cake", "Cheesecake"};
    public static String getRecipeName(int recipeIndex){
        /*because of the indexes in arrays start from 0, and my index starts from 1
        * I should always use index-1*/
        return recipesNames[recipeIndex-1];
    }
    public static int[] imagesIds = {
            R.drawable.nutella_pie,
            R.drawable.brownie,
            R.drawable.yellow_cake,
            R.drawable.cheese_cake
    };
    public static int getImageId(int recipeIndex){
        return imagesIds[recipeIndex-1];
    }
}
