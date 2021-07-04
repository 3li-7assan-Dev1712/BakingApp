package com.example.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.bakingapp.Entries.StepsEntry;
import com.google.android.exoplayer2.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AppRecipeService extends IntentService {

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SET_FIRST_STEP = "com.example.bakingapp.action.setFirstStep";
    public static final String ACTION_GO_TO_NEXT_STEP = "com.example.bakingapp.action.goToNextStep";
    public static final String ACTION_GO_TO_PREVIOUS_STEP = "com.example.bakingapp.action.goToPreviousStep";
    private static final String TAG = AppRecipeService.class.getSimpleName();


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
            }else if(ACTION_GO_TO_NEXT_STEP.equals(action)){
                List<StepsEntry> entries = intent.getParcelableArrayListExtra(getString(R.string.goToNextStepIntentKey));
                if (entries != null)
                    handleActionGoToNextStep(entries);
            }else if(ACTION_GO_TO_PREVIOUS_STEP.equals(action)){
                List<StepsEntry> entries = intent.getParcelableArrayListExtra(getString(R.string.goToPrevioustepIntentKey));
                handleActionGoToPreviousStep(entries);
            }
        }
    }

    private void handleActionGoToPreviousStep(List<StepsEntry> entries) {
        // firstly I'll decrease the tracker number

        int lastStepIndex = SharedPreferenceUtils.getMaxNumberOfSteps(this);
        /*then I check it and if it is bigger or equals to 0 we do out navigation*/
        if (entries == null)
            startActionSetFirstStep(this, null);
        else if (lastStepIndex > 0){
            SharedPreferenceUtils.decreaseMaxNumberOfStepsByOne(this);
            lastStepIndex = SharedPreferenceUtils.getMaxNumberOfSteps(this);
            String recipeDescription = entries.get(lastStepIndex).getDescription();
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            int[] widgetIds = manager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
            RecipeWidgetProvider.updateAppRecipeWidgets(this,
                    manager,
                    widgetIds,
                    recipeDescription);
        }else{
            Toast.makeText(getApplicationContext(), "This is the first step, you can't navigate more back.", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "This is the first step");
        }
    }

    private void handleActionGoToNextStep(List<StepsEntry> entries) {
        /*increase the tracker number and check if it is the last step or not*/
        int lastStepIndex = SharedPreferenceUtils.getMaxNumberOfSteps(this);
        if (entries==null)
            startActionSetFirstStep(this, null);
        else if (lastStepIndex < entries.size()-1){
            SharedPreferenceUtils.increaseMaxNumberOfStepsByOne(this);
            lastStepIndex= SharedPreferenceUtils.getMaxNumberOfSteps(this);
            String recipeDescription = entries.get(lastStepIndex).getDescription();
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            int[] widgetIds = manager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
            RecipeWidgetProvider.updateAppRecipeWidgets(this,
                    manager,
                    widgetIds,
                    recipeDescription);
        }else{
            Toast.makeText(getApplicationContext(), "This is the last step", Toast.LENGTH_SHORT).show();

            Log.d(TAG, "This is the last step");
        }
        /*lastStepIndex starts from 0 and on each time we want to see the next description in the widget
        * we increase it using SharedPreferenceUtils class*/
    }

    private void handleActionSetFirstStep(List<StepsEntry> stepsEntries) {
        /*I use the SharedPreference in order to track the user's preference of the offered recipes.*/
        int recipeId = SharedPreferenceUtils.getFavoriteRecipe(this);
        int lastStepIndex = SharedPreferenceUtils.getMaxNumberOfSteps(this);
        String firstStep;
        if (recipeId == -1 || stepsEntries == null){
            firstStep = getString(R.string.defaultValueMessage);
        }else if (recipeId >= 0 && lastStepIndex > 0){
            /*if the user had a favorite recipe and also stopped in a particular step we're going to
            * let them keep from where they stopped.*/
            firstStep = stepsEntries.get(lastStepIndex).getDescription();
        }else{
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
