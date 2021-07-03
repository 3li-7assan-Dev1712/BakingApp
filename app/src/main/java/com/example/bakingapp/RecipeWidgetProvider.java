package com.example.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.bakingapp.Entries.StepsEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String TAG = RecipeWidgetProvider.class.getSimpleName();
    /*I'm going to use this list of data to support the widget by the primary data.*/
    private static List<StepsEntry> mStepsEntries;

    public static void setmStepsEntries(List<StepsEntry> mStepsEntries) {
        RecipeWidgetProvider.mStepsEntries = mStepsEntries;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int ignored : appWidgetIds) {
            AppRecipeService.startActionSetFirstStep(context, mStepsEntries);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    public static void updateAppRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, String stepDes) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppRecipeWidget(context, appWidgetManager, appWidgetId, stepDes);
        }
    }
    public static void updateAppRecipeWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String stepDes) {


        // Construct the RemoteViews object
        RemoteViews views;
        Bundle bundle = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int minHeight = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        if (minHeight < 156)
            views = getNormalRemoteViews(context, stepDes);
        else
            views = getLongRemoteViews(context, stepDes);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static RemoteViews getNormalRemoteViews(Context context, String stepDes){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        views.setTextViewText(R.id.appwidget_description_text, stepDes);
        int widgetSrc = SharedPreferenceUtils.getFavoriteRecipe(context);
        switch (widgetSrc){
            case 0:
                views.setImageViewResource(R.id.iv_appwidget, R.drawable.nutella_pie);
                break;
            case 1:
                views.setImageViewResource(R.id.iv_appwidget, R.drawable.brownie);
                break;
            case 2:
                views.setImageViewResource(R.id.iv_appwidget, R.drawable.yellow_cake);
                break;
            case 3:
                views.setImageViewResource(R.id.iv_appwidget, R.drawable.cheese_cake);
                break;
            default:
                views.setImageViewResource(R.id.iv_appwidget, 0);
        }

        /*create the intent to start out service to update the widget in the background*/
        Intent goToNextStepIntent = new Intent(context, AppRecipeService.class);
        goToNextStepIntent.setAction(AppRecipeService.ACTION_GO_TO_NEXT_STEP);
        /*creating the back navigation arrow for navigating back to see previous steps*/
        Intent goToPreviousStep = new Intent(context, AppRecipeService.class);
        goToPreviousStep.setAction(AppRecipeService.ACTION_GO_TO_PREVIOUS_STEP);

        Intent openInstructionActivity = new Intent(context, InstructionsActivity.class);

        goToNextStepIntent.putParcelableArrayListExtra(context.getString(R.string.goToNextStepIntentKey), (ArrayList<? extends Parcelable>) mStepsEntries);
        goToPreviousStep.putParcelableArrayListExtra(context.getString(R.string.goToPrevioustepIntentKey), (ArrayList<? extends Parcelable>) mStepsEntries);
        openInstructionActivity.putParcelableArrayListExtra("ali", (ArrayList<? extends Parcelable>) mStepsEntries);
        openInstructionActivity.putExtra(context.getString(R.string.ingredientIdKey), SharedPreferenceUtils.getMaxNumberOfSteps(context));
        PendingIntent goToNextStepPendingIndent =
                PendingIntent.getService(context, 0, goToNextStepIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent goToPreviousStepPendingIntent =
                PendingIntent.getService(context, 0, goToPreviousStep, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.next_description_image_widget, goToNextStepPendingIndent);
        /*open the detail activity when the user click the text description text*/
        PendingIntent openInstructionPendingIntent=
                PendingIntent.getActivity(context, 0, openInstructionActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.previous_description_image_widget, goToPreviousStepPendingIntent);
        if (mStepsEntries!= null && SharedPreferenceUtils.getFavoriteRecipe(context) != -1)
            views.setOnClickPendingIntent(R.id.appwidget_description_text, openInstructionPendingIntent);
        // Instruct the widget manager to update the widget
        return views;
    }

    public static RemoteViews getLongRemoteViews(Context context, String stepDes){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider_aternative);
        views.setTextViewText(R.id.appwidget_description_text, stepDes);
        views.setTextViewText(R.id.appwidget_description_text, stepDes);
        int widgetSrc = SharedPreferenceUtils.getFavoriteRecipe(context);
        switch (widgetSrc){
            case 0:
                views.setImageViewResource(R.id.iv_appwidget, R.drawable.nutella_pie);
                break;
            case 1:
                views.setImageViewResource(R.id.iv_appwidget, R.drawable.brownie);
                break;
            case 2:
                views.setImageViewResource(R.id.iv_appwidget, R.drawable.yellow_cake);
                break;
            case 3:
                views.setImageViewResource(R.id.iv_appwidget, R.drawable.cheese_cake);
                break;
            default:
                views.setImageViewResource(R.id.iv_appwidget, 0);
        }

        /*create the intent to start out service to update the widget in the background*/
        Intent goToNextStepIntent = new Intent(context, AppRecipeService.class);
        goToNextStepIntent.setAction(AppRecipeService.ACTION_GO_TO_NEXT_STEP);
        /*creating the back navigation arrow for navigating back to see previous steps*/
        Intent goToPreviousStep = new Intent(context, AppRecipeService.class);
        goToPreviousStep.setAction(AppRecipeService.ACTION_GO_TO_PREVIOUS_STEP);

        Intent openInstructionActivity = new Intent(context, InstructionsActivity.class);

        goToNextStepIntent.putParcelableArrayListExtra(context.getString(R.string.goToNextStepIntentKey), (ArrayList<? extends Parcelable>) mStepsEntries);
        goToPreviousStep.putParcelableArrayListExtra(context.getString(R.string.goToPrevioustepIntentKey), (ArrayList<? extends Parcelable>) mStepsEntries);
        openInstructionActivity.putParcelableArrayListExtra("ali", (ArrayList<? extends Parcelable>) mStepsEntries);
        openInstructionActivity.putExtra(context.getString(R.string.ingredientIdKey), SharedPreferenceUtils.getMaxNumberOfSteps(context));
        PendingIntent goToNextStepPendingIndent =
                PendingIntent.getService(context, 0, goToNextStepIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent goToPreviousStepPendingIntent =
                PendingIntent.getService(context, 0, goToPreviousStep, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.next_description_image_widget, goToNextStepPendingIndent);
        /*open the detail activity when the user click the text description text*/
        PendingIntent openInstructionPendingIntent=
                PendingIntent.getActivity(context, 0, openInstructionActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.previous_description_image_widget, goToPreviousStepPendingIntent);
        if (mStepsEntries!= null && SharedPreferenceUtils.getFavoriteRecipe(context) != -1)
            views.setOnClickPendingIntent(R.id.appwidget_description_text, openInstructionPendingIntent);
        // Instruct the widget manager to update the widget
        return views;
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        if (mStepsEntries !=  null) {
            String description = mStepsEntries.get(SharedPreferenceUtils.getMaxNumberOfSteps(context)).getDescription();
            updateAppRecipeWidget(context, appWidgetManager, appWidgetId, description);
        }
        Log.d(TAG, "onChange is called");
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
}

