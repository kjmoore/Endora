package com.kieranjohnmoore.endora.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.kieranjohnmoore.endora.R;

public class TrackerWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int[] appWidgetIds, boolean[] exerciseSuccessful) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setImageViewResource(R.id.image_day_0, exerciseSuccessful[0] ? R.drawable.tick_circle : R.drawable.empty_circle);
        views.setImageViewResource(R.id.image_day_1, exerciseSuccessful[1] ? R.drawable.tick_circle : R.drawable.empty_circle);
        views.setImageViewResource(R.id.image_day_2, exerciseSuccessful[2] ? R.drawable.tick_circle : R.drawable.empty_circle);
        views.setImageViewResource(R.id.image_day_3, exerciseSuccessful[3] ? R.drawable.tick_circle : R.drawable.empty_circle);
        views.setImageViewResource(R.id.image_day_4, exerciseSuccessful[4] ? R.drawable.tick_circle : R.drawable.empty_circle);
        views.setImageViewResource(R.id.image_day_5, exerciseSuccessful[5] ? R.drawable.tick_circle : R.drawable.empty_circle);
        views.setImageViewResource(R.id.image_day_6, exerciseSuccessful[6] ? R.drawable.tick_circle : R.drawable.empty_circle);

        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {}

    @Override
    public void onEnabled(Context context) {}

    @Override
    public void onDisabled(Context context) {}
}
