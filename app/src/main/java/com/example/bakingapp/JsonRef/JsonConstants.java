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

}
