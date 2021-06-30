package com.example.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import com.example.bakingapp.Entries.StepsEntry;
import java.util.ArrayList;
import java.util.List;

public class AppRecipeService extends IntentService {

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SET_FIRST_STEP = "com.example.bakingapp.action.setFirstStep";

    public AppRecipeService() {
        super("AppRecipeService");
    }

    public static void startActionSetFirstStep(Context context, List<StepsEntry> stepsEntries) {
        Intent setFirstStepIntent = new Intent(context, AppRecipeService.class);
        setFirstStepIntent.setAction(ACTION_SET_FIRST_STEP);
        setFirstStepIntent.putParcelableArrayListExtra("ali", (ArrayList<? extends Parcelable>) stepsEntries);
        context.startService(setFirstStepIntent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SET_FIRST_STEP.equals(action)) {

                handleActionSetFirstStep(intent.getParcelableArrayListExtra("ali"));
            }
        }
    }

    private void handleActionSetFirstStep(List<StepsEntry> stepsEntries) {
        /*I use the SharedPreference in order to track the user's preference of the offered recipes.*/
        int recipeId = SharedPreferenceUtils.getFavoriteRecipe(this);
        String firstStep;
        if (recipeId == -1 || stepsEntries == null){
            firstStep = getString(R.string.defaultValueMessage);
        }else {
            /*getting the first time in the list and set it as the first step of the recipe*/
            firstStep = stepsEntries.get(0).getDescription();
        }
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        int[] widgetIds = manager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateAppRecipeWidgets(this,
                manager,
                widgetIds,
                firstStep);
    }

}
