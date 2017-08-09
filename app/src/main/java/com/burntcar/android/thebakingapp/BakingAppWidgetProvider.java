package com.burntcar.android.thebakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.burntcar.android.thebakingapp.restCalls.BakingAppClient;
import com.burntcar.android.thebakingapp.restCalls.Ingredient;
import com.burntcar.android.thebakingapp.restCalls.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.burntcar.android.thebakingapp.restCalls.*;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

/*static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

       *//* CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
        *//**//**//**//*views.setTextViewText(R.id.tv_widget, widgetText);*//**//**//**//*

        *//**//**//**//*Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);*//**//**//**//**//*

       /*//* views.setOnClickPendingIntent(R.id.tv_widget,pendingIntent);*//**//*
        // Instruct the widget manager to update the widget

    Intent intent = new Intent(context, ListViewService.class);
    // Add the app widget ID to the intent extras.
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
    //intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
    // Instantiate the RemoteViews object for the app widget layout.
    RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
    // Set up the RemoteViews object to use a RemoteViews adapter.
    // This adapter connects
    // to a RemoteViewsService  through the specified intent.
    // This is how you populate the data.
    rv.setRemoteAdapter( R.id.list_view_widget1, intent);


        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }*/
@SuppressWarnings("deprecation")
@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        // update each of the app widgets with the remote adapter
        for (int i = 0; i < appWidgetIds.length; ++i) {

            // Set up the intent that starts the StackViewService, which will
            // provide the views for this collection.
            Intent intent = new Intent(context, ListViewService.class);
            // Add the app widget ID to the intent extras.
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            // Instantiate the RemoteViews object for the app widget layout.
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
            // Set up the RemoteViews object to use a RemoteViews adapter.
            // This adapter connects
            // to a RemoteViewsService  through the specified intent.
            // This is how you populate the data.
            rv.setTextViewText(R.id.tv_widget,"from OnUpdate");
            rv.setRemoteAdapter( R.id.list_view_widget, intent);

            // The empty view is displayed when the collection has no items.
            // It should be in the same layout used to instantiate the RemoteViews
            // object above.
           /* rv.setEmptyView(R.id.list_view_widget12, R.id.tv_widget);*/

            //
            // Do additional processing specific to this app widget...
            //

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }
        //super.onUpdate(context, appWidgetManager, appWidgetIds);
    }





    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}

