package com.example.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
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
            AppRecipeService.startActionSetFirstStep(context, null);
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
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        views.setTextViewText(R.id.appwidget_description_text, stepDes);
        /*create the intent to start out service to update the widget in the background*/
        Intent goToNextStepIntent = new Intent(context, AppRecipeService.class);
        goToNextStepIntent.setAction(AppRecipeService.ACTION_GO_TO_NEXT_STEP);
        if (mStepsEntries != null)
            goToNextStepIntent.putParcelableArrayListExtra(context.getString(R.string.goToNextStepIntentKey), (ArrayList<? extends Parcelable>) mStepsEntries);
        else
            Log.e(TAG, "mStepsEntries == null");
        PendingIntent pendingIntent =
                PendingIntent.getService(context, 0, goToNextStepIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.next_description_image_widget, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}

